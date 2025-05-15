package com.example.callingdeneme

import android.annotation.SuppressLint
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.callingdeneme.databinding.FragmentFirstBinding
import com.example.callingdeneme.overlay.OverlayService


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val packageName = "com.example.callingdeneme"
        //tmDeneme()
        binding.buttonFirst.setOnClickListener {
            checkAndRequestPermissions()
            if (!Settings.canDrawOverlays(requireContext())) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }


            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonSecond.setOnClickListener {
            Intent(
                requireContext(),
                OverlayService::class.java,
            ).also {
                it.action = OverlayService.Actions.START.toString()
                requireActivity().startService(it)
            }
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.buttonThird.setOnClickListener {
            Intent(
                requireContext(),
                OverlayService::class.java
            ).also {
                it.action = OverlayService.Actions.STOP.toString()
                requireActivity().startService(it)

            }
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    @SuppressLint("MissingPermission", "NewApi")
    private fun tmDeneme() {
        val tm = requireActivity().getSystemService(TELEPHONY_SERVICE) as TelephonyManager?
        tm?.serviceState

    }


    private fun checkAndRequestPermissions(): Boolean {
        val readPhoneState =
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_PHONE_STATE
            )
        val read_call_log =
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_CALL_LOG
            )

        val notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.POST_NOTIFICATIONS
                )
            } else {
                PackageManager.PERMISSION_GRANTED
            }

        val listPermissionsNeeded: MutableList<String> = ArrayList<String>()

        if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_PHONE_STATE)
        }

        if (read_call_log != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_CALL_LOG)
        }

        if (read_call_log != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.PROCESS_OUTGOING_CALLS)
        }


        if (notification != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.POST_NOTIFICATIONS)
        }




        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                listPermissionsNeeded.toTypedArray() as Array<String?>,
                1000
            )

            return false
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}