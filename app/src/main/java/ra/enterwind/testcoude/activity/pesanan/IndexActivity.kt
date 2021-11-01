package ra.enterwind.testcoude.activity.pesanan

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.activity.DashboardActivity
import ra.enterwind.testcoude.activity.adapter.PesananAdapter
import ra.enterwind.testcoude.models.Jadwal
import ra.enterwind.testcoude.utils.Endpoint
import java.util.ArrayList

class IndexActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var jadwallist: ArrayList<Jadwal>
    lateinit var adapter: PesananAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var imgBack : ImageView
    lateinit var email : String
    lateinit var tambah : FloatingActionButton
    lateinit var linear : LinearLayout


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_index)

        val shared = ra.enterwind.testcoude.session.Session(this)

        swipeRefreshLayout = findViewById(R.id.swipe)
        recyclerView = findViewById(R.id.resikelAgenda)
        imgBack = findViewById(R.id.imgBack)
        tambah = findViewById(R.id.floa_tambah)
        linear = findViewById(R.id.kosong)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        jadwallist = arrayListOf<Jadwal>()
        adapter = PesananAdapter(jadwallist, this)

        val detail = shared.getUserDetail()
        email = detail.get(shared.KEY_NAME).toString()

        swipeRefreshLayout.setOnRefreshListener {
            jalan(email)
            Handler().postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
            }, 4000)
        }

        imgBack.setOnClickListener{
            finish()
        }

        tambah.setOnClickListener{
            startActivity(Intent(this, CreateActivity::class.java))
        }

        jalan(email)
    }

    private fun jalan(email : String) {
        val progress =  ProgressDialog.show(this, null , "Loading")
        val jsonArrayRequest = JsonArrayRequest(Endpoint().url_jadwaku + email, Response.Listener {
                response ->
            Log.d("kontol", response.toString())
            if(response.length() == 0){
                progress.dismiss()
                linear.visibility = View.VISIBLE
            } else {
                progress.dismiss()
                jadwallist.clear()
                for(i in 0 until response.length()){
                    val obj: JSONObject = response.getJSONObject(i)
                    val jadwalku = Jadwal(
                        obj.getString("id"),
                        obj.getString("acara"),
                        obj.getString("tanggal"),
                        obj.getString("bulan"),
                        obj.getString("tahun"),
                        obj.getString("member"),
                        obj.getString("alamat"),
                        obj.getString("status")
                    )
                    jadwallist.add(jadwalku)
                }
                recyclerView.adapter = adapter
            }
        }, Response.ErrorListener {
            progress.dismiss()
            Toast.makeText(this, "Maad Terjadi Galat !", Toast.LENGTH_SHORT).show()

        })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonArrayRequest)
    }
}