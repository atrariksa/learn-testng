package com.atrariksa.testng.test.simple;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.atrariksa.testng.simple.AkkaJavaGreeter;
import org.testng.Assert;
import org.testng.annotations.Test;
import static akka.pattern.Patterns.ask;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class AkkaJavaGreeterTest {

    @Test()
    public void testAkkaJavaGreeter() throws Exception {
        ActorSystem actorSystem = ActorSystem.create("test");
        ActorRef dika = actorSystem.actorOf(Props.create(AkkaJavaGreeter.class), "dika");
        ActorRef atrariksa = actorSystem.actorOf(Props.create(AkkaJavaGreeter.class), "atrariksa");
        AkkaJavaGreeter.Greeting greeting = new AkkaJavaGreeter.Greeting("hello, i am an ", "actor tester");
        CompletableFuture<Object> dikaResponse = ask(dika, greeting, Duration.ofMillis(1000)).toCompletableFuture();
        CompletableFuture<Object> atrariksaResponse = ask(atrariksa, greeting, Duration.ofMillis(1000)).toCompletableFuture();
        Assert.assertNotNull(dikaResponse.get());
        Assert.assertEquals(dikaResponse.get(), "hi, actor tester, my name is dika");
        Assert.assertNotNull(atrariksaResponse.get());
        Assert.assertEquals(atrariksaResponse.get(), "hi, actor tester, my name is atrariksa");
    }
}
