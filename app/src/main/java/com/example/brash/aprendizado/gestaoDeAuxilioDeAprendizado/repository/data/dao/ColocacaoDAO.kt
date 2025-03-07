package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.repository.data.model.ColocacaoModel
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.BaralhoModel
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.CartaoModel
import java.time.LocalDateTime

@Dao
interface ColocacaoDAO {
    @Query("SELECT COUNT(*) FROM Colocacao")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Colocacao")
    fun retrieveAll() : List<ColocacaoModel>

    @Query("SELECT * FROM Colocacao WHERE id = :id")
    fun retrieveById(id: Int): ColocacaoModel

    @Insert
    fun insert(p: ColocacaoModel): Long

    @Delete
    fun delete(p: ColocacaoModel)

    @Update
    fun update(p: ColocacaoModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Colocacao WHERE usuario_id = :usuario")
    fun retrieveByUsuario(usuario: Long): List<BaralhoModel>

    @Query("SELECT * FROM Colocacao WHERE data = :data ")
    fun retrieveByData(data: LocalDateTime): List<CartaoModel>


}