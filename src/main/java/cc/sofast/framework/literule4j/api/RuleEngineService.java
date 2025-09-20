package cc.sofast.framework.literule4j.api;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Props;
import cc.sofast.framework.literule4j.actor.AppActor;
import cc.sofast.framework.literule4j.actor.message.ActorMsg;
import cc.sofast.framework.literule4j.actor.message.RuleChinaInitMessage;
import cc.sofast.framework.literule4j.actor.message.SendToRuleMessage;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wxl
 */
@Slf4j
@Getter
@Setter
public class RuleEngineService {

    private ActorSystem<ActorMsg> system;

    public RuleEngineService() {
        Config config = ConfigFactory.parseString("akka.warn-on-no-license-key = off")
                .withFallback(ConfigFactory.load());
        Props props = Props.empty()
//                .withDispatcherFromConfig("dispatcherConfig")
//                .withMailboxFromConfig("mailboxConfig")
                .withDispatcherDefault();
        this.system = ActorSystem.create(AppActor.create(), "RuleEngineSystem", config, props);
        system.log().info("[initMsg] RuleEngineService created an appActor: {}", system);
    }

    public void post(RuleMessage message) {
        SendToRuleMessage chinaMessage = SendToRuleMessage.builder()
                .msg(message)
                .build();
        system.tell(chinaMessage);
    }

    public void init(RuleChinaDefinition ruleChinaDefinition, ActorSystemContext actorSystemContext) {
        system.tell(new RuleChinaInitMessage(ruleChinaDefinition, actorSystemContext));
    }

    public void reload() {

    }

    public void destroy() {

    }
}
