package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create_entity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appEcosistemas.data.entity.EntidadAumentadaRequest
import com.example.appEcosistemas.ui.ecosistema_digital_aumentado.EcosistemaFragment
import com.example.appEcosistemas.ui.main.ChangeFragmentListener
import com.example.appEcosistemas.util.LoadingDialog
import com.example.appEcosistemas.databinding.FragmentEcosistemaCreateEntityBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


class EcosistemaCreateEntityFragment: Fragment(), View.OnClickListener, EcosistemaCreateEntityContract.View{

    private var _binding: FragmentEcosistemaCreateEntityBinding? = null
    private val binding get() = _binding!!
    var listener: ChangeFragmentListener? = null
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var presenter: EcosistemaCreateEntityPresenter
    private lateinit var client: FusedLocationProviderClient
    var userId = "0"
    var ecosistemId = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize location client
        client = LocationServices.getFusedLocationProviderClient(requireActivity());
        presenter = EcosistemaCreateEntityPresenter(this, requireContext())
        _binding = FragmentEcosistemaCreateEntityBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireActivity())
        initValues()
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
        // Check condition
        if (requestCode == 100 && grantResults.isNotEmpty()
            && (grantResults[0] + grantResults[1]
                    == PackageManager.PERMISSION_GRANTED)
        ) {
            // When permission are granted
            // Call  method
            getCurrentLocation()
        } else {
            // When permission are denied
            // Display toast
            Toast
                .makeText(
                    activity,
                    "Permission denied",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        // Initialize Location manager
        val locationManager = requireActivity().getSystemService(
                Context.LOCATION_SERVICE
            ) as LocationManager
        // Check condition
        if (locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
            || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            // When location service is enabled
            // Get last location
            client.lastLocation.addOnCompleteListener { task ->
                // Initialize location
                val location: Location? = task.result
                // Check condition
                if (location != null) {
                    // When location result is not
                    // null set latitude
                    binding.longitudEditText.setText(location.longitude.toString())
                    // set longitude
                    binding.latitudEditText.setText(location.latitude.toString())
                    // set altitude
                    binding.altitudEditText.setText(location.altitude.toString())
                } else {
                    // When location result is null
                    // initialize location request

                    // Initialize location call back
                    val locationCallback: LocationCallback = object : LocationCallback() {
                        override fun onLocationResult(
                            locationResult: LocationResult
                        ) {
                            // Initialize
                            // location
                            val location1: Location = locationResult.lastLocation
                            binding.longitudEditText.setText(location1.longitude.toString())
                            // set longitude
                            binding.latitudEditText.setText(location1.latitude.toString())
                            // set altitude
                            binding.altitudEditText.setText(location1.altitude.toString())
                        }
                    }

                    // Request location updates
//                    client.requestLocationUpdates(
//                        locationRequest,
//                        locationCallback,
//                        Looper.myLooper()
//                    )
                }
            }
        } else {
            // When location service is not enabled
            // open location setting
            startActivity(
                Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                    .setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                    )
            )
        }
    }

    private fun initValues() {
        binding.btnCrearEntidad.setOnClickListener(this)
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation()
        } else {
            // When permission is not granted
            // Call method

            // When permission is not granted
            // Call method
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnCrearEntidad.id ->{
                var error = false
                binding.nombreEntidadEditText.error = null
                binding.descripcionEntidadEditText.error = null
                if(binding.nombreEntidadEditText.text!!.isEmpty()){
                    error = true
                    binding.nombreEntidadEditText.error = "Campo vacío"
                }
                if(binding.descripcionEntidadEditText.text!!.isEmpty()){
                    error = true
                    binding.descripcionEntidadEditText.error = "Campo vacío"
                }
                if (!error){
                    val entidadAumentadaRequest = EntidadAumentadaRequest(
                        null,
                        binding.nombreEntidadEditText.text.toString(),
                        userId,
                        binding.descripcionEntidadEditText.text.toString(),
                        ecosistemId,
                        binding.longitudEditText.text.toString().toDouble(),
                        binding.latitudEditText.text.toString().toDouble(),
                        binding.altitudEditText.text.toString().toDouble())
                    presenter.createEntityTrigger(entidadAumentadaRequest)
                }
            }

        }
    }

    override fun onEntityExists() {
        TODO("Not yet implemented")
    }

    override fun onCreateEntitySuccess() {
        val ecosistemaFragment = EcosistemaFragment()
        ecosistemaFragment.userId = userId
        replaceFragment(ecosistemaFragment)
    }

    override fun onCreateEntityUnSuccess() {
        TODO("Not yet implemented")
    }

    override fun showProgressDialog(message: String) {
        if (loadingDialog.isLoading()) {
            hideProgressDialog()
        }
        loadingDialog.startLoading(message)
    }

    override fun hideProgressDialog() {
        loadingDialog.isDismiss()
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(com.example.appEcosistemas.R.id.fl_container, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

}