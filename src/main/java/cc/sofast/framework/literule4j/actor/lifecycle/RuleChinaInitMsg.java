package cc.sofast.framework.literule4j.actor.lifecycle;

import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wxl
 */
@Getter
@Setter
public class RuleChinaInitMsg extends AbstractRuleMessage {

    private RuleChinaDefinition definition;

    public RuleChinaInitMsg(RuleChinaDefinition definition) {
        this.definition = definition;
    }
}
