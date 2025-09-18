package cc.sofast.framework.literule4j.actor.lifecycle;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wxl
 */
@Getter
@Setter
public class RuleNodeToNextNodeMsg extends AbstractRuleMessage {

    private String originNodeId;

    private List<String> nextNodeId;

}
