package cc.sofast.framework.literule4j.api;

import akka.actor.typed.ActorRef;
import cc.sofast.framework.literule4j.actor.message.ActorMsg;
import cc.sofast.framework.literule4j.api.metadata.Node;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wxl
 */
@Getter
@Setter
public class RuleNodeCtx {
    private ActorRef<ActorMsg> ruleChainActor;
    private ActorRef<ActorMsg> selfActor;
    private Node node;

    public RuleNodeCtx() {
    }

    public RuleNodeCtx(ActorRef<ActorMsg> ruleChainActor, ActorRef<ActorMsg> nodeActor, Node node) {
        this.ruleChainActor = ruleChainActor;
        this.selfActor = nodeActor;
        this.node = node;
    }
}
