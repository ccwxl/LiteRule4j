package cc.sofast.framework.literule4j.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxl
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleMessage {

    /**
     * todo 源节点ID
     */
    @Deprecated
    private String originNodeId;

    /**
     * 规则链ID
     */
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
