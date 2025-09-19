package cc.sofast.framework.literule4j.actor.lifecycle;

import cc.sofast.framework.literule4j.api.RuleMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxl
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleEngineMessage implements ActorMsg {

    private RuleMessage msg;
}
