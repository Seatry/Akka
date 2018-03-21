package bmstu.hadoop.lab5;

import akka.NotUsed;
import akka.actor.*;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.japi.pf.DeciderBuilder;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import akka.http.javadsl.server.AllDirectives;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

public class JsTester extends AllDirectives {

    public static ActorRef storeActor;
    public static ActorRef TestRoute;

    private static final int TIME = 5000;

    private static final int MAX_RETRIES = 10;
    private static SupervisorStrategy strategy =
            new OneForOneStrategy(MAX_RETRIES,
                    Duration.create("1 minute"),
                    DeciderBuilder.
                            match(ArithmeticException.class, e -> resume()).
                            match(NullPointerException.class, e -> restart()).
                            match(IllegalArgumentException.class, e -> stop()).
                            matchAny(o -> escalate()).build());

    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("Tester");
        storeActor = system.actorOf(Props.create(StoreActor.class));
        TestRoute = system.actorOf(
                new RoundRobinPool(5)
                        .withSupervisorStrategy(strategy)
                        .props(Props.create(TestRunner.class)), "routerForTests");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        JsTester tester = new JsTester();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = tester.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read(); // let it run until user presses return

        binding
                .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                .thenAccept(unbound -> system.terminate()); // and shutdown when done
    }
    private Route createRoute() {
        return route(
                path("test", () ->
                        post(() ->  entity(Jackson.unmarshaller(Tests.class), m -> {
                            System.out.println("post()");
                            for (int i = 0; i < m.getSize(); i++) {
                                TestRoute.tell(new JsFunction(m.getJsScript(), m.getTests()
                                        .get(i).getParams(), m.getFunctionName(), m.getPackageId(),
                                        m.getTests().get(i).getExpectedResult()), ActorRef.noSender());
                            }
                            return complete("\nTESTED\n");
                        }))),
                path("response", () ->
                        get(() -> parameter("packageId", (packageId) -> {
                            System.out.println("get()");
                            Future<Object> result = Patterns.ask(storeActor,
                                    new GetMessage(packageId), TIME);
                            return completeOKWithFuture(result, Jackson.marshaller());
                        }))));
    }

}
