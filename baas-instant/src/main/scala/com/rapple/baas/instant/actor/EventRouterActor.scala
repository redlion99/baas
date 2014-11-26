package com.rapple.baas.instant.actor

import akka.actor.{Props, Actor}

import Messages._
import Util.MatchCommand

/**
 * Created by libin on 14-11-22.
 */
class EventRouterActor extends Actor with Instant{

  override def receive: Receive = {
    case Start(_)=>
      val actorAuth=context.actorOf(Props[AuthenticateActor],"authenticate")
      println(actorAuth)
      val actorChat=context.actorOf(Props[ChatActor],"chat")
      println(actorChat)
    case UserInput("login",data,user) =>
      //sender is a responder
      context.actorSelection("authenticate").tell(UserInput("login",data,user),sender)
    case UserInput(MatchCommand("chat",method),data,user) =>
      //sender is a responder
      context.actorSelection("chat").tell(UserInput("chat:"+method,data,user),sender)
    case UserInput(MatchCommand(domain,method),data,user) =>
      sender!Ack(Map (
        "section" -> domain, "method" -> method
        ))
      sender!Done("")
    case _ =>
      println("unknown command")
  }
}
