package com.ttu.pay.articles.comments

import com.ttu.pay.common.NoneAsNullOptionEncoder.*
import com.ttu.pay.common.domain.Username
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class CommentAuthor(username: Username, bio: Option[String], image: Option[String], following: Boolean)

object CommentAuthor:
  given articleAuthorEncoder: JsonEncoder[CommentAuthor] = DeriveJsonEncoder.gen[CommentAuthor]
  given articleAuthorDecoder: JsonDecoder[CommentAuthor] = DeriveJsonDecoder.gen[CommentAuthor]
