package bmstu.hadoop.lab5;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreActor extends AbstractActor {
    private Map<String, ArrayList<String>> store = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, m -> {
                    if(store.containsKey(m.getPakcageID())) {
                        store.get(m.getPakcageID()).add(m.getResult());
                    } else {
                        ArrayList<String> value = new ArrayList<>();
                        value.add(m.getResult());
                        store.put(m.getPakcageID(), value);
                    }
                })
                .match(GetMessage.class, req -> {
                    System.out.println("GetActor");
                    sender().tell(
                            new ResponseMessage(req.getPackageID(), store.get(req.getPackageID())), self());
                }).build();
    }
}
