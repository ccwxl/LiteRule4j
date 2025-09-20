package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.Props;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.*;
import cc.sofast.framework.literule4j.actor.message.ActorMsg;
import cc.sofast.framework.literule4j.actor.message.RuleChinaInitMessage;
import cc.sofast.framework.literule4j.actor.message.SendToRuleMessage;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
public class AppActor extends AbstractBehavior<ActorMsg> {

    private final Map<String, ActorRef<ActorMsg>> ruleChinaIdToActor = new ConcurrentHashMap<>();

    public AppActor(ActorContext<ActorMsg> context) {
        super(context);
    }

    public static Behavior<ActorMsg> create() {

        return Behaviors.supervise(Behaviors.setup(AppActor::new))
                .onFailure(RuntimeException.class, SupervisorStrategy.restart()
                        .withLimit(3, Duration.ofMinutes(1)));
    }

    @Override
    public Receive<ActorMsg> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleChinaInitMessage.class, this::initRuleChinaHandler)
                .onMessage(SendToRuleMessage.class, this::onMessage)
                .build();
    }

    private Behavior<ActorMsg> initRuleChinaHandler(RuleChinaInitMessage initMsg) {
        RuleChinaDefinition definition = initMsg.getDefinition();
        String id = definition.getRuleChain().getId();
        if (ruleChinaIdToActor.containsKey(id)) {
            getContext().getLog().info("[initMsg] AppActor already exists: [{}]", id);
            return this;
        }
        Props empty = Props.empty();
//        empty.withDispatcherFromConfig()
//                .withMailboxFromConfig()
        ActorRef<ActorMsg> chinaActor = getContext().spawn(RuleChainActor.create(definition), definition.getRuleChain().getId(), empty);
        ruleChinaIdToActor.put(definition.getRuleChain().getId(), chinaActor);
        chinaActor.tell(initMsg);
        return Behaviors.same();
    }

    private Behavior<ActorMsg> onMessage(SendToRuleMessage ruleMessage) {
        String ruleChainId = ruleMessage.getMsg().getRuleChainId();
        ActorRef<ActorMsg> ruleMessageActorRef = ruleChinaIdToActor.get(ruleChainId);
        if (ruleMessageActorRef != null) {
            ruleMessageActorRef.tell(ruleMessage);
        } else {
            getContext().getLog().warn("[onMessage] No actor found for ruleChainId: [{}]", ruleChainId);
        }
        return Behaviors.same();
    }
}
