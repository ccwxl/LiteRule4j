package cc.sofast.framework.literule4j.api;


import cc.sofast.framework.literule4j.core.action.LogAction;
import cc.sofast.framework.literule4j.core.action.LogAction1;
import cc.sofast.framework.literule4j.core.action.LogAction2;

import java.util.Map;

/**
 * 规则节点工厂
 *
 * @author wxl
 */
public class NodeFactory {
    /**
     * 创建规则节点
     *
     * @param id            规则节点ID
     * @param type          规则节点类型
     * @param configuration 节点配置
     * @return 规则节点
     */
    public static RuleNode creteRuleNode(String id, String type, Map<String, Object> configuration) {
        return switch (type) {
            case "logAction" -> initRuleNode(new LogAction(), id, configuration);
            case "logAction1" -> initRuleNode(new LogAction1(), id, configuration);
            case "logAction2" -> initRuleNode(new LogAction2(), id, configuration);
            default -> throw new IllegalArgumentException("Unsupported node type: " + type);
        };
    }

    /**
     * 初始化规则节点
     *
     * @param node          规则节点
     * @param id            规则节点ID
     * @param configuration 节点配置
     * @return 初始化后的规则节点
     */
    public static RuleNode initRuleNode(AbstractRuleNode node, String id, Map<String, Object> configuration) {
        node.setId(id);
        node.init(configuration);
        return node;
    }
}
