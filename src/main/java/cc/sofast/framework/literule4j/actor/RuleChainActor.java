package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleNodeToNextNodeMsg;
import cc.sofast.framework.literule4j.actor.lifecycle.ToRuleChinaMsg;
import cc.sofast.framework.literule4j.api.RuleMessage;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将消息路由到RuleNodeActor
 *
 * @author wxl
 */
public class RuleChainActor extends AbstractBehavior<RuleMessage> {

    private final Map<String, List<ActorRef<RuleMessage>>> ruleNodeIdToActor = new ConcurrentHashMap<>();

    public RuleChainActor(ActorContext<RuleMessage> context) {
        super(context);
    }

    public static Behavior<RuleMessage> create(RuleChinaDefinition definition) {

        return null;
    }

    @Override
    public Receive<RuleMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleMessage.class, this::onMessage)
                .build();
    }

    private Behavior<RuleMessage> onMessage(RuleMessage ruleMessage) {
        if (ruleMessage instanceof ToRuleChinaMsg) {

        }
        if (ruleMessage instanceof RuleNodeToNextNodeMsg) {

        }
        return null;
    }
}
