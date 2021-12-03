package app.platinummzadat.qa.firebase

import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import androidx.core.app.NotificationCompat
import app.platinummzadat.qa.R
import raj.nishin.wolfpack.currentLocalTimeInMillis
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat

class NotificationUtils(private val mContext: Context) {

    fun showNotificationMessage(title: String, message: String, timeStamp: String, intent: Intent) {
        showNotificationMessage(title, message, timeStamp, intent, null)
    }

    fun showNotificationMessage(title: String, message: String, timeStamp: String, intent: Intent, imageUrl: String?) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return


        // notification icon
        val icon = R.drawable.ic_nav_auctions

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val resultPendingIntent = PendingIntent.getActivity(
            mContext,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val mBuilder = NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)

        val alarmSound = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification"
        )

        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl != null && imageUrl.length > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                val bitmap = getBitmapFromURL(imageUrl)

                if (bitmap != null) {
                    showBigNotification(
                        bitmap,
                        mBuilder,
                        icon,
                        title,
                        message,
                        timeStamp,
                        resultPendingIntent,
                        alarmSound
                    )
                } else {
                    showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound)
                }
            }
        } else {
            showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound)
            playNotificationSound()
        }
    }


    private fun showSmallNotification(
        mBuilder: NotificationCompat.Builder,
        icon: Int,
        title: String,
        message: String,
        timeStamp: String,
        resultPendingIntent: PendingIntent,
        alarmSound: Uri
    ) {

        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.addLine(message)
        val notification: Notification = mBuilder
            .setSmallIcon(icon)
            .setColor(mContext.getResources().getColor(R.color.colorPrimary))
            .setTicker(title).setWhen(0)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(resultPendingIntent)
            .setSound(alarmSound)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            //                .setStyle(inboxStyle)
//                .setWhen(currentLocalTimeInMillis)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setWhen(getTimeMilliSec(timeStamp))
            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
            .setContentText(message)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .build()

        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Mzadat Alerts"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(mChannel)
        }
//        notificationManager.notify(NOTIFICATION_ID, notification)
        notificationManager.notify(currentLocalTimeInMillis.hashCode(), notification)
    }

    private fun showBigNotification(
        bitmap: Bitmap,
        mBuilder: NotificationCompat.Builder,
        icon: Int,
        title: String,
        message: String,
        timeStamp: String,
        resultPendingIntent: PendingIntent,
        alarmSound: Uri
    ) {
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        bigPictureStyle.setBigContentTitle(title)
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString())
        bigPictureStyle.bigPicture(bitmap)
        val notification: Notification
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {

        notification = mBuilder
            .setSmallIcon(icon)
            .setColor(mContext.getResources().getColor(R.color.colorPrimary))
            .setTicker(title).setWhen(0)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(resultPendingIntent)
            .setSound(alarmSound)
            .setStyle(bigPictureStyle)
//                .setWhen(currentLocalTimeInMillis)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setWhen(getTimeMilliSec(timeStamp))
            .setLargeIcon(bitmap)
            .setContentText(message)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .build()

        /*} else {
            *//*
            * New style begin
            * *//*
            val notificationLayout = RemoteViews(mContext.packageName, R.layout.custom_notification_collapsed)
            val notificationLayoutExpanded = RemoteViews(mContext.packageName, R.layout.custom_notification_expanded)
            notificationLayout.setTextViewText(R.id.tvTimestamp, DateUtils.formatDateTime(mContext, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME))
            notificationLayout.setTextViewText(R.id.tvNotificationTitle, title)
            notificationLayout.setTextViewText(R.id.tvNotificationText, message)
            notificationLayout.setTextViewText(R.id.tvNotificationTextLong, message)
            notificationLayout.setImageViewBitmap(R.id.ivSmallIcon, bitmap)// Change to small icon
            notificationLayoutExpanded.setTextViewText(R.id.tvTimestamp, DateUtils.formatDateTime(mContext, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME))
            notificationLayoutExpanded.setTextViewText(R.id.tvNotificationTitle, title)
            notificationLayoutExpanded.setTextViewText(R.id.tvNotificationText, message)
            notificationLayoutExpanded.setTextViewText(R.id.tvNotificationTextLong, message)
            notificationLayoutExpanded.setImageViewBitmap(R.id.ivSmallIcon, bitmap)// Change to small icon
            notificationLayoutExpanded.setImageViewBitmap(R.id.ivNotification, bitmap)
            notificationLayoutExpanded.setViewVisibility(R.id.tvNotificationTextLong, View.GONE)
            notificationLayout.setViewVisibility(R.id.tvTimestamp, View.GONE)
            notificationLayoutExpanded.setViewVisibility(R.id.tvTimestamp, View.GONE)
            notification = mBuilder
                    .setSmallIcon(icon)
                    .setColor(mContext.getResources().getColor(R.color.colorPrimaryDark))
                    .setTicker(title)
                    .setAutoCancel(true)
                    .setWhen(getTimeMilliSec(timeStamp))
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setSound(alarmSound)
                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(notificationLayout)
                    .setCustomBigContentView(notificationLayoutExpanded)
//                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                    .setContentText(message)
                    .setChannelId(NOTIFICATION_CHANNEL_ID)
                    .build()
            *//*
            * New style end
            * *//*
        }*/
        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Login2 CRM Notification", importance)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(NOTIFICATION_ID_BIG_IMAGE, notification)
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    fun getBitmapFromURL(strURL: String): Bitmap? {
        try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    // Playing notification sound
    fun playNotificationSound() {
        try {
            val alarmSound = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + mContext.getPackageName() + "/raw/notification"
            )
            val r = RingtoneManager.getRingtone(mContext, alarmSound)
            r.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        val PUSH_NOTIFICATION = "pushNotification"
        private val NOTIFICATION_ID = 100
        private val NOTIFICATION_ID_BIG_IMAGE = 101
        private val NOTIFICATION_CHANNEL_ID = "mz_notification_channel"

        /**
         * Method checks if the app is in background or not
         */
        fun isAppIsInBackground(context: Context): Boolean {
            var isInBackground = true
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                val runningProcesses = am.runningAppProcesses
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.getPackageName()) {
                                isInBackground = false
                            }
                        }
                    }
                }
            } else {
                val taskInfo = am.getRunningTasks(1)
                val componentInfo = taskInfo[0].topActivity
                if (componentInfo != null) {
                    if (componentInfo.packageName == context.getPackageName()) {
                        isInBackground = false
                    }
                }
            }

            return isInBackground
        }

        // Clears notification tray messages
        fun clearNotifications(context: Context) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }

        fun getTimeMilliSec(timeStamp: String): Long {
            val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            try {
                val date = format.parse(timeStamp)
                return date.getTime()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return 0
        }
    }
}