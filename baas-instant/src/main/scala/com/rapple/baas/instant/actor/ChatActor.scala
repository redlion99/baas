package com.rapple.baas.instant.actor

import akka.actor.Actor
import Util.MatchCommand
import com.rapple.baas.common.dto.User
import com.rapple.baas.instant.actor.Messages._

/**
 * Created by libin on 14-11-22.
 */
class ChatActor extends Actor with Instant{
  override def receive: Receive = {
    case UserInput(MatchCommand(domain,"join"),data,user) =>
      data.get("room").foreach{
        (room) =>
          val roomName="/public/"+room.toString
          sender ! RoomJoin(roomName)
          sender ! UserPresent(roomName,user)
      }
      sender ! AckAndDone(data)
    case UserInput(MatchCommand(domain,"message"),data,user) =>
      data.get("room").foreach{
        (room) =>
          val roomName="/public/"+room.toString
          sender ! RoomMessage(roomName,Map("user" -> user , "message" -> data.get("message").getOrElse("")))
      }
      sender ! AckAndDone(data)
    case UserInput(MatchCommand(domain,method),data,user) =>
      sender ! AckAndDone(data)
    case _=>
  }
}
