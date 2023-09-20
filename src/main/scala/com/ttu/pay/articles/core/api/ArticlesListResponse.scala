package com.ttu.pay.articles.core.api

import com.ttu.pay.articles.core.Article
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class ArticlesListResponse(
    articles: List[Article],
    articlesCount: Int
)

object ArticlesListResponse:
  given articleDataEncoder: JsonEncoder[ArticlesListResponse] = DeriveJsonEncoder.gen[ArticlesListResponse]
  given articleDataDecoder: JsonDecoder[ArticlesListResponse] = DeriveJsonDecoder.gen[ArticlesListResponse]
