package com.rapple.baas.push.actor

import akka.actor.Actor
import com.corundumstudio.socketio.SocketIOServer

import scala.concurrent.{Future, ExecutionContext}
import scala.util.Success

/**
 * Created by libin on 14-11-23.
 */
class PushActor(server:SocketIOServer) extends Actor{

  implicit val ec:ExecutionContext = context.dispatcher
  override def receive: Receive = {
    case Message(username,eventName,payload)=>
      println(server.getNamespace("").getRoomOperations("/user/private/" + username))
      val future=Future{
        server.getNamespace("").getRoomOperations("/user/private/" + username).sendEvent(eventName,payload)
      }
      future.onComplete{
        case Success(_) =>
        case _ =>
      }
  }
}

case class Message(username:String,eventName:String,payload:AnyRef)