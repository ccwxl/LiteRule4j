package cc.sofast.framework.literule4j.api.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 动态字符串连接类型
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicStringConnectionType implements ConnectionType {
    private String type;

    public static ConnectionType of(String text) {
        return new DynamicStringConnectionType(text);
    }
}
