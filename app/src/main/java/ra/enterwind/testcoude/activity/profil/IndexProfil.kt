package ra.enterwind.testcoude.activity.profil

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.activity.DashboardActivity
import ra.enterwind.testcoude.activity.auth.LoginActivity
import ra.enterwind.testcoude.session.Session

class IndexProfil : AppCompatActivity(){

    lateinit var shared : Session
    lateinit var txt_nama : TextView
    lateinit var txt_nohp : TextView
    lateinit var txt_alamat : TextView
    lateinit var img_back : ImageView

    lateinit var list_password : LinearLayout
    lateinit var list_tentang : LinearLayout
    lateinit var listProfil : LinearLayout
    lateinit var listKeluar : LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_index)

        shared = Session(this)

        txt_nama = findViewById(R.id.namalengkap)
        txt_nohp = findViewById(R.id.nomor_hp)
        txt_alamat = findViewById(R.id.alamat)
        img_back = findViewById(R.id.btnBack)

        list_password = findViewById(R.id.listPassword)
        list_tentang = findViewById(R.id.listTentang)
        listProfil = findViewById(R.id.listProfil)
        listKeluar = findViewById(R.id.listKeluar)

        img_back.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        list_password.setOnClickListener {
            startActivity(Intent(this, UpdatePassword::class.java))

        }

        list_tentang.setOnClickListener {
            startActivity(Intent(this, TentangProfil::class.java))

        }

        listProfil.setOnClickListener {
            startActivity(Intent(this, UpdateProfil::class.java))
        }

        listKeluar.setOnClickListener{
            shared.logoUt()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        onstart()
    }

    private fun onstart() {
        val user = shared.getUserDetail();
        txt_nama.text = user.get(shared.KEY_EMAIL)
        txt_alamat.text = user.get(shared.KEY_ALAMAT)
        txt_nohp.text = user.get(shared.KEY_PASSWORD)
    }



}