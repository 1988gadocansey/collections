package com.ttu.pay

import com.ttu.pay.articles.comments.api.CommentsEndpoints
import com.ttu.pay.articles.comments.{CommentsRepository, CommentsServerEndpoints, CommentsService}
import com.ttu.pay.articles.core.api.ArticlesEndpoints
import com.ttu.pay.articles.core.{ArticlesRepository, ArticlesServerEndpoints, ArticlesService}
import com.ttu.pay.articles.tags.api.TagsEndpoints
import com.ttu.pay.articles.tags.{TagsRepository, TagsServerEndpoints, TagsService}
import com.ttu.pay.auth.AuthService
import com.ttu.pay.common.*
import com.ttu.pay.db.{Db, DbConfig, DbMigrator}
import com.ttu.pay.users.api.UsersEndpoints
import com.ttu.pay.users.{UsersRepository, UsersServerEndpoints, UsersService}
import sttp.tapir.server.interceptor.cors.CORSConfig.AllowedOrigin
import sttp.tapir.server.interceptor.cors.{CORSConfig, CORSInterceptor}
import sttp.tapir.server.ziohttp
import sttp.tapir.server.ziohttp.{ZioHttpInterpreter, ZioHttpServerOptions}
import zio.*
import zio.http.*
import zio.logging.LogFormat
import zio.logging.backend.SLF4J

object Main extends ZIOAppDefault:

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = SLF4J.slf4j(LogFormat.colored)

  override def run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] =

    val port = sys.env.get("HTTP_PORT").flatMap(_.toIntOption).getOrElse(9090)
    val options: ZioHttpServerOptions[Any] = ZioHttpServerOptions.customiseInterceptors
      .exceptionHandler(new DefectHandler())
      .corsInterceptor(
        CORSInterceptor.customOrThrow(
          CORSConfig.default.copy(
            allowedOrigin = AllowedOrigin.All
          )
        )
      )
      .decodeFailureHandler(CustomDecodeFailureHandler.create())
      .options

    (for
      migrator <- ZIO.service[DbMigrator]
      _ <- migrator.migrate()
      endpoints <- ZIO.service[Endpoints]
      httpApp = ZioHttpInterpreter(options).toHttp(endpoints.endpoints)
      actualPort <- Server.install(httpApp.withDefaultErrorResponse)
      _ <- Console.printLine(s"Application ttupay started")
      _ <- Console.printLine(s"Go to http://localhost:$actualPort/docs to open SwaggerUI")
      _ <- ZIO.never
    yield ())
      .provide(
        Configuration.live,
        DbConfig.live,
        Db.dataSourceLive,
        Db.quillLive,
        DbMigrator.live,
        Endpoints.live,
        AuthService.live,
        UsersEndpoints.live,
        UsersServerEndpoints.live,
        UsersService.live,
        UsersRepository.live,
        ArticlesEndpoints.live,
        ArticlesServerEndpoints.live,
        ArticlesService.live,
        ArticlesRepository.live,
        CommentsEndpoints.live,
        CommentsServerEndpoints.live,
        CommentsService.live,
        CommentsRepository.live,
        BaseEndpoints.live,
        TagsEndpoints.live,
        TagsServerEndpoints.live,
        TagsService.live,
        TagsRepository.live,
        Server.defaultWithPort(port)
      )
      .exitCode
