package ra.enterwind.testcoude.session

import android.content.Context
import android.content.SharedPreferences


class Session (context: Context){

    val PREF_NAME = "Fietra Prabaskara"
    val KEY_EMAIL = "name"
    val KEY_PASSWORD = "nomor_hp"
    var PRIVATE_MODE = 0
    var KEY_NAME = "email"
    var KEY_ALAMAT = "alamat"
    lateinit var shared : SharedPreferences

    val IS_LOGIN = "IsLogeIn"

    val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    val editor : SharedPreferences.Editor = sharedPref.edit()



    fun createLogin(name:String, nomor_hp:String, email : String, alamat : String){
        val editor = sharedPref.edit()
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_EMAIL, name)
        editor.putString(KEY_PASSWORD, nomor_hp)
        editor.putString(KEY_NAME, email)
        editor.putString(KEY_ALAMAT, alamat)
        editor.apply()
    }

    fun updateProfil(name:String, alamat: String, nomor_hp: String){
        editor.putString(KEY_EMAIL, name)
        editor.putString(KEY_ALAMAT, alamat)
        editor.putString(KEY_PASSWORD, nomor_hp)
        editor.commit()
        editor.apply()
    }

    fun isLogin(): Boolean {
        return sharedPref.getBoolean(IS_LOGIN, false)
    }

    fun logoUt(){
        editor.clear();
        editor.commit();
    }

    fun getUserDetail(): HashMap<String, String> {
        val user = HashMap<String, String>()
        sharedPref.getString(KEY_EMAIL, null)?.let { user.put(KEY_EMAIL, it) }
        sharedPref.getString(KEY_PASSWORD, null)?.let { user.put(KEY_PASSWORD, it) }
        sharedPref.getString(KEY_NAME, null)?.let { user.put(KEY_NAME, it) }
        sharedPref.getString(KEY_ALAMAT, null)?.let { user.put(KEY_ALAMAT, it) }
        return user
    }
}