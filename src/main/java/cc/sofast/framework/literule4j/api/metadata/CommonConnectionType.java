package cc.sofast.framework.literule4j.api.metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用连接类型
 * @author wxl
 */
@Getter
@AllArgsConstructor
public enum CommonConnectionType implements ConnectionType {
    TRUE("True"),
    FALSE("False"),
    SUCCESS("Success"),
    FAILED("Failed");

    private final String type;

    public static ConnectionType of(String type) {
        for (CommonConnectionType value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }
}
