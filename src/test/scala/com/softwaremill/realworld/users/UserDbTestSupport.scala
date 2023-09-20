package com.ttu.pay.users

import com.ttu.pay.utils.DbData.*
import com.ttu.pay.utils.TestUtils.findUserIdByEmail
import zio.ZIO
object UserDbTestSupport:
  def prepareOneUser =
    ZIO.service[UsersRepository].flatMap(_.add(exampleUser1))

  def prepareTwoUsers =
    for {
      userRepo <- ZIO.service[UsersRepository]
      _ <- userRepo.add(exampleUser1)
      _ <- userRepo.add(exampleUser2)
    } yield ()

  def prepareTwoUsersWithFollowing =
    for {
      userRepo <- ZIO.service[UsersRepository]
      _ <- userRepo.add(exampleUser1)
      _ <- userRepo.add(exampleUser2)
      userId1 <- findUserIdByEmail(userRepo, exampleUser1.email)
      userId2 <- findUserIdByEmail(userRepo, exampleUser2.email)
      _ <- userRepo.follow(userId1, userId2)
    } yield ()
