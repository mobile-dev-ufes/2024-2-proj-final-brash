package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarDicaAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcListarCartaoFrAcoesCartaoBinding
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication
import com.example.brash.utilsGeral.UtilsGeral

/**
 * A DialogFragment that provides actions related to a specific "Cartão" (Card).
 * It allows the user to view, delete, or view associated tips of the card. When the delete option is selected, a confirmation dialog is shown.
 * The card to be acted upon is determined by the "cartaoEmFoco" property in the ViewModel.
 *
 * @constructor Creates an instance of `AcoesCartaoFrDialog`.
 */
class AcoesCartaoFrDialog() : DialogFragment() {

    private var _binding: GtcListarCartaoFrAcoesCartaoBinding? = null
    private val binding get() = _binding!!


    private lateinit var listarCartaoVM: ListarCartaoVM
    private lateinit var appVM: AppVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarCartaoFrAcoesCartaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarCartaoVM = ViewModelProvider(requireActivity())[ListarCartaoVM::class.java]
        appVM = (requireActivity().application as MyApplication).appSharedInformation
        setOnClickListeners()

    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.ListarCartaoFrAcoesCartaoTextViewVisualizarCartao.setOnClickListener {
            dismiss()
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarBaralho")
                VisualizarCartaoFrDialog().show(parentFragmentManager, "VisualizarBaralhoDialog")
            }
        }
        binding.ListarCartaoFrAcoesCartaoTextViewVisualizarDicas.setOnClickListener {
            dismiss()
            listarCartaoVM.cartaoEmFoco.value?.let {
                appVM.setCartaoEmAC(it)
            } ?: run {
                //Toast.makeText(context, "Não foi possível carregar o cartão para dica.", Toast.LENGTH_SHORT).show()
            }
            intentToListarDicaActivity()
            //Toast.makeText(requireContext(), "Visualizar Cartões", Toast.LENGTH_SHORT).show()
        }
        binding.ListarCartaoFrAcoesCartaoTextViewExcluirCartao.setOnClickListener {
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir esse Cartão??",{
                listarCartaoVM.cartaoEmFoco.value?.let { cartao ->
                    listarCartaoVM.excluirCartao(cartao){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhum Cartão selecionada", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

    private fun intentToListarDicaActivity(){
        val intent = Intent(requireContext(), ListarDicaAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent)
    }
}
