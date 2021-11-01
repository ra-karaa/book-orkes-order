package ra.enterwind.testcoude.activity.profil

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_profil_update.*
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.activity.DashboardActivity
import ra.enterwind.testcoude.session.Session
import ra.enterwind.testcoude.utils.Endpoint

class UpdateProfil : AppCompatActivity(){

    lateinit var img_back : ImageView
    lateinit var et_nama : EditText
    lateinit var etNomorHp : EditText
    lateinit var etAlamat : EditText
    lateinit var button : Button

    lateinit var session: Session
    lateinit var email : String
    lateinit var progress : ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_update)

        session = Session(this)
        val detail = session.getUserDetail();
        email = detail.get(session.KEY_NAME)!!

        img_back = findViewById(R.id.ivBack)
        et_nama = findViewById(R.id.etNama)
        etNomorHp = findViewById(R.id.etNomorHp)
        etAlamat = findViewById(R.id.etAlamat)
        button = findViewById(R.id.btnSave)

        etAlamat.setText(detail.get(session.KEY_ALAMAT))
        et_nama.setText(detail.get(session.KEY_EMAIL))
        etNomorHp.setText(detail.get(session.KEY_PASSWORD))

        progress = ProgressDialog(this)
        progress.setMessage("Loading")


        button.setOnClickListener {
            progress.show()
            if(etAlamat.text.toString().isEmpty() && etNama.text.toString().isEmpty() && etNomorHp.text.toString().isEmpty()){
                progress.dismiss()
                Toast.makeText(this, "Mohon Lengkapi Inputan", Toast.LENGTH_SHORT).show()
            } else {
                session.updateProfil(et_nama.text.toString().trim(), etAlamat.text.toString().trim(), etNomorHp.text.toString().trim())
                post_update(et_nama.text.toString().trim(), etAlamat.text.toString().trim(), etNomorHp.text.toString().trim())
            }
        }

        img_back.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

    }

    private fun post_update(nama: String, alamat: String, nohp: String) {
        val string = object : StringRequest(Request.Method.POST, Endpoint().url_profil, Response.Listener {
            response ->
            Log.d("Profil", "post_update: " + response)
            if(response.equals("sukses")){
                progress.dismiss()
                startActivity(Intent(this, IndexProfil::class.java))
                finish()
            } else {
                progress.dismiss()
                Toast.makeText(this, "Maaf Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {
                Log.d("Profil", error.toString())
            }
        }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("email", email.trim())
                params.put("name", nama)
                params.put("alamat", alamat)
                params.put("nomor_hp",nohp)
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(string)
    }

}