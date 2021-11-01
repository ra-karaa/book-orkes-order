package ra.enterwind.testcoude

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import android.widget.Toast
import ra.enterwind.testcoude.activity.DashboardActivity
import ra.enterwind.testcoude.activity.auth.LoginActivity


class MainActivity : AppCompatActivity() {

    val PREF_NAME = "Fietra Prabaskara"
    var PRIVATE_MODE = 0
    lateinit var shared : SharedPreferences
    val IS_LOGIN = "IsLogeIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shared = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        if (shared.getBoolean(IS_LOGIN, false)){
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}
