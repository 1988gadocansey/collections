package com.ttu.pay.users

import com.ttu.pay.common.NoneAsNullOptionEncoder.*
import com.ttu.pay.common.domain.Username
import sttp.tapir.{Schema, SchemaType}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}
case class Profile(username: Username, bio: Option[String], image: Option[String], following: Boolean)

object Profile:
  given profileDataEncoder: JsonEncoder[Profile] = DeriveJsonEncoder.gen[Profile]
  given profileDataDecoder: JsonDecoder[Profile] = DeriveJsonDecoder.gen[Profile]
