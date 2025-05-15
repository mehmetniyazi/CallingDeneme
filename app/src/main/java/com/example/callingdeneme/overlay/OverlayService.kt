package com.example.callingdeneme.overlay

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.app.Service.START_NOT_STICKY
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.callingdeneme.R
import java.security.Provider

// service/OverlayService.kt
class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private var overlayView: View? = null

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        Log.d("overlayy","overlay created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action){
            Actions.START.toString() -> {
                start()
            }
            Actions.STOP.toString() -> stopSelf()
        }

        /*val displayName = intent?.getStringExtra("DISPLAY_NAME") ?: "Bilinmeyen"

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        overlayView = inflater.inflate(R.layout.overlay_call_info, null)
        overlayView?.findViewById<TextView>(R.id.nameText)?.text = displayName

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.addView(overlayView, params)

        Handler(Looper.getMainLooper()).postDelayed({ stopSelf() }, 5000) // 5 saniye sonra kapat*/
        return START_NOT_STICKY
    }

    private fun start(){
        val notification = NotificationCompat.Builder(
            this,
            "running"
        ).setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Run is Active")
            .setContentText("Deneme Deneme")
            .build()
        startForeground(1,notification)
    }


    override fun onDestroy() {
        super.onDestroy()
        overlayView?.let { windowManager.removeView(it) }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    enum class Actions{
        START,
        STOP
    }
}
