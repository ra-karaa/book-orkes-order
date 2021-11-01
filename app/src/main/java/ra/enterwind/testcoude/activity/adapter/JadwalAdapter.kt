package ra.enterwind.testcoude.activity.adapter

/*
    * author <Fieta Prabaskara>
    * fprabaskara@gmail.com
*/

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.models.Jadwal

class JadwalAdapter (val jadwal: List<Jadwal>) : RecyclerView.Adapter<JadwalAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_jadwal, parent, false))
    }

    override fun getItemCount(): Int {
        return jadwal.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(jadwal[position])
    }

    class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {

        fun bindItems(jadwal: Jadwal) {
            val acaraku = itemView.findViewById(R.id.getAcara) as TextView
            val tanggal = itemView.findViewById(R.id.getTanggal) as TextView
            val bulan = itemView.findViewById(R.id.getBulan) as TextView
            val tahun = itemView.findViewById(R.id.getTahun) as TextView
            val tempat = itemView.findViewById(R.id.getTempat) as TextView
            val card = itemView.findViewById(R.id.cardJadwal) as CardView
            acaraku.text = jadwal.acara
            tanggal.text = jadwal.tgl_acara
            bulan.text = jadwal.bulan
            tahun.text = jadwal.tahun
            tempat.text = jadwal.alamat
        }
    }


}
