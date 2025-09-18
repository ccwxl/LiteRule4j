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
     * 调试信息
     */
    private final List<String> trace = new CopyOnWriteArrayList<>();


    public void addTrace(String log) {
//        trace.add(log);
    }
}