package cc.sofast.framework.literule4j.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class RuleContext {
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
    /**
     * 调试信息
     */
    private final List<String> trace = new CopyOnWriteArrayList<>();

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(data.get(key));
    }

    public void addTrace(String log) {
//        trace.add(log);
    }
}