package ra.enterwind.testcoude.activity.auth

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.utils.Endpoint
import com.android.volley.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ra.enterwind.testcoude.activity.DashboardActivity
import ra.enterwind.testcoude.session.Session


class LoginActivity : AppCompatActivity()
{
    lateinit var txtPeler : TextView
    lateinit var etUsername : EditText
    lateinit var etPassword : EditText
    lateinit var btnLogin : Button

    lateinit var sharedPreference : Session
    lateinit var progress : ProgressDialog

     var fcm_toke : String ? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)

        etUsername = findViewById(R.id.txtUsername)
        etPassword = findViewById(R.id.txtPassword)
        txtPeler = findViewById(R.id.register)
        btnLogin = findViewById(R.id.btnLogin)

        progress = ProgressDialog(this);
        progress.setTitle("Please Wait")
        progress.setMessage("Loading")

        sharedPreference=Session(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("Das", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            fcm_toke = task.result

            // Log and toast
            Log.d("Das", fcm_toke)
        })

        txtPeler.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener{

            var username = etUsername.text.toString().trim()
            var password = etPassword.text.toString().trim()

            if(username.isEmpty() && password.isEmpty()){
                Toast.makeText(this, "Harap Lengkapi Seluruh Inputan", Toast.LENGTH_SHORT).show()
            }  else {
                jalan(username, password)
            }
        }
    }

    private fun jalan(username: String, password: String) {
        progress.show()
        val url = Endpoint().url_login;
        val stringRequest = StringRequest(Request.Method.GET, url + username + "/" + password + "/" + fcm_toke, Response.Listener {
                response ->
                if (response.equals("gagal")){
                    progress.dismiss()
                    Toast.makeText(this, "Username dan Password Beda", Toast.LENGTH_SHORT).show()
                } else {
                    progress.dismiss()
                    val list = response.split(",")
                    sharedPreference.createLogin(list[0], list[1], list[2], list[3])
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
        }, Response.ErrorListener {
            progress.dismiss()
            Toast.makeText(this, "Terjadi Galat", Toast.LENGTH_SHORT).show()
        })
        stringRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}