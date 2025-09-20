package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.actor.lifecycle.ActorMsg;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleChinaInitMessage;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleEngineMessage;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleNodeToRuleChinaMessage;
import cc.sofast.framework.literule4j.api.ActorSystemContext;
import cc.sofast.framework.literule4j.api.RuleNodeCtx;
import cc.sofast.framework.literule4j.api.metadata.Connection;
import cc.sofast.framework.literule4j.api.metadata.Metadata;
import cc.sofast.framework.literule4j.api.metadata.Node;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将消息路由到RuleNodeActor
 *
 * @author wxl
 */
public class RuleChainActor extends AbstractBehavior<ActorMsg> {

    private RuleChinaDefinition definition;

    private RuleNodeCtx firstNodeCtx;

    private final Map<String, ActorRef<ActorMsg>> ruleNodeIdToActor = new ConcurrentHashMap<>();

    private final Map<String, List<String>> ruleNodeIdToConnections = new ConcurrentHashMap<>();

    public RuleChainActor(ActorContext<ActorMsg> context, RuleChinaDefinition definition) {
        super(context);
        this.definition = definition;
    }

    public static Behavior<ActorMsg> create(RuleChinaDefinition definition) {

        return Behaviors.setup(ctx -> new RuleChainActor(ctx, definition));
    }

    @Override
    public Receive<ActorMsg> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleChinaInitMessage.class, this::initRuleNodeActors)
                .onMessage(RuleNodeToRuleChinaMessage.class, this::processNodeMessage)
                .onMessage(RuleEngineMessage.class, this::onMessage)
                .build();
    }

    private Behavior<ActorMsg> initRuleNodeActors(RuleChinaInitMessage initMsg) {
        RuleChinaDefinition definition = initMsg.getDefinition();
        ActorSystemContext context = initMsg.getContext();
        Metadata metadata = definition.getMetadata();
        List<Node> nodes = metadata.getNodes();
        List<Connection> connections = metadata.getConnections();
        ActorRef<ActorMsg> self = getContext().getSelf();
        for (Node node : nodes) {
            ActorRef<ActorMsg> nodeActor = getContext().spawn(RuleNodeActor.create(definition, context, node, self), node.getId());
            if (node.getFirstNode()) {
                firstNodeCtx = new RuleNodeCtx(self, nodeActor, node);
            }
            ruleNodeIdToActor.put(node.getId(), nodeActor);
            getContext().getLog().info("[initMsg] RuleChainActor created an nodeActor: {} id:[{}]", nodeActor, node.getId());
        }

        //节点联系
        for (Connection connection : connections) {
            String fromId = connection.getFromId();
            String toId = connection.getToId();
            //添加下一个执行节点
            List<String> connectionList = ruleNodeIdToConnections.computeIfAbsent(fromId, k -> new ArrayList<>());
            connectionList.add(toId);
        }
        return this;
    }

    private Behavior<ActorMsg> onMessage(RuleEngineMessage ruleMessage) {
        firstNodeCtx.getSelfActor().tell(ruleMessage);
        return this;
    }

    private Behavior<ActorMsg> processNodeMessage(RuleNodeToRuleChinaMessage ruleNodeToRuleChinaMessage) {
        // 获取RuleNode的下面的节点执行
        String originNodeId = ruleNodeToRuleChinaMessage.getOriginNodeId();
        List<String> nextNodeIds = ruleNodeIdToConnections.get(originNodeId);
        if (CollectionUtils.isEmpty(nextNodeIds)) {
            return this;
        }
        if (nextNodeIds.size() == 1) {
            String nextNodeId = nextNodeIds.getFirst();
            ActorRef<ActorMsg> nextNodeActor = ruleNodeIdToActor.get(nextNodeId);
            nextNodeActor.tell(ruleNodeToRuleChinaMessage);
        } else {
            for (String nextNodeId : nextNodeIds) {
                ActorRef<ActorMsg> nextNodeActor = ruleNodeIdToActor.get(nextNodeId);
                //todo 数据需要拷贝。ActorNode的消息需要不可变。
                nextNodeActor.tell(ruleNodeToRuleChinaMessage);
            }
        }
        return this;
    }
}
