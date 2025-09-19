package cc.sofast.framework.literule4j.actor.lifecycle;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
@Getter
@Setter
public abstract class AbstractRuleMessage implements RuleDataMessage {
    private String ruleChainId;

    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 元数据
     */
    private final Map<String, Object> metadata = new ConcurrentHashMap<>();
    /**
     * 数据
     */
    private final Map<String, Object> data = new ConcurrentHashMap<>();
}
