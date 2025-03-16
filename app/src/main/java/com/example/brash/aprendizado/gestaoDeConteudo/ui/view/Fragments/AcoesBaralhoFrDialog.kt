package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarAnotacaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.RevisaoCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrAcoesBaralhoBinding
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication
import com.example.brash.utilsGeral.UtilsGeral

/**
 * A DialogFragment that provides actions related to a specific "Baralho" (Deck).
 * It allows the user to view, delete, or perform other actions on the deck such as viewing associated cards,
 * annotations, or reviewing the deck.
 * The deck to be acted upon is determined by the "baralhoEmFoco" property in the ViewModel.
 *
 * @constructor Creates an instance of `AcoesBaralhoFrDialog`.
 */
class AcoesBaralhoFrDialog() : DialogFragment() {

    private var _binding: GtcHomeFrAcoesBaralhoBinding? = null
    private val binding get() = _binding!!


    lateinit var homeVM: HomeVM
    private lateinit var appVM: AppVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrAcoesBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]
        appVM = (requireActivity().application as MyApplication).appSharedInformation

        setOnClickListeners()

    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.HomeFrAcoesBaralhoTextViewVisualizarBaralho.setOnClickListener {
            dismiss()
            Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarBaralho")
            VisualizarBaralhoFrDialog().show(parentFragmentManager, "VisualizarBaralhoDialog")
        }
        binding.HomeFrAcoesBaralhoTextViewVisualizarCartoes.setOnClickListener {
            dismiss()
            homeVM.baralhoEmFoco.value?.let {
                appVM.setBaralhoEmAC(it)
            } ?: run {
                //Toast.makeText(context, "Não foi possível carregar o baralho para cartão.", Toast.LENGTH_SHORT).show()
            }
            intentToListarCartaoActivity()
            //Toast.makeText(requireContext(), "Visualizar Cartões", Toast.LENGTH_SHORT).show()
        }
        binding.HomeFrAcoesBaralhoTextViewVisualizarAnotacoes.setOnClickListener {
            dismiss()
            homeVM.baralhoEmFoco.value?.let {
                appVM.setBaralhoEmAC(it)
            } ?: run {
                //Toast.makeText(context, "Não foi possível carregar o baralho para anotação.", Toast.LENGTH_SHORT).show()
            }
            intentToListarAnotacaoActivity()
        }
        binding.HomeFrAcoesBaralhoTextViewRevisarBaralho.setOnClickListener {
            dismiss()
            homeVM.baralhoEmFoco.value?.let {
                appVM.setBaralhoEmAC(it)
            } ?: run {
                //Toast.makeText(context, "REVISAOAC--->Não foi possível carregar o baralho para revisão.", Toast.LENGTH_SHORT).show()
            }
            intentToRevisaoCartaoActivity()
        }
        binding.HomeFrAcoesBaralhoTextViewMoverBaralho.setOnClickListener {
            dismiss()
            //Toast.makeText(requireContext(), "Mover Baralho", Toast.LENGTH_SHORT).show()
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("ListaPastaAdapter", "Tentando mostrar o diálogo")
                MoverBaralhoFrDialog().show(parentFragmentManager, "MoverBaralhoDialog")
            }
        }
        binding.HomeFrAcoesBaralhoTextViewExcluirBaralho.setOnClickListener {
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir esse Baralho??",{
                homeVM.baralhoEmFoco.value?.let { baralho ->
                    homeVM.excluirBaralho(baralho){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhum Baralho selecionada", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

    private fun intentToRevisaoCartaoActivity(){
        val intent = Intent(requireContext(), RevisaoCartaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent)
    }
    private fun intentToListarCartaoActivity(){
        val intent = Intent(requireContext(), ListarCartaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent)
    }

    private fun intentToListarAnotacaoActivity(){
        val intent = Intent(requireContext(), ListarAnotacaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent)
    }
}
