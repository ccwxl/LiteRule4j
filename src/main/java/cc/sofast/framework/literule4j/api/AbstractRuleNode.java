package cc.sofast.framework.literule4j.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象节点
 *
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractRuleNode implements RuleNode {
    /**
     * 节点id
     */
    protected String id;

    /**
     * 节点关系
     */
    protected Map<NodeResult, List<String>> transitions = new HashMap<>();

    /**
     * 添加节点关系
     *
     * @param result 结果
     * @param targetNodeId 节点id
     */
    public void addTransition(NodeResult result, String targetNodeId) {
        List<String> transition = transitions.computeIfAbsent(result, k -> new ArrayList<>());
        transition.add(targetNodeId);
    }
}
