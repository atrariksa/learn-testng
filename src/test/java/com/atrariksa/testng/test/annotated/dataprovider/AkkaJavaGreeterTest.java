package com.atrariksa.testng.test.annotated.dataprovider;

import akka.actor.*;
import com.atrariksa.testng.simple.AkkaJavaGreeter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static akka.pattern.Patterns.ask;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class AkkaJavaGreeterTest {

    private String firstName;
    private String lastName;
    private ActorSystem actorSystem;

    @BeforeTest
    void setup() {
        firstName = "dika";
        lastName = "atrariksa";
        actorSystem = ActorSystem.create("test");
        ActorRef actor = actorSystem.actorOf(Props.create(AkkaJavaGreeter.class), firstName);
        actorSystem.actorOf(Props.create(AkkaJavaGreeter.class), lastName);
    }

    @DataProvider
    public Object[][] greeting() {
        return new Object[][]{
                { "hello, i am an ", "actor tester" },
                { "good day, i am ", "actor tester#2" }
        };
    }

    @Test(dataProvider = "greeting")
    public void testAkkaJavaGreeter(final String message, final String senderName) throws Exception {

        ActorRef dika = actorSystem.actorFor("akka://test/user/" + firstName);
        ActorRef atrariksa = actorSystem.actorFor("akka://test/user/" + lastName);
        AkkaJavaGreeter.Greeting greeting = new AkkaJavaGreeter.Greeting(message, senderName);
        CompletableFuture<Object> dikaResponse = ask(dika, greeting, Duration.ofMillis(1000)).toCompletableFuture();
        CompletableFuture<Object> atrariksaResponse = ask(atrariksa, greeting, Duration.ofMillis(1000)).toCompletableFuture();
        Assert.assertNotNull(dikaResponse.get());
        Assert.assertEquals(dikaResponse.get(), "hi, "+ senderName +", my name is " + firstName);
        Assert.assertNotNull(atrariksaResponse.get());
        Assert.assertEquals(atrariksaResponse.get(), "hi, "+ senderName +", my name is "  + lastName);

    }
}
