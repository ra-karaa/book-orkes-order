package ra.enterwind.testcoude.activity.adapter

/*
    * author <Fieta Prabaskara>
    * fprabaskara@gmail.com
*/

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.activity.pesanan.DetailActivity
import ra.enterwind.testcoude.activity.pesanan.FotoActivity
import ra.enterwind.testcoude.models.Jadwal

class PesananAdapter (val jadwal: List<Jadwal>, var mContext: Activity) : RecyclerView.Adapter<PesananAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_jadwalku, parent, false);

        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return jadwal.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pesananku:Jadwal = jadwal.get(position)
        holder.acaraku.text = pesananku.acara
        holder.tanggal.text = pesananku.tgl_acara
        holder.bulan.text = pesananku.bulan
        holder.tahun.text = pesananku.tahun
        holder.tempat.text = pesananku.alamat
        when(pesananku.status){
            "0" -> {
                holder.status.text = "Requested"
                holder.card.setOnClickListener{
                    val tent = Intent(mContext, DetailActivity::class.java)
                    tent.putExtra("id", pesananku.id)
                    tent.putExtra("status", pesananku.status)
                    mContext.startActivity(tent)
                }
            }
            "1" -> {
                holder.status.text = "Lakukan Pembayaran"
                holder.card.setOnClickListener{
                val tent = Intent(mContext, DetailActivity::class.java)
                    tent.putExtra("id", pesananku.id)
                    tent.putExtra("status", pesananku.status)
                    mContext.startActivity(tent)
//                    Toast.makeText(mContext, "Akan Upload ", Toast.LENGTH_SHORT).show()
//                    val tenten = Intent(mContext, FotoActivity::class.java)
//                    tenten.putExtra("id", pesananku.id)
//                    mContext.startActivity(tenten)
                }
            }
            "2" -> {
                holder.status.text = "Sedang di Verifikasi"
                holder.card.setOnClickListener{
                    val tent = Intent(mContext, DetailActivity::class.java)
                    tent.putExtra("id", pesananku.id)
                    tent.putExtra("status", pesananku.status)
                    mContext.startActivity(tent)
                }
            }

            "3" -> {
                holder.status.text = "Berhasil"
                holder.card.setOnClickListener{
                    val tent = Intent(mContext, DetailActivity::class.java)
                    tent.putExtra("id", pesananku.id)
                    tent.putExtra("status", pesananku.status)
                    mContext.startActivity(tent)
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val acaraku = itemView.findViewById(R.id.getAcara) as TextView
        val tanggal = itemView.findViewById(R.id.getTanggal) as TextView
        val bulan = itemView.findViewById(R.id.getBulan) as TextView
        val tahun = itemView.findViewById(R.id.getTahun) as TextView
        val tempat = itemView.findViewById(R.id.getTempat) as TextView
        val card = itemView.findViewById(R.id.cardJadwal) as CardView
        val status = itemView.findViewById(R.id.getStatusku) as TextView

    }
}