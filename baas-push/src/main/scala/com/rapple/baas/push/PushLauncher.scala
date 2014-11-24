package com.rapple.baas.push

import akka.actor.{Props, ActorSystem}
import com.corundumstudio.socketio.SocketIOServer
import com.rapple.baas.push.actor.{Message, PushActor}

/**
 * Created by libin on 14-11-23.
 */
trait PushLauncher {
  def start()(implicit ctx:ActorSystem,server:SocketIOServer): Unit ={
    val pushActor=ctx.actorOf(props = Props(new PushActor(server)),name = "push")
    pushActor ! Message("jacky","hello","world")
  }
}

object PushLauncher extends PushLauncher