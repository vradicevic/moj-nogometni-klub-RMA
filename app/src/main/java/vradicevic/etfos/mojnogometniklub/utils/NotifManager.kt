package vradicevic.etfos.mojnogometniklub.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import vradicevic.etfos.mojnogometniklub.MainActivity
import vradicevic.etfos.mojnogometniklub.R

object NotifManager {
    val  notificationChannel ="PLAYERS_UPDATED"
    val notificationID= 35215
    init{
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val title = "Nešto je novo"
            val descText = "Vaš klub ima novih informacija"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel=
                NotificationChannel(notificationChannel,title,importance)
                    .apply { description = descText }
            val notificationManager: NotificationManager = MyAppContext.getContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }


    }

    fun sendNotification(){
        val intent = Intent(MyAppContext.getContext(), MainActivity::class.java)
        // We can put extra in the intent.
        val pendingIntent = PendingIntent.getActivity(
            MyAppContext.getContext(),
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val builder = NotificationCompat.Builder(MyAppContext.getContext(), notificationChannel)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle("Nešto je novo")
            .setContentText("Vaš klub ima novih informacija")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(MyAppContext.getContext())){
            notify(notificationID, builder.build())
        }
    }
}