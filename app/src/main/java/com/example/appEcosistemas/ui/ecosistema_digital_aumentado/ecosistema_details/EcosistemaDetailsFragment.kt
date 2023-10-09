package com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_details

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.appEcosistemas.databinding.FragmentEcosistemaDetailsBinding
import com.example.appEcosistemas.data.constants.TipoDisparador
import com.example.appEcosistemas.data.constants.TipoEcosistema
import com.example.appEcosistemas.data.entity.*
import com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create_entity.EcosistemaCreateEntityFragment
import com.example.appEcosistemas.ui.main.ChangeFragmentListener
import com.example.appEcosistemas.util.LoadingDialog

class EcosistemaDetailsFragment: Fragment(), View.OnClickListener, EcosistemaDetailsContract.View{

    private var _binding: FragmentEcosistemaDetailsBinding? = null
    private val binding get() = _binding!!
    var listener: ChangeFragmentListener? = null
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var presenter: EcosistemaDetailsPresenter
    private lateinit var disparadorSpinner: Spinner
    private lateinit var tipoEcosistemaSpinner: Spinner
    var userId = "0"
    var ecosistemaEscogido: EcosistemaDigitalAumentado? = null
    private var tipoDisparadorPosition = 0
    private var tipoEcosistemaPosition = 0

    companion object {
        const val USER_ID = "ALUMNO"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        presenter = EcosistemaDetailsPresenter(this, requireContext())
        _binding = FragmentEcosistemaDetailsBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireActivity())
        disparadorSpinner = binding.spnDisparadorDetails
        disparadorSpinner.onItemSelectedListener = object :
            OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipoDisparadorPosition = position

