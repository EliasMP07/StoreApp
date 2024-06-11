package com.devdroid07.storeapp.auth.data.repository

import com.devdroid07.storeapp.auth.data.remote.api.AuthApi
import com.devdroid07.storeapp.auth.data.remote.api.LoginRequest
import com.devdroid07.storeapp.auth.data.remote.response.LoginResponse
import com.devdroid07.storeapp.auth.domain.repository.AuthRepository
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    val api: AuthApi
): AuthRepository {
    override suspend fun login(userName: String, password: String): Result<Unit> {
        return try {

            Result.success(Unit)
        }catch (e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, userName: String, password: String) {
        TODO("Not yet implemented")
    }
}
/*

interface FakeStoreApi {
    @POST("graphql")
    suspend fun login(@Body loginRequest: GraphqlRequest): GraphqlResponse<LoginResponse>

    @POST("graphql")
    suspend fun getUserProfile(@Header("Authorization") token: String, @Body profileRequest: GraphqlRequest): GraphqlResponse<UserProfile>
}

data class GraphqlRequest(val query: String, val variables: Map<String, Any>? = null)
data class GraphqlResponse<T>(val data: T)

data class LoginResponse(val login: TokenResponse)
data class TokenResponse(val access_token: String, val refresh_token: String)

data class UserProfile(val myProfile: User)
data class User(val id: String, val name: String, val avatar: String)
*/
