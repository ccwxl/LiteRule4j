package cc.sofast.framework.literule4j.actor.lifecycle;

import cc.sofast.framework.literule4j.api.RuleMessage;
import lombok.*;

/**
 * @author wxl
 */
@Data
@Builder
@RequiredArgsConstructor
public class RuleEngineMessage implements ActorMsg {

    private final RuleMessage msg;
}
