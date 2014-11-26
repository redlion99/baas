package com.rapple.baas.instant.actor

import akka.actor.{Actor, ActorRef, Props}
import com.corundumstudio.socketio.listener.{DisconnectListener, DataListener}
import com.corundumstudio.socketio.{AckRequest, SocketIOClient, SocketIOServer}
import com.fasterxml.jackson.databind.ObjectMapper
import com.rapple.baas.common.dto.User
import com.rapple.baas.instant.actor.Messages._

import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.util.Success


/**
 * Created by libin on 14-11-25.
 */
class Adapter(eventActor:ActorRef ,server: SocketIOServer) extends Actor{

  private val objectMapper: ObjectMapper = new ObjectMapper

  override def receive: Actor.Receive = {
    case ForwardEvents(events) =>

      events map{
        eventName=>
          server.addEventListener(eventName,classOf[java.util.HashMap[String,AnyRef]],new DataListener[java.util.HashMap[String,AnyRef]]{
            override def onData(client: SocketIOClient, data: java.util.HashMap[String,AnyRef], ackSender: AckRequest): Unit = {
              //val input=objectMapper.readValue(data,classOf[java.util.HashMap[String,AnyRef]]).asScala.toMap
              val input=data.asScala.toMap
              val userId=client.get[String]("username")
              val msg=UserInput(eventName,input,new User(userId,userId))

              val responder=context.actorOf(Props(new ResponderActor {
                override def receive: Receive = {

                  case RoomBroadcast(namespace:String,roomName:String,event:String,payload:Map[String,AnyRef]) =>
                    val future=Future{
                      server.getNamespace(namespace).getRoomOperations(roomName).sendEvent(event,payload.asJava)
                    }
                    future.onComplete{
                      case Success(x) =>
                      case _ =>
                    }

                  /* output operations */
                  case Output(event:String,data:Map[String,AnyRef]) =>
                    client.sendEvent(event,data.asJava)
                  case Ack(data) =>
                      ackSender.sendAckData(data.asJava)
                  case AckAndDone(data) =>
                    ackSender.sendAckData(data.asJava)
                    context.stop(self)

                   /* room operations */
                  case RoomJoin(roomName)=>
                    client.joinRoom(roomName)
                  case RoomLeave(roomName)=>
                    client.leaveRoom(roomName)
                  case UserPresent(roomName,user) =>
                    val future=Future{
                      client.getNamespace().getRoomOperations(roomName).sendEvent("user:present",user,roomName)
                    }
                    future.onComplete{
                      case Success(x) =>
                      case _ =>
                    }
                  case RoomMessage(roomName,data) =>
                    val future=Future{
                      client.getNamespace().getRoomOperations(roomName).sendEvent("chat:message",data.asJava)
                    }
                    future.onComplete{
                      case Success(x) =>
                      case _ =>
                    }

                  /* client operations */
                  case ClientSet(key,value)=>
                    client.set(key,value)

                  /* quit operations */
                  case Done(str)=>
                    context.stop(self)
                  case Timeout(str)=>
                    ackSender.sendAckData(str)
                    context.stop(self)
                  case _ => ackSender.sendAckData("")

                }
              }),eventName)

              eventActor.tell(msg,responder)
            }
          })
      }
    case _ =>
  }
}
