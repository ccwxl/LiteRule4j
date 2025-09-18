package cc.sofast.framework.literule4j.actor;

import akka.actor.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleChinaAwareMsg;
import cc.sofast.framework.literule4j.api.RuleMessage;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
public class AppActor extends AbstractBehavior<RuleMessage> {

    private Map<String, ActorRef> ruleChinaIdToActor = new ConcurrentHashMap<>();

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
        if (ruleMessage instanceof RuleChinaAwareMsg) {
            initRuleChina((RuleChinaAwareMsg) ruleMessage);
        }
        return this;
    }

    private void initRuleChina(RuleChinaAwareMsg ruleMessage) {
        RuleChinaDefinition definition = ruleMessage.getDefinition();

    }
}
