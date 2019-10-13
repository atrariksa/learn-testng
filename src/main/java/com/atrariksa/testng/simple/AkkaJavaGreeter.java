package com.atrariksa.testng.simple;

import akka.actor.AbstractActor;

import java.util.logging.Logger;

public class AkkaJavaGreeter extends AbstractActor {

    public static final String GREET = "hi, ";
    public static final String GREETING_RESPONSE = ", my name is ";
    private static final Logger logger = Logger.getGlobal();

    public static class Greeting {
        private String message;
        private String name;
        public Greeting(String message, String name) {
            this.message = message;
            this.name = name;
        }
        public String getMessage() {
            return message;
        }
        public String getName() { return name; }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Greeting.class, greeting -> {
            logger.info(greeting.getMessage());
            getSender().tell(GREET + greeting.getName() + GREETING_RESPONSE + getSelf().path().name(), getSelf());
        }).build();
    }
}
