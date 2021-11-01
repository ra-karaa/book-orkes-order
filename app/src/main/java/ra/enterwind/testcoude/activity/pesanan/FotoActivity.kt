package ra.enterwind.testcoude.activity.pesanan

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Network
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ra.enterwind.testcoude.R
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.pixelcarrot.base64image.Base64Image
import ra.enterwind.testcoude.utils.Endpoint
import ra.enterwind.testcoude.utils.FileDataPart
import ra.enterwind.testcoude.utils.VolleyFileUploadRequest
import java.io.IOException


class FotoActivity : AppCompatActivity(), LifecycleObserver {

    private lateinit var imageView: ImageView
    lateinit var btn_upload : Button
    private var imageData: ByteArray? = null

    companion object {
        private val PIC_CAMERA = 9
        lateinit var id:String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_foto)

        imageView = findViewById(R.id.imageView)
        btn_upload = findViewById(ra.enterwind.testcoude.R.id.button)


        ambil_ijin_kemare()

        id = intent.getStringExtra("id").toString()

        btn_upload.setOnClickListener{
            upload_image_server()
        }
    }

    private fun upload_image_server() {
        val progress = ProgressDialog.show(this, null, "Loading")
        progress.show()
        val request = object : VolleyFileUploadRequest(
            Method.POST,
            Endpoint().url_upload + "/" + id,
            Response.Listener {
                NetworkResponse->
                if(NetworkResponse.statusCode.toString() == "200"){
                    progress.dismiss()
                    Toast.makeText(this, "Berhasil Upload", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, IndexActivity::class.java))
                    finish()
                } else {
                    progress.dismiss()
                    Toast.makeText(this, "Gagal Upload", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                progress.dismiss()
                startActivity(Intent(this, IndexActivity::class.java))
                finish()
            }
        ) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                var params = HashMap<String, FileDataPart>()
                params.put("struk", FileDataPart("image", imageData!!, "jpeg"))
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    private fun ambil_ijin_kemare() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PIC_CAMERA)
    }

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == PIC_CAMERA) {
            val uri = data?.data
            if (uri != null) {
                imageView.setImageURI(uri)
                createImageData(uri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun initializeCamera() {
        if (imageView == null) {
            ambil_ijin_kemare()
        }
    }

}
