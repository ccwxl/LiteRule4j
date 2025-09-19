package cc.sofast.framework.literule4j.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleChinaInitMessage;
import cc.sofast.framework.literule4j.actor.lifecycle.ActorMsg;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleEngineMessage;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;

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
        return Behaviors.setup(AppActor::new);
    }

    @Override
    public Receive<ActorMsg> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleChinaInitMessage.class, this::initRuleChinaHandler)
                .onMessage(RuleEngineMessage.class, this::onMessage)
                .build();
    }

    private Behavior<ActorMsg> initRuleChinaHandler(RuleChinaInitMessage initMsg) {
        RuleChinaDefinition definition = initMsg.getDefinition();
        String id = definition.getRuleChain().getId();
        if (ruleChinaIdToActor.containsKey(id)) {
            getContext().getLog().info("[initMsg] AppActor already exists: [{}]", id);
            return this;
        }
        ActorRef<ActorMsg> chinaActor = getContext().spawn(RuleChainActor.create(definition), definition.getRuleChain().getId());
        ruleChinaIdToActor.put(definition.getRuleChain().getId(), chinaActor);
        getContext().getLog().info("[initMsg] AppActor created an ruleChinaActor: {} id:[{}]", chinaActor, id);
        chinaActor.tell(initMsg);
        return this;
    }


    private Behavior<ActorMsg> onMessage(RuleEngineMessage ruleMessage) {
        String ruleChainId = ruleMessage.getMsg().getRuleChainId();
        ActorRef<ActorMsg> ruleMessageActorRef = ruleChinaIdToActor.get(ruleChainId);
        ruleMessageActorRef.tell(ruleMessage);
        return this;
    }
}
