package cc.sofast.framework.literule4j.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.util.HashMap;
import java.util.Map;

public class DeviceGroup extends AbstractActor {

    final Map<String, ActorRef> deviceIdToActor = new HashMap<>();

    @Override
    public Receive createReceive() {

        return null;
    }
}
