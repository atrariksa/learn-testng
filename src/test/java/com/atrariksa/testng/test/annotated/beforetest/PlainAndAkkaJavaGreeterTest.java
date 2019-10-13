package com.atrariksa.testng.test.annotated.beforetest;

import com.atrariksa.testng.simple.PlainJavaGreeter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.atrariksa.testng.simple.AkkaJavaGreeter;
import static akka.pattern.Patterns.ask;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;


public class PlainAndAkkaJavaGreeterTest {

    private String firstName;
    private String lastName;
    private AkkaJavaGreeter.Greeting greeting;

    @BeforeTest
    void setup() {
        firstName = "dika";
        lastName = "atrariksa";
        greeting = new AkkaJavaGreeter.Greeting("hello, i am an ", "actor tester");
    }

    @Test()
    public void testPlainJavaGreeter() {

        PlainJavaGreeter plainJavaGreeter = new PlainJavaGreeter();
        Assert.assertNotNull(plainJavaGreeter.greet());
        Assert.assertEquals(plainJavaGreeter.greet(), "hello");
        Assert.assertNotNull(plainJavaGreeter.greet("notNull"));
        Assert.assertEquals(plainJavaGreeter.greet(lastName), "hello, " + lastName);
    }

    @Test()
    public void testAkkaJavaGreeter() {
        try {
            ActorSystem actorSystem = ActorSystem.create("test");
            ActorRef dika = actorSystem.actorOf(Props.create(AkkaJavaGreeter.class), firstName);
            ActorRef atrariksa = actorSystem.actorOf(Props.create(AkkaJavaGreeter.class), lastName);
            CompletableFuture<Object> dikaResponse = ask(dika, greeting, Duration.ofMillis(1000)).toCompletableFuture();
            CompletableFuture<Object> atrariksaResponse = ask(atrariksa, greeting, Duration.ofMillis(1000)).toCompletableFuture();
            Assert.assertNotNull(dikaResponse.get());
            Assert.assertEquals(dikaResponse.get(), "hi, actor tester, my name is " + firstName);
            Assert.assertNotNull(atrariksaResponse.get());
            Assert.assertEquals(atrariksaResponse.get(), "hi, actor tester, my name is " + lastName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
