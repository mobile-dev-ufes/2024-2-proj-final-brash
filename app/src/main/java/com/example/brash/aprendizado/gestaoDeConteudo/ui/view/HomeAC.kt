package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.databinding.GtcHomeAcBinding
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.nucleo.ui.view.ConfiguracaoAC
import com.example.brash.nucleo.ui.view.PerfilAC

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesAdicionaisFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesBaralhoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesPastaFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaExpandableAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.utils.showCartoesAllInfoDialog
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr
import com.example.brash.nucleo.utils.getSavedLanguage
import com.example.brash.nucleo.utils.setAppLocale
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

/**
 * Activity representing the home screen of the application, displaying a list of items
 * and providing navigation options for profile and settings.
 *
 * This activity fetches and displays a list of items (e.g., decks and folders) in an expandable
 * list. It also allows the user to interact with the list, view additional options for items,
 * and navigate to profile and configuration screens.
 *
 * @constructor Creates an instance of [HomeAC].
 */
class HomeAC : AppCompatActivity(), AlertDialogFr.OnConfirmListener {

    private lateinit var binding: GtcHomeAcBinding
    private lateinit var homeVM: HomeVM

    private lateinit var appVM: AppVM

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListaExpandableAdapter


    /**
     * Called when the activity is first created.
     *
     * Initializes the layout, ViewModel, RecyclerView, and sets up observers for LiveData.
     * It also configures language settings based on the saved language.
     *
     * @param savedInstanceState A [Bundle] containing the activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = getSavedLanguage(this)
        setAppLocale(this, language)

        binding = GtcHomeAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeVM = ViewModelProvider(this)[HomeVM::class.java]

        appVM = (application as MyApplication).appSharedInformation

        initResultadoBusca()
        setOnClickListeners()
        setObservers()

        appVM.requestUsuarioLogado()
    }

    /**
     * Sets the click listeners for the activity's UI elements.
     *
     * This includes handling clicks for the additional actions button, profile icon,
     * and settings icon.
     */
    private fun setOnClickListeners(){
        binding.HomeAcButtonAcoesAdicionais.setOnClickListener{
            AcoesAdicionaisFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
        }
        binding.HomeAcShapeableImageViewIconePerfil.setOnClickListener{
            intentToPerfilActivity()
        }
        binding.HomeAcImageViewConfiguracoes.setOnClickListener{
            intentToConfiguracaoActivity()
        }
        binding.HomeAcMaisInformacoes.setOnClickListener{
            showCartoesAllInfoDialog(this)
        }
    }
    /**
     * Sets up the observers for LiveData from the ViewModel.
     *
     * Observes changes in the list of home items and updates the UI accordingly.
     * Also updates the profile icon when the logged-in user data changes.
     */
    private fun setObservers(){
        homeVM.homeAcListItemList.observe(this, Observer{
            adapter.updateProdList(homeVM.homeAcListItemList.value!!)
        })

        appVM.usuarioLogado.observe(this, Observer{
            binding.HomeAcShapeableImageViewIconePerfil.setImageResource(it.iconeDeUsuario.imagem.drawableRes)
            binding.HomeAcShapeableImageViewIconePerfil.setBackgroundResource(it.iconeDeUsuario.cor.colorRes)
        })

        appVM.updateHomeAC.observe(this, Observer{
            homeVM.getAllHomeAcListItem()
        })
        /*
        perfilVM.imagemEmFoco.observe(this, Observer { imagem ->
            imagem?.let { binding.EditarPerfilAcShapeableImageViewIconePerfil.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor1.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor2.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor3.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor4.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor5.setImageResource(it.drawableRes) }
        })
         */
    }
    /**
     * Initializes the search result display by setting up the RecyclerView and adapter.
     * It also fetches the list of home items.
     */
    private fun initResultadoBusca(){
        recyclerView = findViewById(R.id.HomeAcExpandableListViewResultadoBusca)
        recyclerView.layoutManager = LinearLayoutManager(this)


        homeVM.getAllHomeAcListItem()

        adapter = ListaExpandableAdapter(homeVM.homeAcListItemList.value ?: run {
            // Caso homeVM.homeAcListItemList.value seja nulo, você pode tratar aqui ou atribuir um valor padrão
            emptyList() // Exemplo: Retorna uma lista vazia no caso de nulo
        }, onPastaItemLongClick = { item ->
            //Toast.makeText(this, "Clicou na pasta: ${item.pasta.nome}", Toast.LENGTH_SHORT).show()
            homeVM.setPastaEmFoco(item.pasta)
            AcoesPastaFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
        }, onBaralhoItemClick = { item ->
            //Toast.makeText(this, "Clicou no baralho: ${item.baralho.nome}", Toast.LENGTH_SHORT).show()
            homeVM.setBaralhoEmFoco(item.baralho)
            AcoesBaralhoFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
        })

        recyclerView.adapter = adapter
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
    /**
     * Navigates to the Configuration screen.
     */
    private fun intentToConfiguracaoActivity(){
        val intent = Intent(this, ConfiguracaoAC::class.java)
        startActivity(intent)
        // vai ter que botar finish
    }
    /**
     * Navigates to the Profile screen.
     */
    private fun intentToPerfilActivity(){
        val intent = Intent(this, PerfilAC::class.java)
        startActivity(intent)
    }

    /**
     * Called when the confirmation button is clicked on the alert dialog.
     *
     * This method implements [AlertDialogFr.OnConfirmListener] to handle confirmation actions.
     */
    override fun onConfirmAlertDialog() {
        // Aqui você pode navegar para outra Activity ou realizar alguma ação
        //Toast.makeText(this, "Confirmado Exclusao!", Toast.LENGTH_SHORT).show()
    }
}