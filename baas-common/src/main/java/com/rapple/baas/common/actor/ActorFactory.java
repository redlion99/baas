package com.rapple.baas.common.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * Created by libin on 14-11-22.
 */
@Component
public class ActorFactory {
    private static ActorSystem actorSystem;
    private static Logger logger= LoggerFactory.getLogger(ActorFactory.class);

    @Bean
    public synchronized static ActorSystem getActorSystem(){
        if(null==actorSystem){
            logger.info("creating actor system!");
            actorSystem=ActorSystem.create("baas");
        }
        return actorSystem;
    }

    public ActorRef createActor(Class actorClass,String name){
        return getActorSystem().actorOf(Props.create(actorClass),name);
    }
}
