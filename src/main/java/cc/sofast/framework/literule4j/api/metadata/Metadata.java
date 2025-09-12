package cc.sofast.framework.literule4j.api.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 元数据
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Metadata {

    /**
     * 节点
     */
    private List<Node> nodes;

    /**
     * 连接
     */
    private List<Connection> connections;
}
