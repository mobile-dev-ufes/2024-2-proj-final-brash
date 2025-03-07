package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.repository.data.model.RelatorioModel
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.AnotacaoModel
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.BaralhoModel
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.CartaoModel
import java.time.LocalDateTime

@Dao
interface DAO {
    @Query("SELECT COUNT(*) FROM Relatorio")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Relatorio")
    fun retrieveAll() : List<RelatorioModel>

    @Query("SELECT * FROM Relatorio WHERE id = :id")
    fun retrieveById(id: Int): RelatorioModel

    @Insert
    fun insert(p: RelatorioModel): Long

    @Delete
    fun delete(p: RelatorioModel)

    @Update
    fun update(p: RelatorioModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Relatorio WHERE usuario_id = :usuario")
    fun retrieveByUsuario(usuario: Long): List<RelatorioModel>

    @Query("SELECT * FROM Relatorio WHERE data = :data ")
    fun retrieveByData(data: LocalDateTime): List<RelatorioModel>

    @Query("SELECT * FROM Relatorio WHERE baralho_id = :baralho")
    fun retrieveByBaralho(baralho: Long): List<RelatorioModel>


}