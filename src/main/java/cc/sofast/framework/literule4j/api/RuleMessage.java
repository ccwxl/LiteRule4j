package cc.sofast.framework.literule4j.api;

import java.util.Map;

/**
 * @author wxl
 */
public interface RuleMessage {

    String getMsgType();

    String getRuleChainId();

    void setRuleChainId(String ruleChainId);

    Map<String, Object> getData();

    Map<String, Object> getMetadata();
}
