package cc.sofast.framework.literule4j.akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

public class RouterExample {

    public static class Worker extends AbstractBehavior<String> {

        public Worker(ActorContext<String> context) {
            super(context);
        }

        @Override
        public Receive<String> createReceive() {
            return newReceiveBuilder()
                    .onMessage(String.class, this::onMessage).build();
        }

        private Behavior<String> onMessage(String msg) {
            System.out.println(getContext().getSelf() + " got message: " + msg);
            return Behaviors.same();
        }
    }

    public static Behavior<String> root() {
        return Behaviors.setup(context -> {
            // ⚠️ 关键：要想看到具体是哪个 worker，需要每个 worker 自己打印 ID
            // 另一种做法：用 PoolRouter 的 setup 回调生成 worker ID


            return (Behavior<String>) Routers.pool(3,
                    Behaviors.<String>setup(Worker::new)
            ).withRoundRobinRouting();
        });
    }

    public static void main(String[] args) {
        ActorSystem<String> system = ActorSystem.<String>create(root(), "router-system");
        // 发送 5 条消息
        for (int i = 0; i < 5; i++) {
            system.tell("msg-" + i);
        }
    }
}