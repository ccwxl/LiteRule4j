package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.actor.lifecycle.DefaultRuleMessage;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleChinaInitMsg;
import cc.sofast.framework.literule4j.api.RuleMessage;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
public class AppActor extends AbstractBehavior<RuleMessage> {

    private final Map<String, ActorRef<RuleMessage>> ruleChinaIdToActor = new ConcurrentHashMap<>();

    public AppActor(ActorContext<RuleMessage> context) {
        super(context);
    }

    public static Behavior<RuleMessage> create() {
        return Behaviors.setup(AppActor::new);
    }

    @Override
    public Receive<RuleMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleChinaInitMsg.class, this::initRuleChinaHandler)
                .onMessage(DefaultRuleMessage.class, this::onMessage)
                .build();
    }

    private Behavior<RuleMessage> initRuleChinaHandler(RuleChinaInitMsg initMsg) {
        getContext().getLog().info("[initMsg] RuleChinaInitMsg received: {}", initMsg);
        RuleChinaDefinition definition = initMsg.getDefinition();
        String id = definition.getRuleChain().getId();
        if (ruleChinaIdToActor.containsKey(id)) {
            getContext().getLog().info("[initMsg] RuleChainActor already exists: {}", id);
            return this;
        }
        ActorRef<RuleMessage> chinaActor = getContext().spawn(RuleChainActor.create(definition), definition.getRuleChain().getId());
        ruleChinaIdToActor.put(definition.getRuleChain().getId(), chinaActor);
        getContext().getLog().info("[initMsg] RuleChainActor created: {}", chinaActor);
        chinaActor.tell(initMsg);
        return this;
    }


    private Behavior<RuleMessage> onMessage(DefaultRuleMessage ruleMessage) {
        getContext().getLog().info("[ruleMsg] RuleEngineChain received: {}", ruleMessage);
        ActorRef<RuleMessage> ruleMessageActorRef = ruleChinaIdToActor.get(ruleMessage.getRuleChainId());
        ruleMessageActorRef.tell(ruleMessage);
        return this;
    }
}
