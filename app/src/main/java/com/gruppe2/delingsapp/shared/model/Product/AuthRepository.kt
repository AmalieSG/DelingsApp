package com.gruppe2.delingsapp.shared.model.Product

interface AuthRepository {
    abstract fun isUserSignedIn(): Boolean
    abstract fun getCurrentUserId(): String

}
