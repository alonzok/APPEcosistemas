package com.example.appEcosistemas.ui.perfil

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.appEcosistemas.R
import com.example.appEcosistemas.data.entity.Alumno
import com.example.appEcosistemas.data.entity.Escuela
import com.example.appEcosistemas.databinding.FragmentPerfilBinding
import com.example.appEcosistemas.ui.main.ChangeFragmentListener
import com.example.appEcosistemas.util.LoadingDialog
import com.example.appEcosistemas.util.ViewCommonFunctions

class PerfilFragment : Fragment(), View.OnClickListener, PerfilContract.View {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: PerfilPresenter
    private lateinit var loadingDialog: LoadingDialog
    var listener: ChangeFragmentListener? = null
    private var alumno: Alumno? = null
    var alumnoID: Long = -1
    private var modify = false

    private var escuelas: List<Escuela> = arrayListOf()

    private var grados: List<String> = arrayListOf()

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val imagen: String
            val uriContent = result.uriContent
            if (uriContent != null) {
                imagen = presenter.uriToBase64(uriContent)
                alumno?.foto = imagen
                binding.civProfileImage.setImageURI(uriContent)
            } else {
                ViewCommonFunctions.showAlertDialogInformative(
                    requireContext(),
                    "Error",
                    "Hubo un error al cargar la imagen."
                )
            }
        } else {
            ViewCommonFunctions.showAlertDialogInformative(
                requireContext(),
                "Error",
                "Hubo un problema al cargar la imagen, vuelva a intentarlo"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        presenter = PerfilPresenter(this, requireContext())
        loadingDialog = LoadingDialog(requireActivity())
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (alumnoID != -1L) {
            initListeners()
            initRecyclerViews()
            presenter.getAlumnoTrigger(alumnoID)
            binding.btnCancelProfile.setText(R.string.modify)
        } else {
            onGetAlumnoUnSuccess()
        }
    }

    private fun initListeners() {
        binding.btnSaveProfile.setOnClickListener(this)
        binding.btnCancelProfile.setOnClickListener(this)
        binding.civProfileImage.setOnClickListener(this)
    }

    private fun initRecyclerViews(schoolPosition: Int = -1, gradePosition: Int = -1) {
        binding.spnSchoolProfile.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                escuelas
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            }
        binding.spnGradeProfile.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                grados
            ).also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            }
        if (schoolPosition > 0) {
            binding.spnSchoolProfile.setSelection(schoolPosition)
        }
        if (gradePosition > 0) {
            binding.spnGradeProfile.setSelection(gradePosition)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_save_profile -> {
                clearAll()
                if (escuelas.isEmpty()) {
                    ViewCommonFunctions.showAlertDialogInformative(
                        requireContext(),
                        "Error",
                        "No se lograron cargar las escuelas, intente más tarde"
                    )
                } else if (grados.isEmpty()) {
                    ViewCommonFunctions.showAlertDialogInformative(
                        requireContext(),
                        "Error",
                        "No se lograron cargar las escuelas, intente más tarde"
                    )
                } else if (binding.tieProfileUsername.text!!.isEmpty()) {
                    binding.tilProfileUsername.error = "Campo vacio"
                } else if (binding.tieProfileName.text!!.isEmpty()) {
                    binding.tilProfileName.error = "Campo vacio"
                } else if (binding.tieProfileLastnameP.text!!.isEmpty()) {
                    binding.tilProfileLastnameP.error = "Campo vacio"
                } else if (binding.tieProfileLastnameM.text!!.isEmpty()) {
                    binding.tilProfileLastnameM.error = "Campo vacio"
                } else {
                    presenter.saveAlumnoTrigger(
                        alumno!!.id,
                        binding.tieProfileUsername.text.toString(),
                        alumno!!.foto,
                        binding.tieProfileName.text.toString(),
                        binding.tieProfileLastnameP.text.toString(),
                        binding.tieProfileLastnameM.text.toString(),
                        binding.spnSchoolProfile.selectedItem.toString(),
                        binding.spnGradeProfile.selectedItem.toString(),
                        alumno!!.calif1,
                        alumno!!.calif2,
                        alumno!!.calif3
                    )
                }
            }
            R.id.btn_cancel_profile -> {
                if (modify) {
                    listener!!.goToEcosistemaFragment()
                    modify = false
                }
            }
            R.id.civ_profile_image -> {
                seleccionarFoto()
            }
        }
    }

    private fun enableAll(enable: Boolean) {
        binding.civProfileImage.isEnabled = enable
        binding.tieProfileUsername.isEnabled = enable
        binding.tieProfileName.isEnabled = enable
        binding.tieProfileLastnameP.isEnabled = enable
        binding.tieProfileLastnameM.isEnabled = enable
        binding.tilProfileUsername.isEnabled = enable
        binding.tilProfileName.isEnabled = enable
        binding.tilProfileLastnameP.isEnabled = enable
        binding.tilProfileLastnameM.isEnabled = enable
        binding.spnSchoolProfile.isEnabled = enable
        binding.spnGradeProfile.isEnabled = enable
        binding.btnSaveProfile.isEnabled = enable
    }

    private fun seleccionarFoto() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
            }
        )
    }

    private fun clearAll() {
        binding.tilProfileUsername.error = null
        binding.tilProfileName.error = null
        binding.tilProfileLastnameP.error = null
        binding.tilProfileLastnameM.error = null
    }

    private fun datosAlumno() {
        binding.tieProfileUsername.setText(alumno?.usuario)
        binding.tieProfileName.setText(alumno?.nombre)
        binding.tieProfileLastnameP.setText(alumno?.apellidoP)
        binding.tieProfileLastnameM.setText(alumno?.apellidoM)
        if (!alumno?.foto.isNullOrEmpty()) {
            binding.civProfileImage.setImageBitmap(presenter.base64ToBitmap(alumno!!.foto))
        }
        enableAll(false)
    }

    override fun onGetAlumnoSuccess(alumno: Alumno) {
        this.alumno = alumno
        datosAlumno()
        presenter.getEscuelasTrigger()
    }

    override fun onGetAlumnoUnSuccess() {
        val positive =
            DialogInterface.OnClickListener { _, _ -> listener?.goToEcosistemaFragment() }

        ViewCommonFunctions.showAlertDialogAccept(
            requireContext(),
            R.string.error,
            R.string.no_user_error,
            R.string.accept,
            positive
        )
    }

    override fun onGetEscuelasSuccess(escuelas: List<Escuela>) {
        this.escuelas = escuelas
        var gradosCantidad = 1
        var escuelaIndex = -1
        var gradoIndex = -1

        escuelas.forEachIndexed { index, escuela ->
            if (escuela.nombre == alumno!!.escuela) {
                gradosCantidad = escuela.grados
                escuelaIndex = index
            }
        }
        grados = presenter.enumerarGrados(gradosCantidad)
        grados.forEachIndexed { index, grado ->
            if (alumno!!.grado == grado) {
                gradoIndex = index
            }
        }
        initRecyclerViews(escuelaIndex, gradoIndex)
    }

    override fun onGetEscuelasUnSuccess() {
        ViewCommonFunctions.showAlertDialogInformative(
            requireContext(),
            "Error",
            "Hubo un error al obtener las escuelas, intente más tarde"
        )
    }

    override fun onsaveAlumnoSuccess(alumno: Alumno) {
        val positive =
            DialogInterface.OnClickListener { _, _ ->
                listener?.goToEcosistemaFragment()
                modify = false
            }

        ViewCommonFunctions.showAlertDialogAccept(
            requireContext(),
            R.string.notice,
            R.string.exit_save,
            R.string.accept,
            positive
        )
    }

    override fun onsaveAlumnoUnSuccess() {
        ViewCommonFunctions.showAlertDialogInformative(
            requireContext(),
            "Error",
            "Hubo un error al guardar su información, intente más tarde"
        )
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

}