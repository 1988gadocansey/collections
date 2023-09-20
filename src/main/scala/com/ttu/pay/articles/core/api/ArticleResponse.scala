package com.ttu.pay.articles.core.api

import com.ttu.pay.articles.core.Article
import com.ttu.pay.articles.core.ArticleSlug.*
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class ArticleResponse(article: Article)

object ArticleResponse:
  given articleEncoder: JsonEncoder[ArticleResponse] = DeriveJsonEncoder.gen[ArticleResponse]
  given articleDecoder: JsonDecoder[ArticleResponse] = DeriveJsonDecoder.gen[ArticleResponse]
