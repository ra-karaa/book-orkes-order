package ra.enterwind.testcoude.activity.pesanan

/*
    * author <Fieta Prabaskara>
    * fprabaskara@gmail.com
*/

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.ortiz.touchview.TouchImageView
import kotlinx.android.synthetic.main.activity_pesanan_detail.*
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.utils.Endpoint

class DetailActivity : AppCompatActivity(){

    lateinit var id_pesanan : String
    lateinit var status_pesanan : String
    lateinit var img_back : ImageView
    lateinit var txt_biaya : TextView
    lateinit var txt_daerah : TextView
    lateinit var txt_waktu : TextView
    lateinit var txt_acara : TextView
    lateinit var txt_tanggal : TextView
    lateinit var btn_upload : Button
    lateinit var liner : LinearLayout
    lateinit var image : TouchImageView
    lateinit var circularProgressDrawable : CircularProgressDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_detail)

        id_pesanan = intent.getStringExtra("id")
        status_pesanan = intent.getStringExtra("status")
        img_back = findViewById(R.id.imageView2)
        txt_biaya = findViewById(R.id.getBiaya)
        txt_daerah = findViewById(R.id.getDaerah)
        txt_waktu = findViewById(R.id.getWaktu)
        txt_tanggal = findViewById(R.id.getTanggal)
        txt_acara = findViewById(R.id.getAcara)
        btn_upload = findViewById(R.id.uploadStruk)
        liner = findViewById(R.id.linearLayout)
        image = findViewById(R.id.getImage)

        circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        img_back.setOnClickListener {
            finish()
        }

        btn_upload.setOnClickListener {

            val tent = Intent(this, FotoActivity::class.java)
            tent.putExtra("id", id_pesanan)
            startActivity(tent)
        }

        if (status_pesanan.equals("1")){
            uploadStruk.visibility = View.VISIBLE

        } else {
            uploadStruk.visibility = View.GONE
        }



        ambil_object()
    }


    private fun ambil_object() {
        val progress = ProgressDialog.show(this, null, "Loading")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, Endpoint().url_jadwal_detail + id_pesanan, null, Response.Listener {
            response ->
            progress.dismiss()
            Log.d("detail", response.toString())
            txt_biaya.text = response.getString("biaya")
            txt_daerah.text = response.getString("daerah")
            txt_waktu.text = response.getString("waktu")
            txt_tanggal.text = response.getString("tgl_acara")
            txt_acara.text = response.getString("acara")
            if(status_pesanan.equals("0") || status_pesanan.equals("1")){
                liner.visibility = View.GONE
            } else {
                if(response.getString("struk").isEmpty()){
                    liner.visibility = View.GONE
                } else {
                    liner.visibility = View.VISIBLE
                    Glide.with(this).load(response.getString("struk")).placeholder(circularProgressDrawable).fitCenter().into(image)
                }
            }
        }, Response.ErrorListener {
            progress.dismiss()
            Toast.makeText(this, "Maaf Ada Kesalahan Pada Server !!", Toast.LENGTH_SHORT).show()
        })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }
}