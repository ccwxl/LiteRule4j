package cc.sofast.framework.literule4j.starter;

import cc.sofast.framework.literule4j.api.RuleEngineController;
import cc.sofast.framework.literule4j.api.RuleEngineService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxl
 */
@Configuration
public class LiteRule4Configuration {

    @Bean
    public RuleEngineService ruleEngineService() {
        return new RuleEngineService();
    }

    @Bean
    public RuleEngineController controller(RuleEngineService ruleEngineService) {
        return new RuleEngineController(ruleEngineService);
    }

    @Bean
    public ClassPathRuleDefinitionScanner ruleDefinitionScanner(RuleEngineService ruleEngineService) {
        return new ClassPathRuleDefinitionScanner(ruleEngineService);
    }
}
