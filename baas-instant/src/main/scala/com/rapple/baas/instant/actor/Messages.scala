package com.rapple.baas.instant.actor

import com.corundumstudio.socketio.{AckRequest, SocketIOClient}

/**
 * Created by libin on 14-11-22.
 */
object Messages {
  case class Context(client:SocketIOClient,data:java.util.HashMap[String,AnyRef],ack: AckRequest)
  case class Incoming(event:String,ctx:Context)
  case class Start(t:String)
}
