package ra.enterwind.testcoude.activity.adapter

/*
    * author <Fieta Prabaskara>
    * fprabaskara@gmail.com
*/

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.models.Tempat



class DaerahAdapter (val tempat : List<Tempat>, var mContext:Activity) : RecyclerView.Adapter<DaerahAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_tempat, parent, false);

        return MyViewHolder(v);
    }

    override fun getItemCount(): Int {
        return tempat.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tempatku:Tempat = tempat.get(position)
        holder.daerah.text = tempatku.daerah
        holder.biaya.text = tempatku.harga
        holder.waktu.text = tempatku.waktu
        holder.card.setOnClickListener{
            val tenten = Intent()
            tenten.putExtra("id", tempatku.id)
            tenten.putExtra("daerah", tempatku.daerah)
            mContext.setResult(Activity.RESULT_OK, tenten)
            mContext.finish()
        }
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val daerah = itemView.findViewById(R.id.getDaerah) as TextView
        val card = itemView.findViewById(R.id.cardDaerah) as CardView
        val biaya = itemView.findViewById(R.id.getBiaya) as TextView
        val waktu = itemView.findViewById(R.id.getWaktu) as TextView
    }

}
