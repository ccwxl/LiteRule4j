package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.api.RuleMessage;

/**
 * @author wxl
 */
public class RuleNodeActor extends AbstractBehavior<RuleMessage> {

    public RuleNodeActor(ActorContext<RuleMessage> context) {
        super(context);
    }

    @Override
    public Receive<RuleMessage> createReceive() {
        return null;
    }
}
