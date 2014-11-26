package com.rapple.baas.app

import akka.actor.{ActorLogging, Actor, Props, ActorSystem}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import com.rapple.baas.common.actor.ActorFactory
import com.rapple.baas.common.io.SocketIOServerLauncher
import com.rapple.baas.instant.InstantLauncher
import com.rapple.baas.push.PushLauncher

/**
 * Created by libin on 14-11-22.
 */
trait BaasBoot {
  def startLaunchers(system:ActorSystem): Unit ={
    val socketIOLauncher= new SocketIOServerLauncher
    implicit val server=socketIOLauncher.getServer
    socketIOLauncher.start()
    InstantLauncher.start()
    PushLauncher.start()
  }
  //System.setProperty("akka.remote.netty.tcp.port", "2551")
  implicit val actorSystem:ActorSystem=ActorFactory.getActorSystem
  startLaunchers(actorSystem)
}

object BaasApp extends App with BaasBoot

object ClusterApp extends  BaasBoot{
  def main(args: Array[String]): Unit = {

    if (args.nonEmpty)
      System.setProperty("akka.remote.netty.tcp.port", args(0))
    else
      System.setProperty("akka.remote.netty.tcp.port", "0")

    // Create an Akka system
    implicit val system = ActorFactory.getActorSystem
    val clusterListener = system.actorOf(Props[SimpleClusterListener],
      name = "clusterListener")

    Cluster(system).subscribe(clusterListener, classOf[ClusterDomainEvent])

    startLaunchers(actorSystem)
  }
}

class SimpleClusterListener extends Actor with ActorLogging {
  def receive = {
    case state: CurrentClusterState ⇒
      log.info("Current members: {}", state.members.mkString(", "))
    case MemberUp(member) ⇒
      log.info("Member is Up: {}", member.address)
    case UnreachableMember(member) ⇒
      log.info("Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) ⇒
      log.info("Member is Removed: {} after {}",
        member.address, previousStatus)
    case _: ClusterDomainEvent ⇒ // ignore
  }
}