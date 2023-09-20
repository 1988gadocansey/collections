package com.ttu.pay.users

case class UserWithPassword(
    user: User,
    hashedPassword: String
)
