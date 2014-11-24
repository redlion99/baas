package com.rapple.baas.instant.actor

import java.util

import akka.actor.Actor
import akka.actor.Actor.Receive
import com.rapple.baas.common.dto.Result
import com.rapple.baas.instant.actor.Messages.Incoming
import scala.collection.JavaConverters._

/**
 * Created by libin on 14-11-22.
 */
class AuthenticateActor extends Actor{
  override def receive: Receive = {
    case Incoming("login",ctx) =>
      println(ctx.data.asScala)
      val romanNumeral = Map(
        1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V"
      )
      ctx.ack.sendAckData(new Result[Long](7777),"xxx",romanNumeral.asJava)
    case Incoming(_,ctx) =>
      ctx.ack.sendAckData("")
    case _ =>

  }
}
