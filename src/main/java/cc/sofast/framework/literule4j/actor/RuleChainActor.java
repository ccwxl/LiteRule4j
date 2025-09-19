package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.actor.lifecycle.DefaultRuleMessage;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleChinaInitMessage;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleNodeToRuleChinaMessage;
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
                .onMessage(RuleChinaInitMessage.class, this::initRuleNodeActors)
                .onMessage(RuleNodeToRuleChinaMessage.class, this::processNodeMessage)
                .onMessage(DefaultRuleMessage.class, this::onMessage)
                .build();
    }


    private Behavior<RuleMessage> initRuleNodeActors(RuleChinaInitMessage initMsg) {
        RuleChinaDefinition definition = initMsg.getDefinition();
        ActorSystemContext context = initMsg.getContext();
        Metadata metadata = definition.getMetadata();
        List<Node> nodes = metadata.getNodes();
        List<Connection> connections = metadata.getConnections();
        ActorRef<RuleMessage> self = getContext().getSelf();
        for (Node node : nodes) {
            ActorRef<RuleMessage> nodeActor = getContext().spawn(RuleNodeActor.create(definition, context, node, self), node.getId());
            ruleNodeIdToActor.put(node.getId(), List.of(nodeActor));
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

    private Behavior<RuleMessage> onMessage(DefaultRuleMessage ruleMessage) {
        getContext().getLog().info("[onMsg] RuleChainActor received a msg: {}", ruleMessage);
        //todo 获取第一个节点执行

        return this;
    }


    private Behavior<RuleMessage> processNodeMessage(RuleNodeToRuleChinaMessage ruleNodeToRuleChinaMessage) {
        //todo 获取RuleNode的下面的节点执行

        return this;
    }
}
