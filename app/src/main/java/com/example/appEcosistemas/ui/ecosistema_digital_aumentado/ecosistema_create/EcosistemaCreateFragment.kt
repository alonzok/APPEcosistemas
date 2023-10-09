package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create

import android.R.layout
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appEcosistemas.R
import com.example.appEcosistemas.data.constants.TipoDisparador
import com.example.appEcosistemas.data.constants.TipoEcosistema
import com.example.appEcosistemas.data.entity.EcosistemaDigitalAumentadoRequest
import com.example.appEcosistemas.databinding.FragmentEcosistemaCreateBinding
import com.example.appEcosistemas.ui.main.ChangeFragmentListener
import com.example.appEcosistemas.util.LoadingDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.io.File


class EcosistemaCreateFragment: Fragment(), View.OnClickListener, EcosistemaCreateContract.View{

    private var _binding: FragmentEcosistemaCreateBinding? = null
    private val binding get() = _binding!!
    var listener: ChangeFragmentListener? = null
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var presenter: EcosistemaCreatePresenter
    private lateinit var disparadorSpinner: Spinner
    private lateinit var tipoEcosistemaSpinner: Spinner
    private lateinit var client: FusedLocationProviderClient
    private var longitud: Double = 0.0
    private var latitud: Double = 0.0
    private var altitud: Double = 0.0
    var userId = "0"
    private var tipoDisparadorPosition = 0
    private var tipoEcosistemaPosition = 0
    private var imageUri: Uri? = null
    companion object {
        const val USER_ID = "ALUMNO"
        private const val IMAGE_PICK_CODE = 999
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize location client
        client = LocationServices.getFusedLocationProviderClient(requireActivity());
        presenter = EcosistemaCreatePresenter(this, requireContext())
        _binding = FragmentEcosistemaCreateBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireActivity())

        disparadorSpinner = binding.spnDisparador
        disparadorSpinner.onItemSelectedListener = object :
            OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipoDisparadorPosition = position

                when(TipoDisparador.values()[position]){
                    TipoDisparador.MARCADOR ->{
                        //hide file field
                        binding.longitud.visibility = View.GONE
                        binding.latitud.visibility = View.GONE
                        binding.altitud.visibility = View.GONE
                        binding.rangoDiametro.visibility = View.GONE
                        binding.marcador.visibility = View.VISIBLE
                        binding.imageView.visibility = View.VISIBLE
                        binding.longitudEditText.text = null
                        binding.latitudEditText.text = null
                        binding.altitudEditText.text = null
                        binding.rangoDiametroEditText.text = null
                    }
                    TipoDisparador.UBICACION ->{
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
                        binding.longitud.visibility = View.VISIBLE
                        binding.latitud.visibility = View.VISIBLE
                        binding.altitud.visibility = View.VISIBLE
                        binding.rangoDiametro.visibility = View.VISIBLE
                        binding.marcador.visibility = View.GONE
                        binding.imageView.visibility = View.GONE
                        binding.marcadorEditText.text = null
                        binding.longitudEditText.isEnabled = false
                        binding.latitudEditText.isEnabled = false
                        binding.altitudEditText.isEnabled = false
//                        var id = binding.imageView.drawable
                        binding.imageView.setImageDrawable(context!!.resources.getDrawable(R.drawable.ic_picture, context!!.theme))
                    }
                    TipoDisparador.OPORTUNIDAD ->{
                        binding.longitud.visibility = View.GONE
                        binding.latitud.visibility = View.GONE
                        binding.altitud.visibility = View.GONE
                        binding.rangoDiametro.visibility = View.GONE
                        binding.marcador.visibility = View.GONE
                        binding.imageView.visibility = View.GONE
                        binding.longitudEditText.text = null
                        binding.latitudEditText.text = null
                        binding.altitudEditText.text = null
                        binding.rangoDiametroEditText.text = null
                        binding.marcadorEditText.text = null
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        tipoEcosistemaSpinner = binding.spnTipoEcosistema
        tipoEcosistemaSpinner.onItemSelectedListener = object :
            OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipoEcosistemaPosition = position
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
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
                            latitud = location1.latitude
                            // set longitude
                            longitud = location1.longitude
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
        binding.btnCrearEcosistema.setOnClickListener(this)
        binding.imageView.setOnClickListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.spnDisparador.adapter =
            ArrayAdapter(requireContext(),
                layout.simple_spinner_item,
                TipoDisparador.values()
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(layout.simple_list_item_1)
            }
        binding.spnTipoEcosistema.adapter =
            ArrayAdapter(requireContext(),
                layout.simple_spinner_item,
                TipoEcosistema.values()
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(layout.simple_list_item_1)
            }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnCrearEcosistema.id ->{
                var error = false
                binding.nombreEcosistemaEditText.error = null
                binding.descripcionEcosistemaEditText.error = null
                binding.cantidadUsuarioEditText.error = null
                if(binding.nombreEcosistemaEditText.text!!.isEmpty()){
                    error = true
                    binding.nombreEcosistemaEditText.error = "Campo vacío"
                }
                if(binding.descripcionEcosistemaEditText.text!!.isEmpty()){
                    error = true
                    binding.descripcionEcosistemaEditText.error = "Campo vacío"
                }
                if(binding.cantidadUsuarioEditText.text!!.isEmpty()){
                    error = true
                    binding.cantidadUsuarioEditText.error = "Campo vacío"
                }
                if (!error){
                    val ecosistemaRequest = EcosistemaDigitalAumentadoRequest(
                        null,
                        binding.cantidadUsuarioEditText.text.toString().toInt(),
                        binding.nombreEcosistemaEditText.text.toString(),
                        userId,
                        binding.descripcionEcosistemaEditText.text.toString(),
                        TipoEcosistema.values()[tipoEcosistemaPosition],
                        TipoDisparador.values()[tipoDisparadorPosition],
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
                    when(TipoDisparador.values()[tipoDisparadorPosition]){
                        TipoDisparador.MARCADOR ->{
                            ecosistemaRequest.marcador = File(binding.marcadorEditText.text.toString())
                        }
                        TipoDisparador.UBICACION ->{
                            ecosistemaRequest.longitud = binding.longitudEditText.text.toString().toDouble()
                            ecosistemaRequest.latitud = binding.latitudEditText.text.toString().toDouble()
                            ecosistemaRequest.altitud = binding.altitudEditText.text.toString().toDouble()
                            ecosistemaRequest.rangoDiametro = binding.rangoDiametroEditText.text.toString().toDouble()
                        }
                        TipoDisparador.OPORTUNIDAD ->{

                        }
                    }
                    presenter.createEcosistemaTrigger(ecosistemaRequest, TipoDisparador.values()[tipoDisparadorPosition])
                }
            }

            binding.imageView.id -> {
                openImageChooser()
            }
        }
    }

    override fun onEcosistemaExists() {
        TODO("Not yet implemented")
    }

    override fun onCreateEcosistemaSuccess() {
        val transaction = requireActivity().supportFragmentManager
        transaction.popBackStackImmediate()
    }

    override fun onCreateEcosistemaUnSuccess() {
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

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.marcadorEditText.setText(data?.data.toString())
            binding.imageView.setImageURI(data?.data)
        }
    }
}