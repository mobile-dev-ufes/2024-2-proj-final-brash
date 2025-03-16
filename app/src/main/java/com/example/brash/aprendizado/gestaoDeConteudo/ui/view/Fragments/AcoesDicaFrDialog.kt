package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarDicaVM
import com.example.brash.databinding.GtcListarDicaFrAcoesDicaBinding
import com.example.brash.utilsGeral.UtilsGeral

/**
 * A DialogFragment that provides actions related to a specific "Dica" (Tip).
 * It allows the user to view or delete the tip. When the delete option is selected, a confirmation dialog is shown.
 * The tip to be acted upon is determined by the "dicaEmFoco" property in the ViewModel.
 *
 * @constructor Creates an instance of `AcoesDicaFrDialog`.
 */
class AcoesDicaFrDialog() : DialogFragment() {

    private var _binding: GtcListarDicaFrAcoesDicaBinding? = null
    private val binding get() = _binding!!


    private lateinit var listarDicaVM: ListarDicaVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarDicaFrAcoesDicaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarDicaVM = ViewModelProvider(requireActivity())[ListarDicaVM::class.java]

        setOnClickListeners()

    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.ListarDicaFrAcoesDicaTextViewVisualizarDica.setOnClickListener {
            dismiss()
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarDica")
                VisualizarDicaFrDialog().show(parentFragmentManager, "VisualizarDicaDialog")
            }
        }
        binding.ListarDicaFrAcoesDicaTextViewExcluirDica.setOnClickListener {
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir essa Dica??",{
                listarDicaVM.dicaEmFoco.value?.let { dica ->
                    listarDicaVM.excluirDica(dica){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhuma Dica selecionada", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
