package cc.sofast.framework.literule4j.api;

import lombok.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
@Data
@Builder
@RequiredArgsConstructor
public final class RuleMessage {

    /**
     * todo 源节点ID
     */
    @Deprecated
    private final String originNodeId;

    /**
     * 规则链ID
     */
    private final String ruleChainId;

    /**
     * 消息类型
     */
    private final String msgType;
    /**
     * 元数据
     */
    private final Map<String, Object> metadata = new ConcurrentHashMap<>();
    /**
     * 数据
     */
    private final Map<String, Object> data = new ConcurrentHashMap<>();
}
