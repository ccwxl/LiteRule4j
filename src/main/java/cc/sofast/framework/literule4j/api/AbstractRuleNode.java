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

}
