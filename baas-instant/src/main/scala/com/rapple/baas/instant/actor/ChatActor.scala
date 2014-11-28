package com.rapple.baas.instant.actor

import akka.actor.Actor
import akka.pattern.{ ask, pipe }
import akka.util.Timeout
import scala.concurrent.duration._
import Util.MatchCommand
import com.rapple.baas.common.dto.{InstantMessage, User}
import com.rapple.baas.instant.actor.Messages._

/**
 * Created by libin on 14-11-22.
 */
class ChatActor extends Actor with Instant{
  implicit val ec=context.dispatcher
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
        (roomId) =>
          val roomPath="/public/"+roomId.toString
          val content:String=data.get("message").getOrElse("").toString
          sender ! RoomEvent(roomPath,Map("user" -> user , "message" -> content))
          val conversationActor=context.actorSelection("../conversation")
          conversationActor ! InstantMessageSave(roomId.toString,new InstantMessage(user,content))
      }
      sender ! AckAndDone(data)
    case UserInput(MatchCommand(domain,"history"),data,user) =>
      data.get("room").foreach{
        (roomId) =>
          val roomPath="/public/"+roomId.toString
          val conversationActor=context.actorSelection("../conversation")
          implicit val timeout = Timeout(1 seconds)
          val result=conversationActor ? InstantMessageFetch(roomId.toString)
          val originalSender=sender
          result.mapTo[RoomMessages].map(roomMessages =>
            originalSender ! AckAndDone(Map("room"->roomMessages.roomName,"messages"->roomMessages.data))
          )
      }
    case UserInput(MatchCommand(domain,method),data,user) =>
      sender ! AckAndDone(data)
    case _=>
  }
}
