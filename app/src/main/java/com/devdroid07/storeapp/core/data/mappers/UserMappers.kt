package com.devdroid07.storeapp.core.data.mappers

import com.devdroid07.storeapp.core.data.repository.UserSerializable
import com.devdroid07.storeapp.core.domain.User
import com.devdroid07.storeapp.core.domain.UserDto

fun User.toUserSerializable(): UserSerializable{
    return UserSerializable(
        id = id,
        image = image,
        email = email,
        token = token
    )
}

fun UserSerializable.toUser(): User{
    return User(
        id = id,
        image = image,
        email = email,
        token = token
    )
}

fun UserDto.toUser(): User{
    return User(
        id = id,
        image = image?:"",
        email = email,
        token = token
    )
}