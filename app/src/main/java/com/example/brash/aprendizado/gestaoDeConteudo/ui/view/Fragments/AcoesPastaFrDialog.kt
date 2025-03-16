package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrAcoesPastaBinding
import com.example.brash.utilsGeral.UtilsGeral

/**
 * A DialogFragment that provides actions related to a specific folder ("Pasta").
 * It allows the user to rename or delete the folder. When the delete option is selected, it also deletes all the decks within the folder.
 * The folder to be acted upon is determined by the "pastaEmFoco" property in the ViewModel.
 *
 * @constructor Creates an instance of `AcoesPastaFrDialog`.
 */
class AcoesPastaFrDialog() : DialogFragment() {

    private var _binding: GtcHomeFrAcoesPastaBinding? = null
    private val binding get() = _binding!!
    lateinit var homeVM: HomeVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrAcoesPastaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]
        setOnClickListeners()
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.HomeFrAcoesPastaTextViewRenomearPasta.setOnClickListener {
            dismiss()
            //Toast.makeText(requireContext(), "Renomear Pasta", Toast.LENGTH_SHORT).show()
            RenomearPastaFrDialog().show(parentFragmentManager, "RenomearPastaDialog")
        }
        binding.HomeFrAcoesPastaTextViewExcluirPasta.setOnClickListener {
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir essa Pasta?? Essa ação irá excluir TODOS os baralhos da pasta",{

                homeVM.pastaEmFoco.value?.let { pasta ->
                    homeVM.excluirPasta(pasta){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhuma pasta selecionada", Toast.LENGTH_SHORT).show()
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
