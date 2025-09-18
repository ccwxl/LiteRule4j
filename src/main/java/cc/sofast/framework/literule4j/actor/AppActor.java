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
                .onMessage(RuleMessage.class, this::onMessage)
                .build();
    }

    private Behavior<RuleMessage> onMessage(RuleMessage ruleMessage) {
        getContext().getLog().info("RuleEngineChain received: {}", ruleMessage);
        if (ruleMessage instanceof RuleChinaInitMsg) {
            initRuleChina((RuleChinaInitMsg) ruleMessage);
        }
        if (ruleMessage instanceof DefaultRuleMessage) {
            ActorRef<RuleMessage> ruleMessageActorRef = ruleChinaIdToActor.get(ruleMessage.getRuleChainId());
            ruleMessageActorRef.tell(ruleMessage);
        }
        return this;
    }

    private void initRuleChina(RuleChinaInitMsg ruleMessage) {
        RuleChinaDefinition definition = ruleMessage.getDefinition();
        ruleChinaIdToActor.put(definition.getId(), createRuleChainActor(definition));
    }

    private ActorRef<RuleMessage> createRuleChainActor(RuleChinaDefinition definition) {
        ActorRef<RuleMessage> chinaActor = getContext().spawn(RuleChainActor.create(definition), "rule-engine-chain");
        getContext().getLog().info("RuleEngineChain created: {}", chinaActor);
        return chinaActor;
    }
}
