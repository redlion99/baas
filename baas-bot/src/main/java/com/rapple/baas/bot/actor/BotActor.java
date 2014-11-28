package com.rapple.baas.bot.actor;

import akka.actor.UntypedActor;
import com.rapple.baas.app.SpringContext;
import com.rapple.baas.bot.lucene.LuceneIndex;
import com.rapple.baas.common.BotBackend;
import com.rapple.baas.common.dto.Room;
import com.rapple.baas.instant.actor.Messages;
import org.springframework.beans.factory.BeanCreationException;

import java.io.IOException;

/**
 * Created by libin on 14-11-28.
 */
public class BotActor extends UntypedActor {

    private BotBackend botBackend;

    public BotActor() {
        botBackend= SpringContext.getBean(BotBackend.class);
        if(botBackend==null){
            throw new BeanCreationException("BotBackend");
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Messages.UserInput){
            Messages.UserInput input=(Messages.UserInput)message;
            Room room = new Room("chat with bot");
        }
    }
}
