package com.rapple.baas.push.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import com.corundumstudio.socketio.SocketIOServer

/**
 * Created by libin on 14-11-23.
 */
class PushActor(server:SocketIOServer) extends Actor{

  override def receive: Receive = {
    case Message(username,eventName,payload)=>
      println(server.getNamespace("").getRoomOperations("/user/private/" + username))
      server.getNamespace("").getRoomOperations("/user/private/" + username).sendEvent(eventName,payload)
  }
}

case class Message(username:String,eventName:String,payload:AnyRef)