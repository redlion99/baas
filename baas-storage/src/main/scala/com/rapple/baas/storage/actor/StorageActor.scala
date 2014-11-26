package com.rapple.baas.storage.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import com.rapple.baas.common.dto.InstantMessage
import com.rapple.baas.storage.Util
import com.rapple.baas.storage.actor.Messages.InstantMessageSave
import com.rapple.baas.storage.store.EntityCollection
import org.redisson.Redisson
import Util._

import scala.concurrent.Future

/**
 * Created by libin on 14-11-25.
 */
class StorageActor(redisson: Redisson)  extends Actor{
  override def receive: Receive = {
    case InstantMessageSave(roomId:String,message:InstantMessage) =>
      val collection=new EntityCollection[InstantMessage](redisson,classOf[InstantMessage],roomId)
      implicit val ec=context.dispatcher
      val future= Future{
        collection.append(message)
      }
      future.onSuccess{
        case _=>
      }

  }
}
