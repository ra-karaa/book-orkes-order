package ra.enterwind.testcoude.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ra.enterwind.testcoude.R
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import ra.enterwind.testcoude.MainActivity
import ra.enterwind.testcoude.activity.auth.LoginActivity


class SplashActivity : AppCompatActivity(){

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstance : Bundle?){
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_splash)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val decorview = window.decorView
        val Uioptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorview.visibility = Uioptions;

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000);

    }
}