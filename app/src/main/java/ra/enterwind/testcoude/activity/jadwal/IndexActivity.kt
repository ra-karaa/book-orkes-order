package ra.enterwind.testcoude.activity.jadwal

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.activity.adapter.JadwalAdapter
import ra.enterwind.testcoude.models.Jadwal
import ra.enterwind.testcoude.utils.Endpoint
import android.os.Handler
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.util.ArrayList


class IndexActivity : AppCompatActivity(){

    lateinit var recyclerView: RecyclerView
    lateinit var jadwallist: ArrayList<Jadwal>
    lateinit var adapter: JadwalAdapter
    lateinit var progress : ProgressDialog
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var imgBack : ImageView

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal_index)

        progress = ProgressDialog(this);
        progress.setTitle("Please Wait")
        progress.setMessage("Loading")

        swipeRefreshLayout = findViewById(R.id.swipe)
        recyclerView = findViewById(R.id.resikelAgenda)
        imgBack = findViewById(R.id.imgBack)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        jadwallist = arrayListOf<Jadwal>()
        adapter = JadwalAdapter(jadwallist)

        swipeRefreshLayout.setOnRefreshListener {
            jalan()
            Handler().postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
            }, 4000)
        }

        imgBack.setOnClickListener{
            finish()
        }

        jalan()
    }

    private fun jalan() {
        progress.show()
        val jsonArrayRequest = JsonArrayRequest(Endpoint().url_jadwal, Response.Listener {
            response ->
            if(response.length() == 0){
                progress.dismiss()
                Toast.makeText(this, "Maaf Belum Ada Data", Toast.LENGTH_SHORT).duration
            } else {
                progress.dismiss()
                jadwallist.clear()
                for(i in 0 until response.length()){
                    val obj:JSONObject = response.getJSONObject(i)
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
            jalan()
            Toast.makeText(this, "Maad Terjadi Gala !", Toast.LENGTH_SHORT).show()

        })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonArrayRequest)
    }
}