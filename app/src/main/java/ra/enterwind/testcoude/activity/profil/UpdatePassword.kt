package ra.enterwind.testcoude.activity.profil

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.session.Session
import ra.enterwind.testcoude.utils.Endpoint

class UpdatePassword : AppCompatActivity(){

    lateinit var img_back : ImageView
    lateinit var et_pass : TextView
    lateinit var et_kon : TextView
    lateinit var btn_simpan : Button
    lateinit var shared : Session
    lateinit var email : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_password)

        shared = Session(this)
        img_back = findViewById(R.id.ivBack)
        et_pass = findViewById(R.id.et_password)
        et_kon = findViewById(R.id.et_konfirmasi)
        btn_simpan = findViewById(R.id.btnSave)

        val detail = shared.getUserDetail()
        email = detail.get(shared.KEY_NAME)!!

        img_back.setOnClickListener {
            finish()
        }

        btn_simpan.setOnClickListener{

            to_simpan()
        }
    }

    private fun to_simpan() {
        val password = et_pass.text.toString().trim();
        val konfirmasi = et_kon.text.toString().trim()
        if(password != konfirmasi){
            Toast.makeText(this, "Password dan Konfirmasi Tidak Sama", Toast.LENGTH_SHORT).show()
        } else {
            simpan_json(password)
        }
    }

    private fun simpan_json(password: String) {
        val stringRequest = object : StringRequest(Request.Method.POST, Endpoint().url_password, Response.Listener {
            response ->
            if(response.equals("sukses")){
                startActivity(Intent(this, IndexProfil::class.java))
                finish()
            } else {
                Toast.makeText(this, "Terjadi Kesalahan Data", Toast.LENGTH_SHORT).show()
            }
        },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.d("Password Update", error!!.message);
                }
            }){

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("email", email)
                params.put("password", password)
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}