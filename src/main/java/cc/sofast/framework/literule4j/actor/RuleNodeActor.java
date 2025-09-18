package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleNodeToNextNodeMsg;
import cc.sofast.framework.literule4j.api.RuleMessage;
import cc.sofast.framework.literule4j.api.RuleNode;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;

/**
 * @author wxl
 */
public class RuleNodeActor extends AbstractBehavior<RuleMessage> {

    private RuleNode ruleNode;

    private ActorRef<RuleMessage> ruleChainActor;

    public RuleNodeActor(ActorContext<RuleMessage> context) {
        super(context);
    }

    public static Behavior<RuleMessage> create(RuleChinaDefinition definition,
                                               ActorRef<RuleMessage> ruleChainActor) {

        return null;
    }

    @Override
    public Receive<RuleMessage> createReceive() {

        return newReceiveBuilder()
                .onMessage(RuleMessage.class, this::onMessage)
                .build();
    }

    private Behavior<RuleMessage> onMessage(RuleMessage ruleMessage) {
        if (ruleMessage instanceof RuleNodeToNextNodeMsg) {

        }
        return this;
    }
}
