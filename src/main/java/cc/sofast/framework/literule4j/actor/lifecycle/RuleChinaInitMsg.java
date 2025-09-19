package cc.sofast.framework.literule4j.actor.lifecycle;

import cc.sofast.framework.literule4j.api.ActorSystemContext;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wxl
 */
@Getter
@Setter
public class RuleChinaInitMsg implements RuleDataLifeMessage {

    private RuleChinaDefinition definition;

    private ActorSystemContext context;

    public RuleChinaInitMsg(RuleChinaDefinition definition, ActorSystemContext actorSystemContext) {
        this.definition = definition;
        this.context = actorSystemContext;
    }
}
