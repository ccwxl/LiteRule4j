package cc.sofast.framework.literule4j.actor.lifecycle;

import cc.sofast.framework.literule4j.api.RuleMessage;
import cc.sofast.framework.literule4j.api.metadata.ConnectionType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author wxl
 */
@Getter
@Setter
public class RuleNodeToRuleChinaMessage implements RuleMessage {

    private RuleMessage msg;

    private String originNodeId;

    private Set<ConnectionType> relationTypes;


}
