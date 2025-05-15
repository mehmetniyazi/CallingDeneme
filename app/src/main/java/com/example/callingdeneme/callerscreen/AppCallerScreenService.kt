package com.example.callingdeneme.callerscreen

import android.content.Intent
import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log
import com.example.callingdeneme.MainActivity
import com.example.callingdeneme.data.UserDataStore

class AppCallerScreenService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {
        val callResponseBuilder = CallResponse.Builder()
        // Make sure to remove the tel prefix and extract the phone number
        val phoneNumber = callDetails.handle.toString()
        val user = UserDataStore.findUserByNumber(phoneNumber)
        //Todo activity acabiliyorsun

        Log.d("call deneme","AppCallerScreenService $user")


        /*if (user!=null){
            // Here you can also add your custom view inside a window using WindowManager
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }*/
        respondToCall(callDetails, CallResponse.Builder().build())


    }
}