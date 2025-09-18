package cc.sofast.framework.literule4j.api;

import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl
 */
@Slf4j
public class RuleEngineService {

    private Map<String, RuleEngine> ruleEngineMap = new HashMap<>();

    public RuleEngine loadRuleEngine(String ruleChainId) throws Exception {
        RuleEngine ruleEngine = ruleEngineMap.putIfAbsent(ruleChainId, RuleChainLoader.loadFromClassPath(ruleChainId));
        log.info("load rule chain: {}", ruleChainId);
        return ruleEngine;
    }

    public void saveRuleEngine(RuleEngine ruleEngine) {
        RuleChinaDefinition ruleChinaDefinition = ruleEngine.getRuleChinaDefinition();
        String id = ruleChinaDefinition.getId();
        ruleEngineMap.put(id, ruleEngine);
    }
}
