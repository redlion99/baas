package com.rapple.baas.common.io;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.corundumstudio.socketio.store.StoreFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by libin on 14-11-23.
 */
public class SocketIOServerLauncher {

    private Logger logger= LoggerFactory.getLogger(SocketIOServerLauncher.class);
    private SocketIOServer server;

    public SocketIOServerLauncher() {
        Configuration config = new Configuration();
        Config conf = ConfigFactory.load();
        config.setHostname("0.0.0.0");
        config.setPort(9979);
        if(conf.hasPath("socket-io")){
            Config ioConfig = conf.getConfig("socket-io");
            if(null!=ioConfig && null!=ioConfig.getString("hostname")){
                config.setHostname(ioConfig.getString("hostname"));
            }
            if(null!=ioConfig && ioConfig.getInt("port")>0){
                config.setPort(ioConfig.getInt("port"));
            }
        }

        config.setAckMode(AckMode.MANUAL);
        config.setTransports(Transport.WEBSOCKET);
        config.setAuthorizationListener(new MyAuthorizationListener());
        //config.setStoreFactory(new RedissonStoreFactory());
        server=new SocketIOServer(config);
    }

    public void start(){
        server.start();

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                logger.info("client connected!");
                String username=client.getHandshakeData().getSingleUrlParam("username");
                client.set("username",username);
                client.joinRoom("/user/private/"+username);
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                for(String room:client.getAllRooms()){
                    client.getNamespace().getRoomOperations(room).sendEvent("user:leave",client.get("username"),room);
                }
            }
        });
    }

    public void stop(){
        server.stop();
    }

    public SocketIOServer getServer() {
        return server;
    }
}
