package ra.enterwind.testcoude.activity.pesanan

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.activity.adapter.DaerahAdapter
import ra.enterwind.testcoude.models.Tempat
import ra.enterwind.testcoude.utils.Endpoint
import java.util.ArrayList

class DaerahActivity : AppCompatActivity(){

    lateinit var recyclerView: RecyclerView
    lateinit var daerahList: ArrayList<Tempat>
    lateinit var adapter: DaerahAdapter
    lateinit var swipeRefreshLayout : SwipeRefreshLayout

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_daerah)

        swipeRefreshLayout = findViewById(R.id.swipe)
        recyclerView = findViewById(R.id.resikelDaerah)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        daerahList = arrayListOf<Tempat>()
        adapter = DaerahAdapter(daerahList, this)

        jalan()
    }

    private fun jalan() {
        val progressDialog:ProgressDialog = ProgressDialog.show(this, null, "Loading")
        val jsonArrayRequest = JsonArrayRequest(Endpoint().url_daerah, Response.Listener {
                response ->
            if(response.length() == 0){
                progressDialog.dismiss()
                Toast.makeText(this, "Maaf Belum Ada Data", Toast.LENGTH_SHORT).duration
            } else {
                progressDialog.dismiss()
                daerahList.clear()
                for(i in 0 until response.length()){
                    val obj: JSONObject = response.getJSONObject(i)
                    val daerah = Tempat(
                        obj.getString("id"),
                        obj.getString("daerah"),
                        obj.getString("biaya"),
                        obj.getString("waktu")
                    )
                    daerahList.add(daerah)
                }
                recyclerView.adapter = adapter
            }
        }, Response.ErrorListener {
            progressDialog.dismiss()
            Toast.makeText(this, "Maad Terjadi Galat !", Toast.LENGTH_SHORT).show()

        })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonArrayRequest)
    }
}