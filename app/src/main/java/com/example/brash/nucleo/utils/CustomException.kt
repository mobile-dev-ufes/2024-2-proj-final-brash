package com.example.brash.nucleo.utils

import com.google.firebase.auth.FirebaseAuthException

/**
 * Custom exception class for handling Firebase authentication verification errors.
 *
 * @param errorCode The error code associated with the Firebase authentication exception.
 * @param message The message describing the exception.
 */
class FirebaseAuthVerifyException(
    errorCode: String,
    message: String
) : FirebaseAuthException(errorCode, message)