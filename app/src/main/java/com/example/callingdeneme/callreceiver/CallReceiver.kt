package com.example.callingdeneme.callreceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.TelephonyCallback
import android.telephony.TelephonyCallback.CallStateListener
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.callingdeneme.data.UserDataStore
import com.example.callingdeneme.overlay.OverlayService


// receiver/CallReceiver.kt
class CallReceiver : BroadcastReceiver() {


    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @RequiresApi(api = Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
        Log.d("call deneme","aramaya dustu")
        //registerCustomTelephonyCallback(context)
        if (state == TelephonyManager.EXTRA_STATE_RINGING && number != null) {
            Log.d("call deneme","aramaya dustu state $state")
            val matchedUser = UserDataStore.fincddUserByNumber(number)
            if (matchedUser != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val intent = Intent(
                        context,
                        OverlayService::class.java,
                    ).also {
                        it.action = OverlayService.Actions.START.toString()
                    }

                    context.startForegroundService(intent)
                } else {

                    val intent = Intent(
                        context,
                        OverlayService::class.java,
                    ).also {
                        it.action = OverlayService.Actions.START.toString()
                    }

                    context.startService((intent))
                }
                /*val serviceIntent = Intent(context, OverlayService::class.java).apply {
                    putExtra("DISPLAY_NAME", matchedUser.displayName)
                }
                context.startForegroundService(serviceIntent)*/
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.S)
internal class CustomTelephonyCallback(val callBack: CallBack) : TelephonyCallback(), CallStateListener {

    override fun onCallStateChanged(state: Int) {
        callBack.callStateChanged(state)
    }
}


@RequiresApi(Build.VERSION_CODES.S)
fun registerCustomTelephonyCallback(context: Context) {
    val telephony = context
        .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    telephony.registerTelephonyCallback(
        context.mainExecutor,
        CustomTelephonyCallback(object : CallBack {
            override fun callStateChanged(state: Int) {
                val myState = state
            }
        })
    )
}

internal interface CallBack {
    fun callStateChanged(state: Int)
}