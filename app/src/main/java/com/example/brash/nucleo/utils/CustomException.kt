package com.example.brash.nucleo.utils

import com.google.firebase.auth.FirebaseAuthException

class FirebaseAuthVerifyException(
    errorCode: String,
    message: String
) : FirebaseAuthException(errorCode, message)