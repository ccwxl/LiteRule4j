package cc.sofast.framework.literule4j.actor;

import akka.actor.ActorRef;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.api.RuleMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
public class AppActor extends AbstractBehavior<RuleMessage> {

    private Map<String, ActorRef> ruleChinaIdToActor = new ConcurrentHashMap<>();
    private Map<ActorRef, String> actorToRuleChinaId = new ConcurrentHashMap<>();

    public AppActor(ActorContext<RuleMessage> context) {
        super(context);
    }

    @Override
    public Receive<RuleMessage> createReceive() {

        return null;
    }
}
