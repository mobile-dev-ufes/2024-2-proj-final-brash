package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarAnotacaoVM
import com.example.brash.databinding.GtcListarAnotacaoFrAcoesAnotacaoBinding
import com.example.brash.utilsGeral.UtilsGeral

/**
 * A DialogFragment that provides actions related to a specific "Anotação" (Note).
 * It allows the user to view, delete, or perform other actions on the note.
 * The note to be acted upon is determined by the "anotacaoEmFoco" property in the ViewModel.
 *
 * @constructor Creates an instance of `AcoesAnotacaoFrDialog`.
 */
class AcoesAnotacaoFrDialog() : DialogFragment() {

    private var _binding: GtcListarAnotacaoFrAcoesAnotacaoBinding? = null
    private val binding get() = _binding!!


    private lateinit var listarAnotacaoVM: ListarAnotacaoVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarAnotacaoFrAcoesAnotacaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarAnotacaoVM = ViewModelProvider(requireActivity())[ListarAnotacaoVM::class.java]

        setOnClickListeners()

    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.ListarAnotacaoFrAcoesAnotacaoTextViewVisualizarAnotacao.setOnClickListener {
            dismiss()
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarBaralho")
                VisualizarAnotacaoFrDialog().show(parentFragmentManager, "VisualizarBaralhoDialog")
            }
        }
        binding.ListarAnotacaoFrAcoesAnotacaoTextViewExcluirAnotacao.setOnClickListener{
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir essa Anotação??",{
                listarAnotacaoVM.anotacaoEmFoco.value?.let { it ->
                    listarAnotacaoVM.excluirAnotacao(it){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhuma Anotacao selecionada", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

    private fun intentToListarCartaoActivity(){
        val intent = Intent(requireContext(), ListarCartaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent)
    }
}
