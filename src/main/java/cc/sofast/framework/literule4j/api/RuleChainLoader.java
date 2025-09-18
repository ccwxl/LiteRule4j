package cc.sofast.framework.literule4j.api;

import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import cc.sofast.framework.literule4j.core.utils.JsonUtils;

/**
 * @author wxl
 */
public class RuleChainLoader {

    public static RuleEngine loadFromJson(String json) throws Exception {
        RuleChinaDefinition definition = JsonUtils.fromJson(json, RuleChinaDefinition.class);

        return null;
    }

    public static RuleEngine loadFromClassPath(String ruleChainId) {

        return null;
    }
}