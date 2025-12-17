package com.alirsyad.app.utils

import android.R
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.util.Base64.decode
import android.webkit.JavascriptInterface
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.util.*


class JavascriptInterface(private val context: Context) {

    @JavascriptInterface
    @Throws(IOException::class)
    fun getBase64FromBlobData(base64Data: String) {
        convertBase64StringToPdfAndStoreIt(base64Data)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun convertBase64StringToPdfAndStoreIt(base64PDf: String) {
        val notificationId = 1
        val currentDateTime: String = DateFormat.getDateTimeInstance().format(Date())
        val dwldsPath = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ).toString() + "/YourFileName_" + currentDateTime + "_.pdf"
        )
        val pdfAsBytes: ByteArray =
            decode(base64PDf.replaceFirst("^data:application/pdf;base64,".toRegex(), ""), 0)
        val os = FileOutputStream(dwldsPath, false)
        os.write(pdfAsBytes)
        os.flush()
        if (dwldsPath.exists()) {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val apkURI: Uri = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName.toString() + ".provider",
                dwldsPath
            )
            intent.setDataAndType(
                apkURI,
                MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val pendingIntent =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            } else {
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }



            val CHANNEL_ID = "MYCHANNEL"
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel =
                    NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW)
                val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentText("You have got something new!")
                    .setContentTitle("File downloaded")
                    .setContentIntent(pendingIntent)
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(R.drawable.sym_action_chat)
                    .build()
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notificationChannel)
                    notificationManager.notify(notificationId, notification)
                }
            } else {
                val b: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.sym_action_chat) //.setContentIntent(pendingIntent)
                    .setContentTitle("MY TITLE")
                    .setContentText("MY TEXT CONTENT")
                if (notificationManager != null) {
                    notificationManager.notify(notificationId, b.build())
                    val h = Handler()
                    val delayInMilliseconds: Long = 1000
                    h.postDelayed(
                        { notificationManager.cancel(notificationId) },
                        delayInMilliseconds
                    )
                }
            }
        }
        Toast.makeText(context, "PDF FILE DOWNLOADED!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getBase64StringFromBlobUrl(blobUrl: String): String {
            return if (blobUrl.startsWith("blob")) {
                "javascript: var xhr = new XMLHttpRequest();" +
                        "xhr.open('GET', '" + blobUrl + "', true);" +
                        "xhr.setRequestHeader('Content-type','application/pdf');" +
                        "xhr.responseType = 'blob';" +
                        "xhr.onload = function(e) {" +
                        "    if (this.status == 200) {" +
                        "        var blobPdf = this.response;" +
                        "        var reader = new FileReader();" +
                        "        reader.readAsDataURL(blobPdf);" +
                        "        reader.onloadend = function() {" +
                        "            base64data = reader.result;" +
                        "            Android.getBase64FromBlobData(base64data);" +
                        "        }" +
                        "    }" +
                        "};" +
                        "xhr.send();"
            } else "javascript: console.log('It is not a Blob URL');"
        }
    }

}