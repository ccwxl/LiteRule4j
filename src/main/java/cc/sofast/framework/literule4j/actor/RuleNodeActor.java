package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.*;
import cc.sofast.framework.literule4j.actor.lifecycle.ActorMsg;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleEngineMessage;
import cc.sofast.framework.literule4j.api.*;
import cc.sofast.framework.literule4j.api.metadata.Node;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;

import java.time.Duration;

/**
 * @author wxl
 */
public class RuleNodeActor extends AbstractBehavior<ActorMsg> {
    private RuleChinaDefinition definition;
    private RuleNode ruleNode;
    private Node node;
    private ActorRef<ActorMsg> ruleChainActor;
    private DefaultRuleContext ruleContext;

    public RuleNodeActor(ActorContext<ActorMsg> context,
                         RuleChinaDefinition definition, ActorSystemContext actorSystemContext,
                         Node node, ActorRef<ActorMsg> ruleChainActor) {
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

    public static Behavior<ActorMsg> create(RuleChinaDefinition definition, ActorSystemContext context, Node node,
                                            ActorRef<ActorMsg> ruleChainActor) {
        PoolRouter<ActorMsg> actorMsgPoolRouter =
                Routers.pool(3, Behaviors.<ActorMsg>setup(ctx -> new RuleNodeActor(ctx, definition, context, node, ruleChainActor)))
                        .withRoundRobinRouting();

        //supervisorStrategy
        return Behaviors.<ActorMsg>supervise(Behaviors.<ActorMsg>setup(ctx -> new RuleNodeActor(ctx, definition, context, node, ruleChainActor)))
                .onFailure(RuntimeException.class, SupervisorStrategy.restart()
                        .withLimit(3, Duration.ofMinutes(1)));
    }

    @Override
    public Receive<ActorMsg> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleEngineMessage.class, this::onMessage)
                .build();
    }

    private Behavior<ActorMsg> onMessage(RuleEngineMessage ruleMessage) {
        RuleMessage msg = ruleMessage.getMsg();
        try {
            ruleNode.onMsg(ruleContext, msg);
        } catch (Exception e) {
            ruleContext.tellFailure(msg, e);
        }
        return this;
    }
}
