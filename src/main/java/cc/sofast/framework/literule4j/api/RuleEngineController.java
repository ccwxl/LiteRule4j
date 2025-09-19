package cc.sofast.framework.literule4j.api;

import cc.sofast.framework.literule4j.actor.lifecycle.ActorMsg;
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

    public void exec(RuleMessage message) {

        service.post(message);
    }
}
