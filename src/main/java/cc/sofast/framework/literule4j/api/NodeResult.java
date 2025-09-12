package cc.sofast.framework.literule4j.api;

import cc.sofast.framework.literule4j.api.metadata.CommonConnectionType;
import cc.sofast.framework.literule4j.api.metadata.Connection;
import cc.sofast.framework.literule4j.api.metadata.ConnectionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 节点执行结果
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeResult {
    public static final NodeResult SUCCESS = new NodeResult(CommonConnectionType.SUCCESS);
    public static final NodeResult FAILED = new NodeResult(CommonConnectionType.FAILED);

    public static final NodeResult TRUE = new NodeResult(CommonConnectionType.TRUE);
    public static final NodeResult FALSE = new NodeResult(CommonConnectionType.FALSE);

    private ConnectionType result;

    public static NodeResult of(String msgType) {
        return new NodeResult(Connection.ConnectionTypeDeserializer.of(msgType));
    }
}
