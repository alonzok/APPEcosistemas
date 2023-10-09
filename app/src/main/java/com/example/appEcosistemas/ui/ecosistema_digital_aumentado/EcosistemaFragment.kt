package com.example.appEcosistemas.ui.ecosistema_digital_aumentado

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.appEcosistemas.data.entity.EcosistemaDigitalAumentado
import com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_create.EcosistemaCreateFragment
import com.example.appEcosistemas.ui.ecosistema_digital_aumentado.ecosistema_details.EcosistemaDetailsFragment
import com.example.appEcosistemas.ui.main.ChangeFragmentListener
import com.example.appEcosistemas.util.LoadingDialog
import com.example.appEcosistemas.util.ViewCommonFunctions
import com.example.appEcosistemas.databinding.FragmentEcosistemaDigitalBinding


class EcosistemaFragment : Fragment(), EcosistemaContract.View, View.OnClickListener{

    private var _binding: FragmentEcosistemaDigitalBinding? = null
    private val binding get() = _binding!!
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var presenter: EcosistemaPresenter
    private lateinit var ecosistemas: List<EcosistemaDigitalAumentado>
    private lateinit var ecosistemasSpinner: Spinner
    private lateinit var ecosistemaEscogido: EcosistemaDigitalAumentado
    var listener: ChangeFragmentListener? = null
    var userId: String = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        presenter = EcosistemaPresenter(this, requireContext())
        _binding = FragmentEcosistemaDigitalBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireActivity())
        ecosistemasSpinner = binding.spnEcosistemas
        ecosistemasSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ecosistemaEscogido = ecosistemas[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        initValues()
        return binding.root
    }


    private fun initValues() {
        presenter.getEcosistemasTrigger()
        binding.btnCrearEcosistema.setOnClickListener(this)
        binding.btnSeleccionar.setOnClickListener(this)
    }

    private fun initRecyclerView(){
        val ecosistemaString: ArrayList<String> = ArrayList()
        for (ecosistema in ecosistemas){
            ecosistemaString.add(ecosistema.nombreDelEcosistema)
        }
        binding.spnEcosistemas.adapter =
            ArrayAdapter(requireContext(),
                R.layout.simple_spinner_item,
                ecosistemaString
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(R.layout.simple_list_item_1)
            }

    }

    override fun onGetEcosistemasUnSuccess() {
        initRecyclerView()
        ViewCommonFunctions.showAlertDialogInformative(
            requireContext(),
            "Error",
            "Hubo un error al obtener las ecosistemas, intente más tarde"
        )
    }

    override fun onGetEcosistemasSuccess(ecosistemas: List<EcosistemaDigitalAumentado>) {
        this.ecosistemas = ecosistemas
        initRecyclerView()
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

    override fun onClick(p0: View?) {
        when (p0?.id) {
            com.example.appEcosistemas.R.id.btn_crear_ecosistema -> {
                goToCreate()
            }
            com.example.appEcosistemas.R.id.btn_seleccionar ->{
                goToDetails()
            }
        }
    }

    private fun goToCreate() {
        val create = EcosistemaCreateFragment()
        create.userId = userId
        replaceFragment(create)
    }

    private fun goToDetails() {
        val detail = EcosistemaDetailsFragment()
        detail.userId = userId
        detail.ecosistemaEscogido = ecosistemaEscogido
        replaceFragment(detail)

    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(com.example.appEcosistemas.R.id.fl_container, fragment)
        transaction.addToBackStack(fragment.toString())
        transaction.commit()
    }
}