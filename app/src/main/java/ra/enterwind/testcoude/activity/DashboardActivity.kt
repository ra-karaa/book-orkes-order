package ra.enterwind.testcoude.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.activity.auth.LoginActivity
import ra.enterwind.testcoude.activity.jadwal.IndexActivity
import ra.enterwind.testcoude.activity.profil.IndexProfil
import ra.enterwind.testcoude.session.Session


class DashboardActivity : AppCompatActivity() {

    lateinit var jadwal : CardView
    lateinit var name : TextView
    lateinit var pesanan : CardView
    lateinit var profil : CardView
    lateinit var sharedPreference : Session
    lateinit var fcm_token : String


    @SuppressLint("WrongConstant")
    override fun onCreate(saved : Bundle?){
        super.onCreate(saved)
        setContentView(R.layout.activity_dashboard)

        sharedPreference=Session(this)
        name = findViewById(R.id.namae)
        jadwal = findViewById(R.id.jadwal)
        pesanan = findViewById(R.id.pesanan)
        profil = findViewById(R.id.cardProfil)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val decorview = window.decorView
        val Uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorview.visibility = Uioptions;


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("Das", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("Das", token)
        })

        val detail = sharedPreference.getUserDetail()
        name.text = detail.get(sharedPreference.KEY_EMAIL)

        pesanan.setOnClickListener{
            startActivity(Intent(this, ra.enterwind.testcoude.activity.pesanan.IndexActivity::class.java))
        }

        jadwal.setOnClickListener{
            startActivity(Intent(this, IndexActivity::class.java))
        }

        profil.setOnClickListener {
            startActivity(Intent(this, IndexProfil::class.java))
        }
    }

}
