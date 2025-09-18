package cc.sofast.framework.literule4j.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.api.RuleMessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将消息路由到RuleNodeActor
 *
 * @author wxl
 */
public class RuleChainActor extends AbstractBehavior<RuleMessage> {

    private Map<String, List<ActorRef>> ruleNodeIdToActor = new ConcurrentHashMap<>();

    public RuleChainActor(ActorContext<RuleMessage> context) {
        super(context);
    }


    @Override
    public Receive<RuleMessage> createReceive() {
        return null;
    }
}
