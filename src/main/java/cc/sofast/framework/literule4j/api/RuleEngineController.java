package cc.sofast.framework.literule4j.api;

import lombok.Getter;
import lombok.Setter;

/**
 * 控制器
 * @author wxl
 */
@Getter
@Setter
public class RuleEngineController {

    private RuleEngineService service;

    public RuleEngineController(RuleEngineService service) {
        this.service = service;
    }

    public void exec(RuleMessage message) throws Exception {

        service.post(message);
    }
}
