package bmstu.hadoop.lab5client;


import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.*;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class Example {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);

        String data = null;

        try {
            data = new Scanner(new File("/home/alexander/mavenProjects/test.json")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final CompletionStage<HttpResponse> postFuture =
                Http.get(system)
                        .singleRequest(HttpRequest.POST("http://localhost:8080/test").withEntity(
                                MediaTypes.APPLICATION_JSON.toContentType(), data), materializer);
        System.out.println(postFuture.toCompletableFuture().get());
        String ready = new Scanner(System.in).next();
        final CompletionStage<HttpResponse> responseFuture =
                Http.get(system)
                        .singleRequest(HttpRequest.GET("http://localhost:8080/response?packageId=11"), materializer);
        System.out.println(responseFuture.toCompletableFuture().get());


    }
}
