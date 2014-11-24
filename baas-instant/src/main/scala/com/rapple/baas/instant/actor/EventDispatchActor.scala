package com.rapple.baas.instant.actor

import akka.actor.{Props, Actor}

import Messages._
import com.rapple.baas.common.dto.Result

/**
 * Created by libin on 14-11-22.
 */
class EventDispatchActor extends Actor{
  override def receive: Receive = {
    case Start(_)=>
      val actorAuth=context.actorOf(Props[AuthenticateActor],"authenticate")
      println(actorAuth)
      val actorChat=context.actorOf(Props[ChatActor],"chat")
      println(actorChat)
    case Incoming("login",ctx) =>
      context.actorSelection("authenticate")! Incoming("login",ctx)
    case Incoming("chat",ctx) =>
      context.actorSelection("chat")! Incoming("chat",ctx)
    case Incoming(_,ctx) =>
      ctx.ack.sendAckData(new Result("",""))
  }
}
