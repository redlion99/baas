package com.rapple.baas.storage.actor

import com.rapple.baas.common.dto.InstantMessage

/**
 * Created by libin on 14-11-25.
 */
object Messages {
  case class InstantMessageSave(room:String,message:InstantMessage)
}

