package edu.uoc.pac4.data.user

import edu.uoc.pac4.data.network.OAuthException
import edu.uoc.pac4.data.user.model.User

/**
 * Created by alex on 11/21/20.
 */
interface UserRepository {

    @Throws(OAuthException::class)
    suspend fun getUser(): User?

    @Throws(OAuthException::class)
    suspend fun updateUser(description: String): User?

}