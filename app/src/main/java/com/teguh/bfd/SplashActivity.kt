package com.teguh.bfd

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    // properti
    private lateinit var providers: List<AuthUI.IdpConfig>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var listener: FirebaseAuth.AuthStateListener

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun onStop() {
        if (firebaseAuth != null && listener != null){
            firebaseAuth.removeAuthStateListener(listener)
        }
        super.onStop()
    }

    private fun delaySplash() {
        Completable.timer(2,TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe({
                firebaseAuth.addAuthStateListener(listener)
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init() {
        // ini value properti providers
        providers = Arrays.asList(
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

        firebaseAuth = FirebaseAuth.getInstance()

        listener = FirebaseAuth.AuthStateListener { myFirebaseAuth ->
            val user = myFirebaseAuth.currentUser
            if (user != null){
                Toast.makeText(this, "Selamat datang ${user.uid}", Toast.LENGTH_SHORT).show()
            } else {
                showLogin()
            }
        }
    }

    private fun showLogin() {
        val authMethodPickerLayout = AuthMethodPickerLayout.Builder(R.layout.activity_splash)
            .setPhoneButtonId(R.id.button_phone_login)
            .build()

        button_phone_login.setOnClickListener{

            startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAuthMethodPickerLayout(authMethodPickerLayout)
                .setTheme(R.style.SplashTheme)
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build(), LOGIN_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val response = IdpResponse.fromResultIntent(data)

        if (requestCode == LOGIN_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
            } else {
                Toast.makeText(this, response!!.error!!.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object{
        private const val LOGIN_REQUEST_CODE = 7171
    }
}
