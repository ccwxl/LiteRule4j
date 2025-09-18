package cc.sofast.framework.literule4j.api.metadata;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 规则定义
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class RuleChinaDefinition {

    private String id;

    /**
     * 规则定义
     */
    private RuleChina ruleChain;

    /**
     * 元数据定义
     */
    private Metadata metadata;
}
