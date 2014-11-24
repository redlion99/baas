package com.rapple.baas.instant

import akka.actor.{ActorSystem, Props, ActorContext}
import com.corundumstudio.socketio.{SocketIOServer, AckMode, Configuration}
import com.rapple.baas.instant.actor.EventDispatchActor
import com.rapple.baas.instant.actor.Messages._
import com.rapple.baas.instant.event.Dispatcher
import scala.collection.JavaConverters._

/**
 * Created by libin on 14-11-22.
 */
trait InstantLauncher {

  def start()(implicit ctx:ActorSystem,server:SocketIOServer): Unit ={

    val eventActor=ctx.actorOf(Props[EventDispatchActor],"event")
    eventActor ! Start("")
    new Dispatcher(eventActor).forwardEvents(server,List("login","chat").asJava)
  }
  def stop()(implicit ctx:ActorSystem,server:SocketIOServer): Unit ={

  }
}

object InstantLauncher extends InstantLauncher