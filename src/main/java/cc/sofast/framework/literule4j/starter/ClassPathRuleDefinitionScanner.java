package cc.sofast.framework.literule4j.starter;

import cc.sofast.framework.literule4j.api.RuleChainLoader;
import cc.sofast.framework.literule4j.api.RuleEngine;
import cc.sofast.framework.literule4j.api.RuleEngineService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author wxl
 */
public class ClassPathRuleDefinitionScanner implements InitializingBean {

    private RuleEngineService ruleEngineService;

    public ClassPathRuleDefinitionScanner(RuleEngineService ruleEngineService) {
        this.ruleEngineService = ruleEngineService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:rule-definition/*.json");
        for (Resource resource : resources) {
            String json = new String(resource.getInputStream().readAllBytes());
            RuleEngine ruleEngine = RuleChainLoader.loadFromJson(json);
            ruleEngineService.saveRuleEngine(ruleEngine);
        }
    }
}
