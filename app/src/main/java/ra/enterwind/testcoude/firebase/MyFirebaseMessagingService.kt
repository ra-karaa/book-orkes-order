package ra.enterwind.testcoude.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ra.enterwind.testcoude.R
import ra.enterwind.testcoude.activity.pesanan.IndexActivity
import org.json.JSONObject




class MyFirebaseMessagingService : FirebaseMessagingService(){

    var json:JSONObject? = null

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("msg", "onMessageReceived: " + message)
        if (message.getNotification() != null) {
            Log.d("OKE", "Message From " + message.getFrom()); //sender ID
            Log.d("OKE", "Notification Title " + message.notification?.title) //notification title
            Log.d("OKE", "Notification Title " + message.notification?.body) //notification title
        }

        val intent = Intent(this, IndexActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = "Default"
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.img_2)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setContentIntent(pendingIntent)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(0, builder.build())
    }
}