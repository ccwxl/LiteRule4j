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

    }

    @Override
    public void tellFailure(RuleMessage msg, Throwable th) {

    }

    @Override
    public void tellNext(RuleMessage msg, String relationType) {

    }

    @Override
    public void tellNext(RuleMessage msg, Set<String> relationTypes) {

    }

    @Override
    public void tellTrue(RuleMessage msg) {

    }

    @Override
    public void tellFalse(RuleMessage msg) {

    }
}
