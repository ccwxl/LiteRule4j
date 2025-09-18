package cc.sofast.framework.literule4j.actor.lifecycle;

import cc.sofast.framework.literule4j.api.DefaultRuleMessage;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wxl
 */
@Getter
@Setter
public class RuleChinaAwareMsg extends DefaultRuleMessage {

    private RuleChinaDefinition definition;

    public RuleChinaAwareMsg(RuleChinaDefinition definition) {
        this.definition = definition;
    }
}
