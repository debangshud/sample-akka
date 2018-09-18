package com.acacia.akka.sample2;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleApp2 {

    @Slf4j
    static class Alarm extends AbstractActor {

        static class Activity {

        }

        static class Disable {

        }

        static class Enable {

        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(Disable.class, disable -> log.info("Disabled"))
                    .match(Enable.class, enable -> log.info("Enabled"))
                    .match(Activity.class,activity -> log.info("Activity"))
                    .build();
        }

        public static Props props() {
            return Props.create(Alarm.class);
        }
    }

    public static void main(String[] args) {
        log.info("main");
        ActorSystem sample2 = ActorSystem.create("sample2");
        ActorRef alarm = sample2.actorOf(Alarm.props(), "alarm");
        alarm.tell(new Alarm.Enable(), ActorRef.noSender());
        alarm.tell(new Alarm.Disable(), ActorRef.noSender());
        alarm.tell(new Alarm.Activity(),ActorRef.noSender());

        ActorRef enable = sample2.actorOf(Alarm.props(), "enable");
        ActorRef disable = sample2.actorOf(Alarm.props(), "disable");
        ActorRef activity = sample2.actorOf(Alarm.props(), "activity");
        enable.tell(new Alarm.Enable(), ActorRef.noSender());
        disable.tell(new Alarm.Disable(), ActorRef.noSender());
        activity.tell(new Alarm.Activity(),ActorRef.noSender());

    }
}
