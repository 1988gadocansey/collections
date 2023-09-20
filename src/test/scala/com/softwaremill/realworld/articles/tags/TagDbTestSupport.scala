package com.ttu.pay.articles.tags

import com.ttu.pay.articles.core.ArticlesRepository
import com.ttu.pay.users.UsersRepository
import com.ttu.pay.utils.DbData.*
import com.ttu.pay.utils.TestUtils.findUserIdByEmail
import zio.ZIO

object TagDbTestSupport:

  def prepareBasicTagsData =
    for {
      articleRepo <- ZIO.service[ArticlesRepository]
      userRepo <- ZIO.service[UsersRepository]
      _ <- userRepo.add(exampleUser1)
      _ <- userRepo.add(exampleUser2)
      userId1 <- findUserIdByEmail(userRepo, exampleUser1.email)
      userId2 <- findUserIdByEmail(userRepo, exampleUser2.email)
      _ <- articleRepo.addArticle(exampleArticle1, userId1)
      _ <- articleRepo.addArticle(exampleArticle2, userId2)
    } yield ()
