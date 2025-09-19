package cc.sofast.framework.literule4j.api;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Props;
import cc.sofast.framework.literule4j.actor.AppActor;
import cc.sofast.framework.literule4j.actor.lifecycle.RuleChinaInitMsg;
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

    private ActorSystem<RuleMessage> system;

    public RuleEngineService() {
        Config config = ConfigFactory.parseString("akka.warn-on-no-license-key = off")
                .withFallback(ConfigFactory.load());
        this.system = ActorSystem.create(AppActor.create(), "RuleEngineSystem", config, Props.empty().withDispatcherDefault());
        system.log().info("[initMsg] RuleEngineService created an appActor: {}", system);
    }

    public void post(RuleMessage message) {
        system.tell(message);
    }

    public void init(RuleChinaDefinition ruleChinaDefinition, ActorSystemContext actorSystemContext) {
        system.tell(new RuleChinaInitMsg(ruleChinaDefinition, actorSystemContext));
    }

    public void reload() {

    }

    public void destroy() {

    }
}
