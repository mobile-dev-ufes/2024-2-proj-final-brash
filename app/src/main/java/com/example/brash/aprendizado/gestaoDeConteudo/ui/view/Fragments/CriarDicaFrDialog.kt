package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarDicaVM
import com.example.brash.databinding.GtcListarDicaFrCriarDicaBinding

/**
 * A DialogFragment that allows the user to create a new hint ("Dica").
 * It provides an input field for the hint text and buttons for confirming or canceling the creation.
 *
 * @constructor Creates an instance of `CriarDicaFrDialog`.
 */
class CriarDicaFrDialog : DialogFragment() {

    private var _binding: GtcListarDicaFrCriarDicaBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarDicaVM: ListarDicaVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarDicaFrCriarDicaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarDicaVM = ViewModelProvider(requireActivity())[ListarDicaVM::class.java]

        setOnClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setObservers(){
    }

    private fun setOnClickListeners(){
        binding.ListarDicaFrCriarDicaButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.ListarDicaFrCriarDicaButtonCriar.setOnClickListener {
            val hintText = binding.ListarDicaFrCriarDicaInputTexto.text.toString()
            listarDicaVM.criarDica(hintText, {
                dismiss()
            })

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