                when(TipoDisparador.values()[position]){
                    TipoDisparador.MARCADOR ->{
                        //hide file field
                        binding.longitudDetails.visibility = View.GONE
                        binding.latitudDetails.visibility = View.GONE
                        binding.altitudDetails.visibility = View.GONE
                        binding.rangoDiametroDetails.visibility = View.GONE
                        binding.longitudEditTextDetails.text = null
                        binding.latitudEditTextDetails.text = null
                        binding.altitudEditTextDetails.text = null
                        binding.rangoDiametroEditTextDetails.text = null
                    }
                    TipoDisparador.UBICACION ->{
                        binding.longitudDetails.visibility = View.VISIBLE
                        binding.latitudDetails.visibility = View.VISIBLE
                        binding.altitudDetails.visibility = View.VISIBLE
                        binding.rangoDiametroDetails.visibility = View.VISIBLE
                        binding.longitudEditTextDetails.setText(ecosistemaEscogido?.longitud.toString())
                        binding.latitudEditTextDetails.setText(ecosistemaEscogido?.latitud.toString())
                        binding.altitudEditTextDetails.setText(ecosistemaEscogido?.altitud.toString())
                    }
                    TipoDisparador.OPORTUNIDAD ->{
                        binding.longitudDetails.visibility = View.GONE
                        binding.latitudDetails.visibility = View.GONE
                        binding.altitudDetails.visibility = View.GONE
                        binding.rangoDiametroDetails.visibility = View.GONE
                        binding.longitudEditTextDetails.text = null
                        binding.latitudEditTextDetails.text = null
                        binding.altitudEditTextDetails.text = null
                        binding.rangoDiametroEditTextDetails.text = null
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        tipoEcosistemaSpinner = binding.spnTipoEcosistemaDetails
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


    private fun initValues() {
        binding.btnModificarEcosistemaDetails.setOnClickListener(this)
        binding.btnAgregarEntidad.setOnClickListener(this)
        binding.btnEliminar.setOnClickListener(this)
        binding.descripcionEcosistemaEditTextDetails.setText(ecosistemaEscogido?.descripcion)
        binding.nombreEcosistemaEditTextDetails.setText(ecosistemaEscogido?.nombreDelEcosistema)
        binding.cantidadUsuarioEditTextDetails.setText(ecosistemaEscogido?.cantidadUsuarios.toString())
        binding.rangoDiametroEditTextDetails.setText(ecosistemaEscogido?.rangoDiametro.toString())
        tipoDisparadorPosition = ecosistemaEscogido?.tipoDisparador!!.ordinal
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.spnDisparadorDetails.adapter =
            ArrayAdapter(requireContext(),
                R.layout.simple_spinner_item,
                TipoDisparador.values()
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(R.layout.simple_list_item_1)
            }
        binding.spnDisparadorDetails.setSelection(tipoDisparadorPosition)
        binding.spnTipoEcosistemaDetails.adapter =
            ArrayAdapter(requireContext(),
                R.layout.simple_spinner_item,
                TipoEcosistema.values()
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(R.layout.simple_list_item_1)
            }
        binding.spnTipoEcosistemaDetails.setSelection(ecosistemaEscogido?.tipoEcosistema!!.ordinal)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnModificarEcosistemaDetails.id ->{
                var error = false
                binding.nombreEcosistemaEditTextDetails.error = null
                binding.descripcionEcosistemaEditTextDetails.error = null
                binding.cantidadUsuarioEditTextDetails.error = null
                if(binding.nombreEcosistemaEditTextDetails.text!!.isEmpty()){
                    error = true
                    binding.nombreEcosistemaEditTextDetails.error = "Campo vacío"
                }
                if(binding.descripcionEcosistemaEditTextDetails.text!!.isEmpty()){
                    error = true
                    binding.descripcionEcosistemaEditTextDetails.error = "Campo vacío"
                }
                if(binding.cantidadUsuarioEditTextDetails.text!!.isEmpty()){
                    error = true
                    binding.cantidadUsuarioEditTextDetails.error = "Campo vacío"
                }
                if (!error){
                    val ecosistemaRequest = EcosistemaDigitalAumentadoRequest(
                        ecosistemaEscogido?.id,
                        binding.cantidadUsuarioEditTextDetails.text.toString().toInt(),
                        binding.nombreEcosistemaEditTextDetails.text.toString(),
                        userId,
                        binding.descripcionEcosistemaEditTextDetails.text.toString(),
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

                        }
                        TipoDisparador.UBICACION ->{
                            ecosistemaRequest.longitud = binding.longitudEditTextDetails.text.toString().toDouble()
                            ecosistemaRequest.latitud = binding.latitudEditTextDetails.text.toString().toDouble()
                            ecosistemaRequest.altitud = binding.altitudEditTextDetails.text.toString().toDouble()
                            ecosistemaRequest.rangoDiametro = binding.rangoDiametroEditTextDetails.text.toString().toDouble()
                        }
                        TipoDisparador.OPORTUNIDAD ->{

                        }
                    }
                    presenter.createEcosistemaTrigger(ecosistemaRequest, TipoDisparador.values()[tipoDisparadorPosition])
                }
            }

            binding.btnAgregarEntidad.id ->{
                val createFragment = EcosistemaCreateEntityFragment()
                createFragment.userId = userId
                createFragment.ecosistemId = ecosistemaEscogido!!.id
                replaceFragment(createFragment)
            }

            binding.btnEliminar.id ->{
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Está seguro de eliminar el ecosistema?")
                    .setCancelable(false)
                    .setPositiveButton("Si") { dialog, id ->
                        presenter.deleteEcosistemaTrigger(ecosistemaEscogido!!.id)
                    }
                    .setNegativeButton("No"){ dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }
    }

    override fun onCreateEcosistemaSuccess() {
        val transaction = requireActivity().supportFragmentManager
        transaction.popBackStackImmediate()
    }

    override fun onCreateEcosistemaUnSuccess() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Error en eliminar ecosistema")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, id ->
                dialog.dismiss()
            }
    }

    override fun onDeleteEcosistemaSuccess() {
        val transaction = requireActivity().supportFragmentManager
        transaction.popBackStackImmediate()
    }

    override fun onDeleteEcosistemaUnSuccess() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Error en crear ecosistema")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, id ->
                dialog.dismiss()
            }
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