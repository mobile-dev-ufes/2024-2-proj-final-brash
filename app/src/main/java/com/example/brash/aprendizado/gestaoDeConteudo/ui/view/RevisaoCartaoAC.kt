package com.example.brash.aprendizado.gestaoDeConteudo.ui.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoCartaoVM
import com.example.brash.databinding.GtcRevisaoCartaoAcBinding
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication


/**
 * Activity that handles the review of flashcards in the application.
 *
 * This activity is responsible for displaying a specific flashcard review interface.
 * It sets up the ViewModel and observers for managing the data flow between the UI and
 * the business logic. The activity also manages the click interactions and handles
 * the lifecycle of the review process.
 *
 * @constructor Creates an instance of [RevisaoCartaoAC].
 */
class RevisaoCartaoAC : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: GtcRevisaoCartaoAcBinding

    private lateinit var revisaoCartaoVM: RevisaoCartaoVM
    private lateinit var appVM: AppVM


    /**
     * Called when the activity is first created.
     *
     * Initializes the layout and binds the ViewModel. Sets up the ViewModel with the deck
     * for review and prepares the click listeners for the UI elements.
     *
     * @param savedInstanceState A [Bundle] containing the activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcRevisaoCartaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        revisaoCartaoVM = ViewModelProvider(this)[RevisaoCartaoVM::class.java]
        appVM = (application as MyApplication).appSharedInformation

        appVM.baralhoEmAC.value?.let {
            revisaoCartaoVM.setBaralhoOwner(it)
        } ?: run {
            //Toast.makeText(applicationContext, "Erro: Baralho não encontrado para revisão.", Toast.LENGTH_SHORT).show()
        }

        setOnClickListeners()
        setObservers()
    }

    /**
     * Sets the click listeners for the UI elements.
     *
     * Configures the actions when the user interacts with certain views in the activity.
     * For now, it binds the action to the "back" button to finish the activity.
     */
    private fun setOnClickListeners(){
        binding.RevisaoAcImageViewRetornar.setOnClickListener {
            finish()
        }
    }

    /**
     * Sets up the observers for the LiveData or StateFlow variables in the ViewModel.
     *
     * This function initializes the observers that will react to changes in data
     * during the lifecycle of the activity. This method is intended to be filled in
     * with the observers that react to data changes in the [revisaoCartaoVM].
     */
    private fun setObservers(){

    }

    /**
     * Handles the click events on the views.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view : View) {
    }

    /**
     * Called when the activity is stopped. Can be used to release resources or save data.
     *
     * This method is invoked when the activity is no longer in the foreground. You can use this method
     * to stop ongoing processes or save any data that needs to persist.
     */
    override fun onStop() {
        super.onStop()

    }

}