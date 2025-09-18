package cc.sofast.framework.literule4j.api;

import akka.actor.ActorRef;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wxl
 */
@Getter
@Setter
public class RuleEngine {

    private RuleChinaDefinition ruleChinaDefinition;

    private ActorRef appActor;

    public void run(RuleMessage message) {

        appActor.tell(message, ActorRef.noSender());
    }
}
