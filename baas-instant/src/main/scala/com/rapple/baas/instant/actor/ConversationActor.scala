package com.rapple.baas.instant.actor

import akka.actor.Actor
import com.rapple.baas.common.dto.InstantMessage
import com.rapple.baas.instant.actor.Messages.{RoomMessages, InstantMessageFetch, InstantMessageSave}
import com.rapple.baas.storage.store.EntityCollection
import org.redisson.Redisson

import scala.concurrent.Future

/**
 * Created by libin on 14-11-25.
 */
class ConversationActor(redisson: Redisson)  extends Actor{
  implicit val ec=context.dispatcher
  override def receive: Receive = {
    case InstantMessageSave(roomId:String,message:InstantMessage) =>
      val collection=new EntityCollection[InstantMessage](redisson,classOf[InstantMessage],roomId)
      val future= Future{
        collection.append(message)
      }
      future.onSuccess{
        case _=>
      }
    case InstantMessageFetch(roomId:String)=>
      val collection=new EntityCollection[InstantMessage](redisson,classOf[InstantMessage],roomId)

      val originalSender=sender()
      val future= Future{
        originalSender!RoomMessages(roomId,collection.tail(0))
      }
      future.onSuccess{
        case _=>
      }
  }
}
