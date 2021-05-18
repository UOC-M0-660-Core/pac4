package edu.uoc.pac4.data.authentication.repository

/**
 * Created by alex on 12/09/2020.
 */

interface AuthenticationRepository {

    /// Returns true if a user exists. False otherwise
    suspend fun isUserAvailable(): Boolean

    /// Perform login with the given authorizationCode
    suspend fun login(authorizationCode: String)

    /// Log out the current user
    suspend fun logout()

}