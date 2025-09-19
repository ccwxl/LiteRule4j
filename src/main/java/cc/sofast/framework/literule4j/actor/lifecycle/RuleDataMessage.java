package cc.sofast.framework.literule4j.actor.lifecycle;

import cc.sofast.framework.literule4j.api.RuleMessage;

import java.util.Map;

/**
 * @author wxl
 */
public interface RuleDataMessage extends RuleMessage {
    String getMsgType();

    String getRuleChainId();

    void setRuleChainId(String ruleChainId);

    Map<String, Object> getData();

    Map<String, Object> getMetadata();

}
