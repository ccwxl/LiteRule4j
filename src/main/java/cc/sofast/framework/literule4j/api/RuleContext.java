package cc.sofast.framework.literule4j.api;

import lombok.Data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wxl
 */
@Data
public class RuleContext {

    /**
     * 调试信息
     */
    private final List<String> trace = new CopyOnWriteArrayList<>();
}