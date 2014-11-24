package com.rapple.baas.common.io;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by libin on 14-11-23.
 */
public class MyAuthorizationListener implements AuthorizationListener {
    Logger logger = LoggerFactory.getLogger(MyAuthorizationListener.class);
    @Override
    public boolean isAuthorized(HandshakeData data) {
        logger.info("handshake authorization for "+data.getSingleUrlParam("username"));
        return true;
    }
}
