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

    // Binding to access the layout views using ViewBinding
    private var _binding: GtcHomeFrAcoesPastaBinding? = null
    private val binding get() = _binding!!

    // ViewModel to manage data related to the folder actions
    lateinit var homeVM: HomeVM

    /**
     * Inflates the layout of the fragment using ViewBinding and returns the root view.
     *
     * @param inflater The LayoutInflater used to inflate the layout.
     * @param container The container to attach the view to.
     * @param savedInstanceState The saved state of the fragment.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        _binding = GtcHomeFrAcoesPastaBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called after the view has been created. Initializes the ViewModel and sets the click listeners for buttons.
     *
     * @param view The root view of the fragment's layout.
     * @param savedInstanceState The saved instance state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel correctly
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]

        setOnClickListeners()
    }

    /**
     * Placeholder method for setting up observers for the ViewModel (not used in this case).
     */
    private fun setObservers(){

    }

    /**
     * Sets up the click listeners for the folder actions: rename and delete.
     * The delete option shows a confirmation dialog before proceeding.
     */
    private fun setOnClickListeners(){

        // When the "Rename Folder" option is clicked, show the Rename Folder dialog
        binding.HomeFrAcoesPastaTextViewRenomearPasta.setOnClickListener {
            dismiss() // Dismiss the current dialog
            // Show the Rename Folder dialog
            RenomearPastaFrDialog().show(parentFragmentManager, "RenomearPastaDialog")
        }

        // When the "Delete Folder" option is clicked, show a confirmation dialog
        binding.HomeFrAcoesPastaTextViewExcluirPasta.setOnClickListener {
            // Show a confirmation dialog before deleting the folder
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir essa Pasta?? Essa ação irá excluir TODOS os baralhos da pasta",{

                // If a folder is selected, delete it and all decks within it
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

    /**
     * Cleans up the binding to avoid memory leaks when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}