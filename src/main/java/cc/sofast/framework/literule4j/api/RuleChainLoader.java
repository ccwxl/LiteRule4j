package cc.sofast.framework.literule4j.api;

import cc.sofast.framework.literule4j.api.metadata.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wxl
 */
public class RuleChainLoader {
    static ObjectMapper mapper = new ObjectMapper();
    static ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public static RuleEngine loadFromJson(String json) throws Exception {
        RuleChinaDefinition definition = mapper.readValue(json, new TypeReference<>() {
        });

        Metadata metadata = definition.getMetadata();
        List<Node> nodes = metadata.getNodes();
        List<Connection> connections = metadata.getConnections();

        Node startNode = nodes.stream().filter(Node::getFirstNode).findFirst().orElseThrow(() -> new IllegalArgumentException("No start node found"));
        String startNodeId = startNode.getId();
        RuleEngine engine = new RuleEngine(startNodeId, executorService);

        Map<String, RuleNode> nodeMap = new HashMap<>();
        for (Node node : nodes) {
            String id = node.getId();
            String type = node.getType();
            RuleNode ruleNode = NodeFactory.creteRuleNode(id, type, node.getConfiguration());
            nodeMap.put(id, ruleNode);
            engine.addNode(ruleNode);
        }

        for (Connection connection : connections) {
            String fromId = connection.getFromId();
            String toId = connection.getToId();
            AbstractRuleNode ruleNode = (AbstractRuleNode) nodeMap.get(fromId);
            // 添加下一个执行节点
            ConnectionType type = connection.getType();
            ruleNode.addTransition(new NodeResult(type), toId);
        }
        return engine;
    }
}