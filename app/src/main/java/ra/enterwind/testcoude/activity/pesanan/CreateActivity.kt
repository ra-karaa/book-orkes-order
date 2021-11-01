package ra.enterwind.testcoude.activity.pesanan

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_auth_register.*
import ra.enterwind.testcoude.R
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_dashboard.*
import ra.enterwind.testcoude.session.Session
import ra.enterwind.testcoude.utils.Endpoint
import kotlin.math.tan


class CreateActivity : AppCompatActivity()
{
    lateinit var back : ImageView
    lateinit var acara : EditText
    lateinit var tanggal : TextView
    lateinit var daerah : TextView
    lateinit var id_daerah : String
    lateinit var btnPesan : Button
    lateinit var email : String


    var cal = Calendar.getInstance()
    val SECOND_ACTIVITY_REQUEST_CODE = 0
    lateinit var sharedPreference : Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_create)

        sharedPreference = Session(this)
        val detail = sharedPreference.getUserDetail()
        email = detail.get(sharedPreference.KEY_NAME)!!

        acara = findViewById(R.id.et_acara)
        tanggal = findViewById(R.id.et_tanggal)
        daerah = findViewById(R.id.txt_daerah)
        back = findViewById(R.id.imgBack)
        btnPesan = findViewById(R.id.btnPesan)
        back.setOnClickListener{
            finish()
        }

        tanggal.setOnClickListener{
            DatePickerDialog(this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        daerah.setOnClickListener{
            val intent = Intent(this, DaerahActivity::class.java)
            startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
        }

        btnPesan.setOnClickListener{
            buat_pesanan()
        }


    }

    private fun buat_pesanan() {
        val progressDialog:ProgressDialog = ProgressDialog.show(this, null, "Loading")
        progressDialog.show()
        val stringRequest = object  : StringRequest(Request.Method.POST, Endpoint().url_buat + email, Response.Listener {
            response ->
            Log.d("Buat", response)
            if(response.equals("sukses")){
                progressDialog.dismiss()
                startActivity(Intent(this, IndexActivity::class.java))
                finish()
            } else {
                progressDialog.dismiss()
                Toast.makeText(this, "Maaf Kesalahan Back End", Toast.LENGTH_SHORT).show()
            }

        }, Response.ErrorListener {
                VolleyError->
                Toast.makeText(this, VolleyError.message, Toast.LENGTH_SHORT).show()
        }){
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("acara", acara.text.toString().trim())
                params.put("tgl_acara", tanggal.text.toString().trim())
                params.put("alamat", id_daerah)
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                // Set text view with string
                id_daerah = data!!.getStringExtra("id")
                daerah.text = data!!.getStringExtra("daerah")
            }
        }
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat)
        tanggal.text = sdf.format(cal.time)
    }

}


