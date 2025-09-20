# LiteRule4j

**LiteRule4j** 是一个用 Java 编写的轻量级规则引擎，基于 Akka Actor 实现，适用于物联网 (IoT)、规则链处理等场景。

---

## 🔍 特性

- 基于 **Akka Typed / Actor 模型**，支持异步、并发和高扩展性
- 支持规则链（Rule Chain）：多个 `RuleNode` 顺序或并行处理
- 节点与节点之间的 **连接关系 (Connection/Metadata)** 可灵活配置
- 支持容错 (Fault Tolerance)、路由 (Router) 等特性
- 易于集成，扩展性好

---

## 📦 项目结构 (简要)

| 模块 / 包            | 功能描述                                                          |
|-------------------|---------------------------------------------------------------|
| `actor`           | 基于 Akka 的 Actor 架构，包括 AppActor、RuleChainActor、RuleNodeActor 等 |
| `actor.lifecycle` | 定义消息类型（如 RuleChinaInitMessage、RuleEngineMessage 等）            |
| `api.metadata`    | 规则链数据模型，包括 RuleChinaDefinition、Node、Connection、Metadata 等     |
| `api`             | 上下文支持（RuleNodeCtx、ActorSystemContext 等）                       |

---

## 🚀 快速开始

1. 克隆项目

   ```bash
   git clone https://github.com/ccwxl/LiteRule4j.git
   cd LiteRule4j
   ```

2. 构建

   ```bash
   mvn clean package
   ```

3. 在项目中引入依赖或直接引用源码

4. 定义规则链并初始化 Actor 系统

   ```java
    RuleMessage message = RuleMessage.builder()
                        .ruleChainId("chain_has_sub_chain_node")
                        .msgType("rule_chain_init")
                        .putData("index", i)
                        .build();
    controller.exec(message);
   ```

---

## ⚙️ 扩展特性

- 可配置 **Router**（RoundRobin、PoolRouter 等）提升并发能力
- 可配置 **SupervisorStrategy** 实现失败恢复
- 支持 Dispatcher、Mailbox 等扩展 (TODO)
- 集群模式 (TODO)

---

## 🧩 适用场景

- IoT 设备消息处理
- 报警/监控规则触发
- 动态规则链执行

---

## 📄 协议

请查看 `LICENSE` 文件。
