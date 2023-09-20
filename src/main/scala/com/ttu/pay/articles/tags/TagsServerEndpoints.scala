package com.ttu.pay.articles.tags

import com.ttu.pay.articles.tags.api.{TagsEndpoints, TagsListResponse}
import com.ttu.pay.common.ErrorMapper.defaultErrorsMappings
import sttp.tapir.ztapir.*
import zio.ZLayer

import scala.util.chaining.*

class TagsServerEndpoints(tagsEndpoints: TagsEndpoints, tagsService: TagsService):

  val getTagsServerEndpoint: ZServerEndpoint[Any, Any] = tagsEndpoints.getTagsEndpoint
    .zServerLogic(_ =>
      tagsService.getAllTags
        .map(foundTags => TagsListResponse(tags = foundTags))
        .logError
        .pipe(defaultErrorsMappings)
    )

  val endpoints: List[ZServerEndpoint[Any, Any]] =
    List(getTagsServerEndpoint)

object TagsServerEndpoints:
  val live: ZLayer[TagsEndpoints & TagsService, Nothing, TagsServerEndpoints] = ZLayer.fromFunction(new TagsServerEndpoints(_, _))
