package cc.sofast.framework.literule4j.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 规则引擎消息
 * @author wxl
 */
@Getter
@Setter
public class DefaultRuleMessage implements RuleMessage {

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
