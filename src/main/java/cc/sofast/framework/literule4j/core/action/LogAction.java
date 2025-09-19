package cc.sofast.framework.literule4j.core.action;

import cc.sofast.framework.literule4j.api.AbstractRuleNode;
import cc.sofast.framework.literule4j.api.RuleContext;
import cc.sofast.framework.literule4j.actor.lifecycle.ActorMsg;
import cc.sofast.framework.literule4j.api.RuleMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author wxl
 */
@Slf4j
public class LogAction extends AbstractRuleNode {
    /**
     * 初始化
     *
     * @param config 节点配置
     */
    @Override
    public void init(Map<String, Object> config) {

    }

    /**
     * 执行节点
     *
     * @param ctx     上下文
     * @param ruleMsg 消息
     */
    @Override
    public void onMsg(RuleContext ctx, RuleMessage ruleMsg) throws Exception {
        log.info("LogAction:[{}] id:[{}] originId: [{}]", ruleMsg.getRuleChainId(), getId(), ruleMsg.getOriginNodeId());
        ctx.tellSuccess(ruleMsg);
    }
}
