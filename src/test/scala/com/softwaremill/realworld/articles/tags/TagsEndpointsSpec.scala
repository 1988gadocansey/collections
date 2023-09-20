package com.ttu.pay.articles.tags

import com.ttu.pay.articles.core.ArticlesRepository
import com.ttu.pay.articles.tags.TagDbTestSupport.*
import com.ttu.pay.articles.tags.TagEndpointTestSupport.*
import com.ttu.pay.articles.tags.api.TagsEndpoints
import com.ttu.pay.auth.AuthService
import com.ttu.pay.common.{BaseEndpoints, Configuration}
import com.ttu.pay.users.UsersRepository
import com.ttu.pay.utils.TestUtils.*
import sttp.client3.UriContext
import zio.test.ZIOSpecDefault

object TagsEndpointsSpec extends ZIOSpecDefault:

  override def spec = suite("tag endpoints tests")(
    test("return empty list") {
      for {
        result <- checkIfTagsListIsEmpty(
          uri = uri"http://test.com/api/tags"
        )
      } yield result
    },
    test("return tags list") {
      for {
        _ <- prepareBasicTagsData
        result <- checkTags(
          uri = uri"http://test.com/api/tags"
        )
      } yield result
    }
  ).provide(
    Configuration.live,
    AuthService.live,
    BaseEndpoints.live,
    TagsRepository.live,
    TagsService.live,
    TagsEndpoints.live,
    TagsServerEndpoints.live,
    ArticlesRepository.live,
    UsersRepository.live,
    testDbLayerWithEmptyDb
  )
