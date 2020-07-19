package com.teguh.bfd

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.teguh.bfd.Utils.Commons
import com.teguh.bfd.models.Users
import dmax.dialog.SpotsDialog
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
    private lateinit var dialog: AlertDialog
    private lateinit var userRef: DatabaseReference

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

        dialog = SpotsDialog.Builder()
            .setMessage(R.string.silahkan_tunggu)
            .setContext(this)
            .setCancelable(false)
            .build()

        userRef = FirebaseDatabase.getInstance().getReference(Commons.USER_REFERENCES)

        // ini value properti providers
        providers = Arrays.asList(
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

        firebaseAuth = FirebaseAuth.getInstance()

        listener = FirebaseAuth.AuthStateListener { myFirebaseAuth ->
            val user = myFirebaseAuth.currentUser
            if (user != null){
                //Toast.makeText(this, "Selamat datang ${user.uid}", Toast.LENGTH_SHORT).show()
                checkUserFromDatabase(user.uid)
            } else {
                showLogin()
            }
        }
    }

    private fun checkUserFromDatabase(uid: String) {
        dialog.show()
        userRef.child(uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@SplashActivity, "Error: "+p0.message, Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists()){
                        val user = snapshot.getValue(Users::class.java)
                        gotoMainActiviy(user)
                   } else {
                        showRegisterLayout(uid)
                   }
                        dialog.dismiss()
                }

            })
    }

    private fun showRegisterLayout(uid: String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("BIODATA")
        builder.setMessage("Lengkapi data terlebih dahulu sebelum memulai")

        val itemView = LayoutInflater.from(this@SplashActivity)
            .inflate(R.layout.layout_register, null)

        val edtName = itemView.findViewById<EditText>(R.id.edt_name)
        val edtAddress = itemView.findViewById<EditText>(R.id.edt_address)
        val edtPhone = itemView.findViewById<EditText>(R.id.edt_phone)

        builder.setView(itemView)
        builder.setCancelable(false)
        builder.setNegativeButton("BATAL", {dialogInterface, i -> dialogInterface.dismiss()  })
        builder.setPositiveButton("REGISTER") { dialogInterface, i ->
            val user = Users()
            user.uid = uid
            user.name = edtName.text.toString()
            user.address = edtAddress.text.toString()
            user.phone = edtPhone.text.toString()

            userRef.child(uid)
                .setValue(user)
                .addOnCompleteListener{ task ->  
                    if (task.isSuccessful){
                        dialogInterface.dismiss()
                        Toast.makeText(this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()
                        gotoMainActiviy(user)
                    }
                }
        }

        builder.create().show()


    }

    private fun gotoMainActiviy(user: Users?) {
        Commons.currentUser = user
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
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
