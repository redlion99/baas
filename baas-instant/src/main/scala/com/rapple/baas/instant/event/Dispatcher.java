package com.rapple.baas.instant.event;

import akka.actor.ActorRef;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rapple.baas.instant.actor.Messages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by libin on 14-11-22.
 */
public class Dispatcher {


    private ActorRef actorRef;

    private ObjectMapper objectMapper=new ObjectMapper();

    public Dispatcher(ActorRef actorRef) {
        this.actorRef = actorRef;
    }


    public void forwardEvents(SocketIOServer server, List<String> events){
        Class n=String.class;
        for(final String eventName:events){
            server.addEventListener( eventName,n,new DataListener<String>() {
                @Override
                public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
                    Messages.Incoming msg=  new Messages.Incoming(eventName,new Messages.Context(client,objectMapper.readValue(String.valueOf(data),HashMap.class),ackSender));
                    actorRef.tell(msg, ActorRef.noSender());
                }
            });
        }
    }

}
