# LiteRule4j

**LiteRule4j** is a lightweight rule engine written in Java, built on **Akka Actor**.  
It is designed for IoT message processing, rule chains, and event-driven workflows.

---

## 🔍 Features

- Built on **Akka Typed / Actor Model**, supports async & concurrency
- Supports **Rule Chains**: multiple `RuleNode` executed in sequence or parallel
- Flexible **Connections (Metadata)** between nodes
- Fault tolerance with **SupervisorStrategy**
- Easy to integrate and extend

---

## 📦 Project Structure (Simplified)

| Module / Package  | Description                                                             |
|-------------------|-------------------------------------------------------------------------|
| `actor`           | Core Akka actors (AppActor, RuleChainActor, RuleNodeActor)              |
| `actor.lifecycle` | Message definitions (RuleChinaInitMessage, RuleEngineMessage, etc.)     |
| `api.metadata`    | Rule chain data model (RuleChinaDefinition, Node, Connection, Metadata) |
| `api`             | Context support (RuleNodeCtx, ActorSystemContext)                       |

---

## 🚀 Getting Started

1. Clone the repo

   ```bash
   git clone https://github.com/ccwxl/LiteRule4j.git
   cd LiteRule4j
   ```

2. Build

   ```bash
   mvn clean package
   ```

3. Import into your project

4. Define a RuleChain and initialize actors

   ```java
    RuleMessage message = RuleMessage.builder()
                        .ruleChainId("chain_has_sub_chain_node")
                        .msgType("rule_chain_init")
                        .putData("index", i)
                        .build();
    controller.exec(message);
   ```

---

## ⚙️ Extensions

- **Router** (RoundRobin, PoolRouter) for parallelism
- **SupervisorStrategy** for fault recovery
- Dispatcher, mailbox configs (TODO)

---

## 🧩 Use Cases

- IoT device message processing
- Alerting and monitoring rules
- Dynamic rule chain execution

---

## 📄 License

See `LICENSE` file.
