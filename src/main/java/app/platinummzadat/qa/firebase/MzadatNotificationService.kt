package app.platinummzadat.qa.firebase

import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import app.platinummzadat.qa.currentUserId
import app.platinummzadat.qa.views.root.RootActivity
import raj.nishin.wolfpack.currentLocalTimeInMillis
import raj.nishin.wolfpack.getTimeFromMillis
import raj.nishin.wolfpack.wlog

class MzadatNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        wlog("NOTIFICATION: ${remoteMessage.data}")
        with(remoteMessage.data) {
            val title = if (keys.contains("title")) this["title"] ?: "New Notification" else "New Notification"
            val message = if (keys.contains("message")) this["message"] ?: "Tap to see more" else "Tap to see more"
            val type = if (keys.contains("type")) this["type"] ?: "notification" else "notification"
            val auctionId = (if (keys.contains("item_id")) this["item_id"] ?: "-1" else "-1").toInt()

            /*val serverId = if (keys.contains("server_id")) try {
                this["server_id"]?.toInt()
            } catch (e: Exception) {
                -1
            } else -1
            val parentServerId = if (keys.contains("parent_server_id")) try {
                this["parent_server_id"]?.toInt()
            } catch (e: Exception) {
                -1
            } else -1
            val notificationId = if (keys.contains("notification_id")) try {
                this["notification_id"]?.toInt()
            } catch (e: Exception) {
                -1
            } else -1
            val type = if (keys.contains("type")) try {
                this["type"]?.toInt()
            } catch (e: Exception) {
                NOTIFICATION_ALERT
            } else NOTIFICATION_ALERT
            val img = if (keys.contains("image_url")) this["image_url"] ?: "" else ""*/
            val timeStamp = if (keys.contains("timestamp")) this["timestamp"] ?: getTimeFromMillis(
                currentLocalTimeInMillis, "yyyy-MM-dd hh:mm:ss"
            ) else getTimeFromMillis(
                currentLocalTimeInMillis, "yyyy-MM-dd hh:mm:ss"
            )
            val broadcast = Intent().apply {
                action = "mzadat.new_notification"
                putExtra("title", title)
                putExtra("message", message)
            }
            sendBroadcast(broadcast)
            if (-1!= currentUserId) {
                val intent = Intent(applicationContext, RootActivity::class.java).apply {
                    putExtra("type", type)
                    putExtra("item_id", auctionId)
                }
                showNotificationMessage(
                    applicationContext, title, message, timeStamp, intent
                )
                /*if (img.isEmpty()) {
                    showNotificationMessage(
                        applicationContext, title, message, timeStamp, intent
                    )
                } else {
                    showNotificationMessageWithBigImage(
                        applicationContext, title, message, timeStamp, intent, img
                    )
                }*/
            }

        }
    }

    private fun showNotificationMessage(
        context: Context,
        title: String,
        message: String,
        timeStamp: String,
        intent: Intent
    ) {
        val notificationUtils = NotificationUtils(context)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent)
    }

    private fun showNotificationMessageWithBigImage(
        context: Context,
        title: String,
        message: String,
        timeStamp: String,
        intent: Intent,
        imageUrl: String
    ) {
        val notificationUtils = NotificationUtils(context)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl)
    }
}
