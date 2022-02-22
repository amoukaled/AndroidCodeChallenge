package com.amoukaled.androidcodechallenge.utils

import at.favre.lib.crypto.bcrypt.BCrypt

/**
 * Utils for Bcrypt
 */
object BcryptUtils {

    // Bcrypt hashed with cost 12
    // private const val COST = 12

    /**
     * Validates the raw password to the hash.
     * @see [at.favre.lib.crypto.bcrypt.BCrypt.Verifyer.verify]
     */
    fun validate(hashedPassword: String, rawPassword: String): Boolean {
        return BCrypt.verifyer().verify(rawPassword.toCharArray(), hashedPassword)?.verified ?: false
    }

}