package cc.sofast.framework.literule4j.api;

import java.util.Set;

/**
 * @author wxl
 */
public interface RuleContext {
    void tellSuccess(RuleMessage msg);

    void tellFailure(RuleMessage msg, Throwable th);

    void tellNext(RuleMessage msg, String relationType);

    void tellNext(RuleMessage msg, Set<String> relationTypes);

    void tellTrue(RuleMessage msg);

    void tellFalse(RuleMessage msg);
}