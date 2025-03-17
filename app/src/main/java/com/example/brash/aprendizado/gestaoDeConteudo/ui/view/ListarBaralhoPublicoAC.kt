package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.BaralhoPublico
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.VisualizarBaralhoPublicoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaBaralhoPublicoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarBaralhoPublicoVM
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding

/**
 * Activity for listing public decks and allowing interaction with them.
 *
 * This activity displays a list of public decks that the user can interact with, including
 * the option to view more details of a specific deck. It also provides search functionality
 * to filter the list of public decks based on user input. The activity uses a ViewModel to
 * manage data and LiveData for updating the UI.
 *
 * @constructor Creates an instance of [ListarBaralhoPublicoAC].
 */
class ListarBaralhoPublicoAC : AppCompatActivity() {

    private lateinit var binding: GtcListarBaralhoPublicoAcBinding
    private lateinit var listarBaralhoPublicoVM: ListarBaralhoPublicoVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaBaralhoPublicoAdapter

    /**
     * Called when the activity is first created.
     *
     * Initializes the layout, ViewModel, RecyclerView for displaying the public decks,
     * and sets up the listener for interactions with the public decks. It also fetches
     * the list of public decks to display.
     *
     * @param savedInstanceState A [Bundle] containing the activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarBaralhoPublicoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listarBaralhoPublicoVM = ViewModelProvider(this)[ListarBaralhoPublicoVM::class.java]


        // Inicializando o listener diretamente
        val listener = object : OnBaralhoPublicoListener {
            override fun onClick(b: BaralhoPublico) {
                //Toast.makeText(applicationContext, b.nome, Toast.LENGTH_SHORT).show()
                listarBaralhoPublicoVM.setBaralhoPublicoEmFoco(b)
                VisualizarBaralhoPublicoFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
            }
        }

        // Inicializando o adapter com o listener
        adapter = ListaBaralhoPublicoAdapter().apply {
            setListener(listener) // Garanta que o listener seja configurado
        }

        recyclerView = binding.ListarBaralhoPublicoAcRecycleViewResultadoBusca
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView

        setOnClickListeners()
        setObservers()

        listarBaralhoPublicoVM.getAllBaralhosPublicos()


    }

    /**
     * Sets click listeners for the activity's UI elements.
     *
     * Binds actions to buttons and UI elements, such as the search input and the back button.
     */
    private fun setOnClickListeners(){
        binding.ListarBaralhoPublicoAcInputDePesquisa.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                // O usuário pressionou "Done"
                listarBaralhoPublicoVM.updateFilterBaralhoPublicoList(binding.ListarBaralhoPublicoAcInputDePesquisa.text.toString())

                true // Retorna true para indicar que o evento foi tratado
            } else {
                false // Permite que o evento continue propagando
            }
        }
        binding.ListarBaralhoPublicoAcImageViewRetornar.setOnClickListener {
            finish()
        }
    }

    /**
     * Sets up the observers for LiveData from the ViewModel.
     *
     * Observes changes in the list of public decks and updates the RecyclerView adapter
     * accordingly with the sorted list of public decks.
     */
    private fun setObservers(){

        listarBaralhoPublicoVM.baralhoPublicoListSort.observe(this, Observer { baralhoListSort ->
            //if (baralhoListSort != null && baralhoListSort.isNotEmpty()) {
                adapter.updateBaralhoPublicoList(baralhoListSort)
                //Log.d("ListaBaralhoPublico", "Lista atualizada com sucesso.")
            //} else {
                //Log.d("ListaBaralhoPublico", "A lista de baralhos públicos está vazia.")
            //}
        })
    }
    private fun intentToCadastrarContaActivity(){
        //val intent = Intent(this, CadastrarContaAC::class.java)
        //startActivity(intent)
    }

    /**
     * Called when the activity is stopped.
     *
     * This method is invoked when the activity is no longer in the foreground, allowing for any
     * necessary cleanup when the activity is stopped.
     */
    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    //private fun intentToPerfilActivity(){
        //val intent = Intent(this, PerfilAC::class.java)
        //startActivity(intent)
    //}

}