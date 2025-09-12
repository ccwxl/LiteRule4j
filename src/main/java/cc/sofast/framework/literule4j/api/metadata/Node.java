package cc.sofast.framework.literule4j.api.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 节点
 * @author wxl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Node {
    private String id;
    private String name;
    private String type;
    private String description;
    private String debugMode;
    private Boolean firstNode = false;
    private Map<String, Object> configuration;
}
