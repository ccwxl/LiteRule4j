package cc.sofast.framework.literule4j.api;


import cc.sofast.framework.literule4j.actor.lifecycle.RuleNodeToRuleChinaMessage;
import cc.sofast.framework.literule4j.api.metadata.CommonConnectionType;
import cc.sofast.framework.literule4j.api.metadata.ConnectionType;
import cc.sofast.framework.literule4j.api.metadata.DynamicStringConnectionType;

import java.util.Set;
import java.util.stream.Collectors;

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
        msg.setOriginNodeId(nodeCtx.getNode().getId());
        RuleNodeToRuleChinaMessage ruleNodeToRuleChinaMessage = new RuleNodeToRuleChinaMessage();
        ruleNodeToRuleChinaMessage.setMsg(msg);
        ruleNodeToRuleChinaMessage.setOriginNodeId(nodeCtx.getNode().getId());
        ruleNodeToRuleChinaMessage.setRelationTypes(Set.of(CommonConnectionType.SUCCESS));
        nodeCtx.getRuleChainActor().tell(ruleNodeToRuleChinaMessage);
    }

    @Override
    public void tellFailure(RuleMessage msg, Throwable th) {
        msg.setOriginNodeId(nodeCtx.getNode().getId());
        RuleNodeToRuleChinaMessage ruleNodeToRuleChinaMessage = new RuleNodeToRuleChinaMessage();
        ruleNodeToRuleChinaMessage.setMsg(msg);
        ruleNodeToRuleChinaMessage.setOriginNodeId(nodeCtx.getNode().getId());
        ruleNodeToRuleChinaMessage.setRelationTypes(Set.of(CommonConnectionType.FAILED));
        nodeCtx.getRuleChainActor().tell(ruleNodeToRuleChinaMessage);
    }

    @Override
    public void tellNext(RuleMessage msg, String relationType) {
        msg.setOriginNodeId(nodeCtx.getNode().getId());
        RuleNodeToRuleChinaMessage ruleNodeToRuleChinaMessage = new RuleNodeToRuleChinaMessage();
        ruleNodeToRuleChinaMessage.setMsg(msg);
        ruleNodeToRuleChinaMessage.setOriginNodeId(nodeCtx.getNode().getId());
        ruleNodeToRuleChinaMessage.setRelationTypes(Set.of(DynamicStringConnectionType.of(relationType)));
        nodeCtx.getRuleChainActor().tell(ruleNodeToRuleChinaMessage);
    }

    @Override
    public void tellNext(RuleMessage msg, Set<String> relationTypes) {
        msg.setOriginNodeId(nodeCtx.getNode().getId());
        Set<ConnectionType> collect = relationTypes.stream().map(DynamicStringConnectionType::of).collect(Collectors.toSet());
        RuleNodeToRuleChinaMessage ruleNodeToRuleChinaMessage = new RuleNodeToRuleChinaMessage();
        ruleNodeToRuleChinaMessage.setMsg(msg);
        ruleNodeToRuleChinaMessage.setOriginNodeId(nodeCtx.getNode().getId());
        ruleNodeToRuleChinaMessage.setRelationTypes(collect);
        nodeCtx.getRuleChainActor().tell(ruleNodeToRuleChinaMessage);
    }

    @Override
    public void tellTrue(RuleMessage msg) {
        msg.setOriginNodeId(nodeCtx.getNode().getId());
        RuleNodeToRuleChinaMessage ruleNodeToRuleChinaMessage = new RuleNodeToRuleChinaMessage();
        ruleNodeToRuleChinaMessage.setMsg(msg);
        ruleNodeToRuleChinaMessage.setOriginNodeId(nodeCtx.getNode().getId());
        ruleNodeToRuleChinaMessage.setRelationTypes(Set.of(CommonConnectionType.TRUE));
        nodeCtx.getRuleChainActor().tell(ruleNodeToRuleChinaMessage);
    }

    @Override
    public void tellFalse(RuleMessage msg) {
        msg.setOriginNodeId(nodeCtx.getNode().getId());
        RuleNodeToRuleChinaMessage ruleNodeToRuleChinaMessage = new RuleNodeToRuleChinaMessage();
        ruleNodeToRuleChinaMessage.setMsg(msg);
        ruleNodeToRuleChinaMessage.setOriginNodeId(nodeCtx.getNode().getId());
        ruleNodeToRuleChinaMessage.setRelationTypes(Set.of(CommonConnectionType.FALSE));
        nodeCtx.getRuleChainActor().tell(ruleNodeToRuleChinaMessage);
    }
}
