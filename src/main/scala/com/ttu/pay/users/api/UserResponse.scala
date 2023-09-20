package com.ttu.pay.users.api

import com.ttu.pay.users.User
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

case class UserResponse(
    user: User
)

object UserResponse:
  given userEncoder: zio.json.JsonEncoder[UserResponse] = DeriveJsonEncoder.gen[UserResponse]
  given userDecoder: zio.json.JsonDecoder[UserResponse] = DeriveJsonDecoder.gen[UserResponse]
