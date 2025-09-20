package cc.sofast.framework.literule4j.actor.message;

import cc.sofast.framework.literule4j.api.ActorSystemContext;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wxl
 */
@Getter
@Setter
public class RuleChinaInitMessage implements ActorMsg {

    private RuleChinaDefinition definition;

    private ActorSystemContext context;

    public RuleChinaInitMessage(RuleChinaDefinition definition, ActorSystemContext actorSystemContext) {
        this.definition = definition;
        this.context = actorSystemContext;
    }
}
