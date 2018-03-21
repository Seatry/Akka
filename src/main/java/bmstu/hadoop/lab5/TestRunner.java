package bmstu.hadoop.lab5;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import static bmstu.hadoop.lab5.JsTester.storeActor;


public class TestRunner extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(JsFunction.class, m -> {
                    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                    engine.eval(m.getJscript());
                    Invocable invocable = (Invocable) engine;
                    String RunResult = invocable.invokeFunction(m.getFunctionName(),
                            m.getParams().toArray()).toString();
                    System.out.println("PostActor");
                    String result = (RunResult.equals(m.getExpectedResult()) ? RunResult +" : true"
                            : RunResult + " : false");
                    storeActor.tell(new StoreMessage(m.getPackageID(), result), ActorRef.noSender());
                }).build();
    }
}
