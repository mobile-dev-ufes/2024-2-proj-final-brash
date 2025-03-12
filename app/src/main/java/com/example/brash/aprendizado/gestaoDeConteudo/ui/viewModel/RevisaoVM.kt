package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.OpcoesDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.utils.OrdemDeBuscaHome
import java.lang.Exception

class RevisaoVM(application: Application) : AndroidViewModel(application) {


    private var _baralhoEmFoco = MutableLiveData<Baralho>()

    val baralhoEmFoco get() = _baralhoEmFoco


    fun setBaralhoEmFoco(baralho: Baralho){
        baralhoEmFoco.value = baralho
        Log.d("RevisaoAC", "Defini Baralho em FOCO")
    }
    fun resetBaralhoEmFoco(){
        baralhoEmFoco.value = null
        Log.d("RevisaoAC", "Resetei Baralho em FOCO")
    }

}


/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */