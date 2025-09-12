package cc.sofast.framework.literule4j.api;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author wxl
 */
public class RuleEngine {
    private final Map<String, RuleNode> nodes = new ConcurrentHashMap<>();
    private final String startNodeId;
    private final ExecutorService executor;
    // 性能优化：预分配集合容量，减少扩容
    private static final int DEFAULT_QUEUE_CAPACITY = 64;
    private static final int DEFAULT_VISITED_CAPACITY = 32;
    private static final int MAX_DEPTH = 1000;
    // 性能优化：重用StringBuilder，减少GC压力
    private final StringBuilder traceBuilder = new StringBuilder(256);


    public RuleEngine(String startNodeId, ExecutorService executor) {
        this.startNodeId = startNodeId;
        this.executor = executor;
    }

    /**
     * 添加规则节点
     */
    public void addNode(RuleNode node) {
        nodes.put(node.getId(), node);
    }

    /**
     * 运行规则引擎
     */
    public void run(RuleContext ctx) {
        // 参数校验
        if (ctx == null) {
            throw new IllegalArgumentException("RuleContext cannot be null");
        }
        if (startNodeId == null || startNodeId.trim().isEmpty()) {
            ctx.addTrace("Warning: No start node configured");
            return;
        }

        // 性能优化：预分配集合容量
        Set<String> visitedNodes = new HashSet<>(DEFAULT_VISITED_CAPACITY);
        Deque<String> queue = new ArrayDeque<>(DEFAULT_QUEUE_CAPACITY);

        // 执行统计
        int executedNodes = 0;
        int currentDepth = 0;

        // 性能优化：缓存开始时间，避免重复调用
        long executionStartTime = System.currentTimeMillis();
        queue.addLast(startNodeId);

        while (!queue.isEmpty() && currentDepth < MAX_DEPTH) {
            String nodeId = queue.removeFirst();

            // 检查是否已访问过（防止循环）
            if (visitedNodes.contains(nodeId)) {
                // 性能优化：减少字符串拼接
                addTraceOptimized(ctx, "Warning: Detected cycle at node[", nodeId, "], skipping");
                continue;
            }

            RuleNode node = nodes.get(nodeId);
            if (node == null) {
                addTraceOptimized(ctx, "Warning: Node[", nodeId, "] not found, skipping");
                continue;
            }

            // 标记为已访问
            visitedNodes.add(nodeId);
            executedNodes++;
            currentDepth++;

            // 性能优化：使用基本类型，避免装箱
            NodeResult result = NodeResult.SUCCESS;
            // 使用纳秒精度
            long nodeStartTime = System.nanoTime();

            try {
                // 执行规则节点
                result = executeNode(node, ctx);
                // 性能优化：延迟计算执行时间，只在需要时计算
                // 限制日志数量
                if (ctx.getTrace().size() < 100) {
                    // 转换为毫秒
                    long executionTime = (System.nanoTime() - nodeStartTime) / 1_000_000;
                    addTraceOptimized(ctx, "Node[", nodeId, "] executed in ", String.valueOf(executionTime), "ms with result: ", String.valueOf(result.getResult()));
                }
            } catch (Exception e) {
                long executionTime = (System.nanoTime() - nodeStartTime) / 1_000_000;
                addTraceOptimized(ctx, "Node[", nodeId, "] failed after ", String.valueOf(executionTime), "ms: ", e.getMessage());
                result = NodeResult.FAILED;
            } finally {
                // 无论成功还是失败，都根据结果获取下一节点
                List<String> nextIds = node.getTransitions().get(result);
                if (nextIds != null && !nextIds.isEmpty()) {
                    // 性能优化：预检查，避免不必要的循环
                    for (String nextId : nextIds) {
                        // 使用isEmpty()比trim().isEmpty()更快
                        if (nextId != null && !nextId.isEmpty()) {
                            queue.addLast(nextId);
                        }
                    }
                }
            }
        }

        // 性能优化：减少字符串操作
        if (currentDepth >= MAX_DEPTH) {
            addTraceOptimized(ctx, "Warning: Reached maximum execution depth (", String.valueOf(MAX_DEPTH), "), execution stopped");
        }

        long totalExecutionTime = System.currentTimeMillis() - executionStartTime;
        addTraceOptimized(ctx, "Rule execution completed: ", String.valueOf(executedNodes), " nodes executed, depth: ", String.valueOf(currentDepth), ", total time: ", String.valueOf(totalExecutionTime), "ms");
    }

    /**
     * 性能优化：使用StringBuilder减少字符串拼接的GC压力
     */
    private void addTraceOptimized(RuleContext ctx, String... parts) {
        // 限制最大日志数量
        if (ctx.getTrace().size() >= 1000) {
            return;
        }
        // 重用StringBuilder
        traceBuilder.setLength(0);
        for (String part : parts) {
            if (part != null) {
                traceBuilder.append(part);
            }
        }
        ctx.addTrace(traceBuilder.toString());
    }

    /**
     * 执行规则节点
     */
    private NodeResult executeNode(RuleNode node, RuleContext ctx) throws Exception {
        return node.execute(ctx);
    }

    /**
     * 执行规则节点（基于CompletableFuture）
     */
    private NodeResult executeNode2(RuleNode node, RuleContext ctx) throws Exception {
        return java.util.concurrent.CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return node.execute(ctx);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executor)
                .join();
    }

    /**
     * 销毁规则引擎
     */
    public void destroy() {
        for (RuleNode node : nodes.values()) {
            node.destroy();
        }
    }

    /**
     * 重新加载规则引擎
     */
    public void reLoad() {
        //1. 创建新的节点
        //2. 直接替换nodes里的节点，注意锁的控制
        //3. 销毁节点
    }

}