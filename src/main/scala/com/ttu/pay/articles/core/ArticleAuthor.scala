package com.ttu.pay.articles.core

import com.ttu.pay.common.NoneAsNullOptionEncoder.*
import com.ttu.pay.common.domain.Username
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class ArticleAuthor(username: Username, bio: Option[String], image: Option[String], following: Boolean)

object ArticleAuthor:
  given articleAuthorEncoder: JsonEncoder[ArticleAuthor] = DeriveJsonEncoder.gen[ArticleAuthor]
  given articleAuthorDecoder: JsonDecoder[ArticleAuthor] = DeriveJsonDecoder.gen[ArticleAuthor]
