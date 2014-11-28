package com.rapple.baas.storage

import akka.actor.{Props, ActorSystem}
import com.corundumstudio.socketio.SocketIOServer
import org.redisson.{Config, Redisson}

/**
 * Created by libin on 14-11-25.
 */
trait StorageLauncher {
  def start()(implicit ctx:ActorSystem,server:SocketIOServer): Unit ={

  }
}
