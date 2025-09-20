package cc.sofast.framework.literule4j.starter;

import cc.sofast.framework.literule4j.api.ActorSystemContext;
import cc.sofast.framework.literule4j.api.RuleEngineService;
import cc.sofast.framework.literule4j.api.metadata.RuleChinaDefinition;
import cc.sofast.framework.literule4j.core.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author wxl
 */
@Slf4j
public class ClassPathRuleDefinitionScanner implements InitializingBean {

    private final RuleEngineService ruleEngineService;

    private final ActorSystemContext actorSystemContext;

    public ClassPathRuleDefinitionScanner(RuleEngineService ruleEngineService, ActorSystemContext actorSystemContext) {
        this.ruleEngineService = ruleEngineService;
        this.actorSystemContext = actorSystemContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:rule-definition/*.json");
        for (Resource resource : resources) {
            String json = new String(resource.getInputStream().readAllBytes());
            RuleChinaDefinition ruleChinaDefinition = JsonUtils.fromJson(json, RuleChinaDefinition.class);
            ruleEngineService.init(ruleChinaDefinition, actorSystemContext);
        }
    }
}
