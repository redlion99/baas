package com.rapple.baas.instant.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import com.rapple.baas.common.dto.Result
import com.rapple.baas.instant.actor.Messages.Incoming

/**
 * Created by libin on 14-11-22.
 */
class ChatActor extends Actor{
  override def receive: Receive = {
    case Incoming("chat",ctx) =>ctx.ack.sendAckData(new Result("welcome"))
    case _=>

  }
}
