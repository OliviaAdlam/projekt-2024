package com.example.projectaplikacja.Models
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthInterface {
    fun signUpWithEmailPassword(email: String, password: String): Task<AuthResult>
    fun signInWithEmailPassword(email: String, password: String): Task<AuthResult>
    fun signInWithGoogle(account: GoogleSignInAccount)
    fun sendPasswordResetEmail(email: String): Task<Void>
    fun signOut()
}