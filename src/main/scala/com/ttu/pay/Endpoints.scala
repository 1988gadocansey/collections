package com.ttu.pay

import com.ttu.pay.articles.comments.CommentsServerEndpoints
import com.ttu.pay.articles.core.ArticlesServerEndpoints
import com.ttu.pay.articles.tags.TagsServerEndpoints
import com.ttu.pay.users.UsersServerEndpoints
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir.ZServerEndpoint
import zio.{Task, ZLayer}

class Endpoints(
    articlesEndpoints: ArticlesServerEndpoints,
    commentsServerEndpoints: CommentsServerEndpoints,
    tagsEndpoints: TagsServerEndpoints,
    usersServerEndpoints: UsersServerEndpoints
):

  val endpoints: List[ZServerEndpoint[Any, Any]] = {
    val api = articlesEndpoints.endpoints ++ commentsServerEndpoints.endpoints ++ tagsEndpoints.endpoints ++ usersServerEndpoints.endpoints
    val docs = docsEndpoints(api)
    api ++ docs
  }

  private def docsEndpoints(apiEndpoints: List[ZServerEndpoint[Any, Any]]): List[ZServerEndpoint[Any, Any]] = SwaggerInterpreter()
    .fromServerEndpoints[Task](apiEndpoints, "realworld-tapir-zio", "0.1.0")

object Endpoints:
  val live: ZLayer[
    ArticlesServerEndpoints & CommentsServerEndpoints & TagsServerEndpoints & UsersServerEndpoints,
    Nothing,
    Endpoints
  ] =
    ZLayer.fromFunction(new Endpoints(_, _, _, _))
