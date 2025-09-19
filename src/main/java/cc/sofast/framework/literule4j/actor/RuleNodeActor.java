package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.actor.lifecycle.DefaultRuleMessage;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleNodeToNextNodeMsg;
import cc.sofast.framework.literule4j.api.*;
import cc.sofast.framework.literule4j.api.metadata.Node;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;

/**
 * @author wxl
 */
public class RuleNodeActor extends AbstractBehavior<RuleMessage> {
    private RuleChinaDefinition definition;
    private RuleNode ruleNode;
    private Node node;
    private ActorRef<RuleMessage> ruleChainActor;
    private DefaultRuleContext ruleContext;

    public RuleNodeActor(ActorContext<RuleMessage> context,
                         RuleChinaDefinition definition, ActorSystemContext actorSystemContext,
                         Node node, ActorRef<RuleMessage> ruleChainActor) {
        super(context);
        this.definition = definition;
        this.ruleNode = NodeFactory.creteRuleNode(node.getId(), node.getType(), node.getConfiguration());
        this.ruleChainActor = ruleChainActor;
        RuleNodeCtx ruleNodeCtx = new RuleNodeCtx();
        ruleNodeCtx.setRuleChainActor(ruleChainActor);
        ruleNodeCtx.setNode(node);
        ruleNodeCtx.setSelfActor(context.getSelf());
        this.ruleContext = new DefaultRuleContext(actorSystemContext, definition.getRuleChain().getName(), ruleNodeCtx);
    }

    public static Behavior<RuleMessage> create(RuleChinaDefinition definition, ActorSystemContext context, Node node,
                                               ActorRef<RuleMessage> ruleChainActor) {
        return Behaviors.setup(ctx -> new RuleNodeActor(ctx, definition, context, node, ruleChainActor));
    }

    @Override
    public Receive<RuleMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleMessage.class, this::onMessage)
                .build();
    }

    private Behavior<RuleMessage> onMessage(RuleMessage ruleMessage) {
        if (ruleMessage instanceof DefaultRuleMessage) {
            try {
                ruleNode.onMsg(ruleContext, ruleMessage);
            } catch (Exception e) {
                ruleContext.tellFailure(ruleMessage, e);
            }
        }
        return this;
    }
}
