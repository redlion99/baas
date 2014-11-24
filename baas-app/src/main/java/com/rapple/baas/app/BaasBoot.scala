package com.rapple.baas.app

import akka.actor.ActorSystem
import com.rapple.baas.common.actor.ActorFactory
import com.rapple.baas.common.io.SocketIOServerLauncher
import com.rapple.baas.instant.InstantLauncher
import com.rapple.baas.push.PushLauncher

/**
 * Created by libin on 14-11-22.
 */
trait BaasBoot {
  val socketIOLauncher= new SocketIOServerLauncher
  implicit val server=socketIOLauncher.getServer
  socketIOLauncher.start()
  implicit val actorSystem:ActorSystem=ActorFactory.getActorSystem
  InstantLauncher.start()
  PushLauncher.start()

}
object BaasApp extends App with BaasBoot