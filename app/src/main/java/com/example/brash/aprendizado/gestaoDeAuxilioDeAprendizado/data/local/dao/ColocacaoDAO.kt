package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.data.local.dao

//NOT USED YET

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.data.local.entity.ColocacaoEntity
import com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity.BaralhoEntity
import com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity.CartaoEntity
import java.time.LocalDateTime

@Dao
interface ColocacaoDAO {
    @Query("SELECT COUNT(*) FROM Colocacao")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Colocacao")
    fun retrieveAll() : List<ColocacaoEntity>

    @Query("SELECT * FROM Colocacao WHERE id = :id")
    fun retrieveById(id: Int): ColocacaoEntity

    @Insert
    fun insert(p: ColocacaoEntity): Long

    @Delete
    fun delete(p: ColocacaoEntity)

    @Update
    fun update(p: ColocacaoEntity): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Colocacao WHERE usuario_id = :usuario")
    fun retrieveByUsuario(usuario: Long): List<BaralhoEntity>

    @Query("SELECT * FROM Colocacao WHERE data = :data ")
    fun retrieveByData(data: LocalDateTime): List<CartaoEntity>


}