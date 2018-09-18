package com.acacia.akka.sample1;

import akka.actor.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleApp1 {

    static class Counter extends AbstractActor {
        private int counter = 0;

        // protocol
        static class Message {

        }

        public Receive createReceive() {
            return receiveBuilder().match(Counter.Message.class, message -> onMesssage(message)).build();
        }

        private void onMesssage(Counter.Message message) throws InterruptedException {
            Thread.sleep(1000);
            counter++;
            log.info("Increased counter {}", counter);
        }

        public static Props props() {
            return Props.create(Counter.class);
        }
    }

    public static void main(String[] args) {
        log.info("main");
        ActorSystem actorSystem = ActorSystem.create("sample1");

        for (int i = 0; i < 150; i++) {
            ActorRef counter = actorSystem.actorOf(Counter.props(), String.valueOf(i));
            counter.tell(new Counter.Message(), ActorRef.noSender());
        }

        log.info("Done");
    }
}
