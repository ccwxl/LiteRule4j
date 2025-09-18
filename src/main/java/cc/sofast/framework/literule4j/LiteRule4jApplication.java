package cc.sofast.framework.literule4j;

import cc.sofast.framework.literule4j.actor.lifecycle.DefaultRuleMessage;
import cc.sofast.framework.literule4j.api.RuleEngineController;
import cc.sofast.framework.literule4j.api.RuleMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author wxl
 */
@SpringBootApplication
public class LiteRule4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteRule4jApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RuleEngineController controller) {
        return args -> {
            RuleMessage message = new DefaultRuleMessage();
            message.setRuleChainId("chain_call_rest_api");
            controller.exec(message);
        };
    }
}
