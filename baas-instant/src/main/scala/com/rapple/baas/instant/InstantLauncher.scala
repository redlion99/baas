package com.rapple.baas.instant

import akka.actor.{ActorSystem, Props, ActorContext}
import com.corundumstudio.socketio.{SocketIOServer, AckMode, Configuration}
import com.rapple.baas.instant.actor.{ConversationActor, Adapter, EventRouterActor}
import com.rapple.baas.instant.actor.Messages._
import org.redisson.{Redisson, Config}
import scala.collection.JavaConverters._

/**
 * Created by libin on 14-11-22.
 */
trait InstantLauncher {

  def start()(implicit ctx:ActorSystem,server:SocketIOServer): Unit ={

    val eventActor=ctx.actorOf(Props[EventRouterActor],"event")
    eventActor ! Start("")
    val adapter=ctx.actorOf(Props(new Adapter(eventActor,server)),"adapter")
    adapter ! ForwardEvents(List("login","chat:join","chat:invite","chat:accept","chat:message","chat:history","chat:typing","user:present","bot:start","bot:end","bot:message"))



  }
  def stop()(implicit ctx:ActorSystem,server:SocketIOServer): Unit ={

  }
}

object InstantLauncher extends InstantLauncher