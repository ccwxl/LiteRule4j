package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleChinaInitMsg;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleNodeToNextNodeMsg;
import cc.sofast.framework.literule4j.api.ActorSystemContext;
import cc.sofast.framework.literule4j.api.RuleMessage;
import cc.sofast.framework.literule4j.api.metadata.Connection;
import cc.sofast.framework.literule4j.api.metadata.Metadata;
import cc.sofast.framework.literule4j.api.metadata.Node;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将消息路由到RuleNodeActor
 *
 * @author wxl
 */
public class RuleChainActor extends AbstractBehavior<RuleMessage> {

    private RuleChinaDefinition definition;

    private final Map<String, List<ActorRef<RuleMessage>>> ruleNodeIdToActor = new ConcurrentHashMap<>();

    private final Map<String, List<String>> ruleNodeIdToConnections = new ConcurrentHashMap<>();

    public RuleChainActor(ActorContext<RuleMessage> context, RuleChinaDefinition definition) {
        super(context);
        this.definition = definition;
    }

    public static Behavior<RuleMessage> create(RuleChinaDefinition definition) {

        return Behaviors.setup(ctx -> new RuleChainActor(ctx, definition));
    }

    @Override
    public Receive<RuleMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleMessage.class, this::onMessage)
                .build();
    }

    private Behavior<RuleMessage> onMessage(RuleMessage ruleMessage) {
        if (ruleMessage instanceof RuleNodeToNextNodeMsg) {
            getContext().getLog().info("RuleEngineChain received RuleNodeToNextNodeMsg: {}", ruleMessage);
        }
        if (ruleMessage instanceof RuleChinaInitMsg initMsg) {
            initRuleNodeActor(initMsg);
            getContext().getLog().info("RuleEngineChain received RuleChinaInitMsg: {}", ruleMessage);
        }
        return this;
    }

    private void initRuleNodeActor(RuleChinaInitMsg initMsg) {
        RuleChinaDefinition definition = initMsg.getDefinition();
        ActorSystemContext context = initMsg.getContext();
        Metadata metadata = definition.getMetadata();
        List<Node> nodes = metadata.getNodes();
        List<Connection> connections = metadata.getConnections();
        ActorRef<RuleMessage> self = getContext().getSelf();
        for (Node node : nodes) {
            ActorRef<RuleMessage> chinaActor = getContext().spawn(RuleNodeActor.create(definition,context, node, self), node.getId());
            ruleNodeIdToActor.put(node.getId(), List.of(chinaActor));
        }

        //节点联系
        for (Connection connection : connections) {
            String fromId = connection.getFromId();
            String toId = connection.getToId();
            //添加下一个执行节点
            List<String> connectionList = ruleNodeIdToConnections.computeIfAbsent(fromId, k -> new ArrayList<>());
            connectionList.add(toId);
        }
    }
}
