package cc.sofast.framework.literule4j.api;

import cc.sofast.framework.literule4j.actor.lifecycle.ActorMsg;

import java.util.Map;

/**
 * 规则节点
 * @author wxl
 */
public interface RuleNode {
    /**
     * 初始化
     * @param config 节点配置
     */
    void init(Map<String, Object> config);

    /**
     * 销毁
     */
    default void destroy() {
    }

    /**
     * 执行节点
     * @param ctx 上下文
     * @param ruleMsg 消息
     */
    void onMsg(RuleContext ctx, RuleMessage ruleMsg) throws Exception;
}
