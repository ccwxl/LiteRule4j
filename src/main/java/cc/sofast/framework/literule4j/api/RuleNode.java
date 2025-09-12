package cc.sofast.framework.literule4j.api;

import java.util.List;
import java.util.Map;

/**
 * 规则节点
 * @author wxl
 */
public interface RuleNode {
    /**
     * 初始化
     * @param config 节点配置
     */
    void init(Map<String, Object> config);

    /**
     * 销毁
     */
    default void destroy() {
    }

    /**
     * 获取节点ID
     * @return 节点ID
     */
    String getId();

    /**
     * 执行节点
     * @param ctx 上下文
     * @return 节点执行结果
     */
    NodeResult execute(RuleContext ctx) throws Exception;

    /**
     * 获取节点转换关系
     * @return 节点转换关系
     */
    Map<NodeResult, List<String>> getTransitions();
}
