package cc.sofast.framework.literule4j.api;

import akka.actor.typed.ActorRef;
import cc.sofast.framework.literule4j.api.metadata.Node;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wxl
 */
@Getter
@Setter
public class RuleNodeCtx {
    private ActorRef<RuleMessage> ruleChainActor;
    private ActorRef<RuleMessage> selfActor;
    private Node node;

    public RuleNodeCtx() {
    }

    public RuleNodeCtx(ActorRef<RuleMessage> ruleChainActor, ActorRef<RuleMessage> nodeActor, Node node) {
        this.ruleChainActor = ruleChainActor;
        this.selfActor = nodeActor;
        this.node = node;
    }
}
