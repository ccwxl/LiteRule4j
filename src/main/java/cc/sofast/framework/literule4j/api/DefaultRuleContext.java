package cc.sofast.framework.literule4j.api;


import java.util.Set;

/**
 * @author wxl
 */
public class DefaultRuleContext implements RuleContext {
    private final ActorSystemContext system;
    private final String ruleChainName;
    private final RuleNodeCtx nodeCtx;

    public DefaultRuleContext(ActorSystemContext system, String ruleChainName, RuleNodeCtx nodeCtx) {
        this.system = system;
        this.ruleChainName = ruleChainName;
        this.nodeCtx = nodeCtx;
    }

    @Override
    public void tellSuccess(RuleMessage msg) {
        //todo 发送成功消息

    }

    @Override
    public void tellFailure(RuleMessage msg, Throwable th) {
        //todo 发送失败消息
    }

    @Override
    public void tellNext(RuleMessage msg, String relationType) {
        //todo 发送下一条消息
    }

    @Override
    public void tellNext(RuleMessage msg, Set<String> relationTypes) {
        //todo 批量发送消息
    }

    @Override
    public void tellTrue(RuleMessage msg) {
        //todo 发送消息true
    }

    @Override
    public void tellFalse(RuleMessage msg) {
        //todo 发送消息false
    }
}
