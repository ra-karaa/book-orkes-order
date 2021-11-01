package ra.enterwind.testcoude.activity.profil

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ra.enterwind.testcoude.R

class TentangProfil : AppCompatActivity(){

    lateinit var img_back : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_tentang)

        img_back = findViewById(R.id.ivBack)
        img_back.setOnClickListener{
            finish()
        }
    }
}