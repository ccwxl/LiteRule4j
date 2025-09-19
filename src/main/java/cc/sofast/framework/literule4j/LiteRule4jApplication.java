package cc.sofast.framework.literule4j;

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

    static void main(String[] args) {
        SpringApplication.run(LiteRule4jApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RuleEngineController controller) {
        return _ -> {
            RuleMessage message = new RuleMessage("chain_has_sub_chain_node", "chain_has_sub_chain_node", null);
            controller.exec(message);
        };
    }
}
