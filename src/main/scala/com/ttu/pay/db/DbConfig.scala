package com.ttu.pay.db

import com.ttu.pay.common.AppConfig
import zio.{ZIO, ZLayer}

case class DbConfig(jdbcUrl: String):
  val connectionInitSql = ""

object DbConfig:

  val live: ZLayer[AppConfig, Nothing, DbConfig] =
    ZLayer.fromZIO {
      for {
        appConfig <- ZIO.service[AppConfig]
      } yield DbConfig(appConfig.db.url)
    }
