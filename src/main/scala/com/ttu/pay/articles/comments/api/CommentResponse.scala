package com.ttu.pay.articles.comments.api

import com.ttu.pay.articles.comments.Comment
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class CommentResponse(comment: Comment)

object CommentResponse:
  given commentEncoder: JsonEncoder[CommentResponse] = DeriveJsonEncoder.gen
  given commentDecoder: JsonDecoder[CommentResponse] = DeriveJsonDecoder.gen
