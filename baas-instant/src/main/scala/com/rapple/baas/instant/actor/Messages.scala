package com.rapple.baas.instant.actor

import java.util

import com.corundumstudio.socketio.{AckRequest, SocketIOClient}
import com.rapple.baas.common.dto.{Room, InstantMessage, User}

/**
 * Created by libin on 14-11-22.
 */
object Messages {
  case class Context(client:SocketIOClient,data:java.util.HashMap[String,AnyRef],ack: AckRequest)
  case class UserInput(event:String,data:Map[String,AnyRef],user:User)
  case class Output(event:String,data:Map[String,AnyRef])
  case class Start(t:String)
  case class Done(t:String)
  case class Ack(data:Map[String,AnyRef])
  case class AckAndDone(data:Map[String,AnyRef])

  case class ForwardEvents(events: List[String])
  case class ClientSet(key:String,value:AnyRef)
  case class RoomBroadcast(namespace:String,roomName:String,event:String,data:Map[String,AnyRef])
  case class RoomEvent(roomName:String,data:Map[String,AnyRef])
  case class RoomJoin(roomName:String)
  case class RoomLeave(roomName:String)
  case class RoomFetchHistory(roomName:String)
  case class RoomMessages(roomName:String,data:util.Collection[InstantMessage])
  case class UserPresent(roomName:String,user:User)
  case class RespondTimeout(t:String)



  case class InstantMessageSave(room:String,message:InstantMessage)
  case class InstantMessageFetch(room:String)

  case class BotConversationCreate(botName:String)
  case class BotRoomCreated(room:Room)
}
