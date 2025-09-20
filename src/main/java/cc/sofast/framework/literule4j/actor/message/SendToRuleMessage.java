package cc.sofast.framework.literule4j.actor.message;

import cc.sofast.framework.literule4j.api.RuleMessage;
import cc.sofast.framework.literule4j.api.metadata.ConnectionType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author wxl
 */
@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class SendToRuleMessage implements ActorMsg {

    private final RuleMessage msg;

    private final String originNodeId;

    private final Set<ConnectionType> relationTypes;

}
