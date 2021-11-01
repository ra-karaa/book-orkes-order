package ra.enterwind.testcoude.activity.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ra.enterwind.testcoude.MainActivity
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.utils.Endpoint


class RegisterActivity : AppCompatActivity()
{
    lateinit var back : ImageView
    lateinit var daftar : Button
    lateinit var nama : EditText
    lateinit var alamat : EditText
    lateinit var nomor : EditText
    lateinit var username : EditText
    lateinit var password : EditText
    lateinit var progress : ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_register)

        back = findViewById(R.id.imgBack)
        daftar = findViewById(R.id.btnRegister)
        nama = findViewById(R.id.et_nama)
        alamat = findViewById(R.id.et_alamat)
        nomor = findViewById(R.id.et_nomor_hp)
        username = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)

        progress = ProgressDialog(this);
        progress.setTitle("Please Wait")
        progress.setMessage("Loading")

        back.setOnClickListener{
            finish()
        }

        daftar.setOnClickListener{
            cek()
        }
    }

    private fun cek() {
        val namae = nama.text.toString().trim()
        val alamate = alamat.text.toString().trim()
        val nomore = nomor.text.toString().trim()
        val usernamae = username.text.toString().trim()
        val passworde = password.text.toString().trim()

        if(!namae.isEmpty() && !alamate.isEmpty() && !nomore.isEmpty() && !usernamae.isEmpty() && !passworde.isEmpty()){
            kirim_data(namae, alamate, nomore, usernamae, passworde)
        } else {
            Toast.makeText(this, "Mohon Lengkapi Semua Inputan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun kirim_data(
        namae: String,
        alamate: String,
        nomore: String,
        usernamae: String,
        passworde: String
    ) {
        progress.show()
        val url = Endpoint().url_register
        val stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
                response ->
                Log.d("Peler", response)
                if(response.equals("sukses")){
                    progress.dismiss()
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    progress.dismiss()
                    Toast.makeText(this, "Gagal Daftar", Toast.LENGTH_SHORT).show()
                }
        },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    progress.dismiss()
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
        }){
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("nama", namae)
                params.put("alamat", alamate)
                params.put("nomor_hp", nomore)
                params.put("email", usernamae)
                params.put("password", passworde)
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }
}