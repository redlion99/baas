package com.rapple.baas.instant.actor


import akka.actor.Actor
import akka.actor.Actor.Receive
import com.rapple.baas.common.dto.Result
import com.rapple.baas.instant.actor.Messages.{AckAndDone, UserInput, Ack}
import scala.collection.JavaConverters._

/**
 * Created by libin on 14-11-22.
 */
class AuthenticateActor extends Actor{
  override def receive: Receive = {
    case UserInput("login",data,_) =>
      println(data)
      val romanNumeral = Map(
        "1" -> "I", "2" -> "II", "3" -> "III"
      )
      sender ! AckAndDone(romanNumeral)
    case _ =>

  }
}
