package cc.sofast.framework.literule4j.api;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    @Builder.Default
    private final Map<String, Object> metadata = new ConcurrentHashMap<>();
    /**
     * 数据
     */
    @Builder.Default
    private final Map<String, Object> data = new ConcurrentHashMap<>();

    public static class RuleMessageBuilder {
        public RuleMessageBuilder putMetadata(String key, Object value) {
            if (!metadata$set) {
                metadata(new ConcurrentHashMap<>());
                metadata$set = true;
            }
            this.metadata$value.put(key, value);
            return this;
        }

        public RuleMessageBuilder putData(String key, Object value) {
            if (!data$set) {
                data(new ConcurrentHashMap<>());
                data$set = true;
            }
            this.data$value.put(key, value);
            return this;
        }
    }
}
