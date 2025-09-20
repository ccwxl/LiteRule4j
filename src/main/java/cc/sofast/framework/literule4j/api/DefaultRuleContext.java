package cc.sofast.framework.literule4j.api;


import cc.sofast.framework.literule4j.actor.message.SendToRuleMessage;
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
        nodeCtx.getRuleChainActor().tell(SendToRuleMessage.builder()
                .msg(msg)
                .originNodeId(nodeCtx.getNode().getId())
                .relationTypes(Set.of(CommonConnectionType.SUCCESS))
                .build());
    }

    @Override
    public void tellFailure(RuleMessage msg, Throwable th) {
        nodeCtx.getRuleChainActor().tell(SendToRuleMessage.builder()
                .msg(msg)
                .originNodeId(nodeCtx.getNode().getId())
                .relationTypes(Set.of(CommonConnectionType.FAILED))
                .build());
    }

    @Override
    public void tellNext(RuleMessage msg, String relationType) {
        nodeCtx.getRuleChainActor().tell(SendToRuleMessage.builder()
                .msg(msg)
                .originNodeId(nodeCtx.getNode().getId())
                .relationTypes(Set.of(DynamicStringConnectionType.of(relationType)))
                .build());
    }

    @Override
    public void tellNext(RuleMessage msg, Set<String> relationTypes) {
        Set<ConnectionType> collect = relationTypes.stream().map(DynamicStringConnectionType::of).collect(Collectors.toSet());
        nodeCtx.getRuleChainActor().tell(SendToRuleMessage.builder()
                .msg(msg)
                .originNodeId(nodeCtx.getNode().getId())
                .relationTypes(collect)
                .build());
    }

    @Override
    public void tellTrue(RuleMessage msg) {
        nodeCtx.getRuleChainActor().tell(SendToRuleMessage.builder()
                .msg(msg)
                .originNodeId(nodeCtx.getNode().getId())
                .relationTypes(Set.of(CommonConnectionType.TRUE))
                .build());
    }

    @Override
    public void tellFalse(RuleMessage msg) {
        nodeCtx.getRuleChainActor().tell(SendToRuleMessage.builder()
                .msg(msg)
                .originNodeId(nodeCtx.getNode().getId())
                .relationTypes(Set.of(CommonConnectionType.FALSE))
                .build());
    }

    @Override
    public String getSelfId() {
        return nodeCtx.getNode().getId();
    }

    @Override
    public String getSelfPath() {
        return nodeCtx.getSelfActor().path().toSerializationFormat();
    }
}
