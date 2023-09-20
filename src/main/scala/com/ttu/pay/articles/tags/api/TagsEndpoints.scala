package com.ttu.pay.articles.tags.api

import com.ttu.pay.common.BaseEndpoints
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import zio.ZLayer

import scala.util.chaining.*

class TagsEndpoints(base: BaseEndpoints):

  val getTagsEndpoint = base.publicEndpoint.get
    .in("api" / "tags")
    .out(jsonBody[TagsListResponse])

object TagsEndpoints:
  val live: ZLayer[BaseEndpoints, Nothing, TagsEndpoints] = ZLayer.fromFunction(new TagsEndpoints(_))
