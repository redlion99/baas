package com.rapple.baas.instant.actor

import akka.actor.Actor
import com.corundumstudio.socketio.{AckRequest, SocketIOClient}
import com.rapple.baas.instant.actor.Messages.Timeout
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
 * Created by libin on 14-11-25.
 */
abstract class ResponderActor extends Actor{

  //import context.dispatcher
  implicit val ec:ExecutionContext = context.dispatcher
  val timeoutMessenger=context.system.scheduler.scheduleOnce(10 seconds){
    self ! Timeout("timeout")
  }
}
