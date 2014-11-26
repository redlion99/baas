package com.rapple.baas.storage

import akka.actor.{Props, ActorSystem}
import com.corundumstudio.socketio.SocketIOServer
import com.rapple.baas.storage.actor.StorageActor
import org.redisson.{Config, Redisson}

/**
 * Created by libin on 14-11-25.
 */
trait StorageLauncher {
  def start()(implicit ctx:ActorSystem,server:SocketIOServer): Unit ={
    val config= Config
    val redisson: Redisson=Redisson.create(config)
    val eventActor=ctx.actorOf(Props(new StorageActor(redisson)),"storage")
  }
}
