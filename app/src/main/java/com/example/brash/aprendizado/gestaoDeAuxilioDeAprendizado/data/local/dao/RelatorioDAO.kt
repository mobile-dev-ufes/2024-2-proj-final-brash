package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.data.local.dao

//NOT USED YET

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.data.local.entity.RelatorioEntity
import java.time.LocalDateTime

@Dao
interface RelatorioDAO {
    @Query("SELECT COUNT(*) FROM Relatorio")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Relatorio")
    fun retrieveAll() : List<RelatorioEntity>

    @Query("SELECT * FROM Relatorio WHERE id = :id")
    fun retrieveById(id: Int): RelatorioEntity

    @Insert
    fun insert(p: RelatorioEntity): Long

    @Delete
    fun delete(p: RelatorioEntity)

    @Update
    fun update(p: RelatorioEntity): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Relatorio WHERE usuario_id = :usuario")
    fun retrieveByUsuario(usuario: Long): List<RelatorioEntity>

    @Query("SELECT * FROM Relatorio WHERE data = :data ")
    fun retrieveByData(data: LocalDateTime): List<RelatorioEntity>

    @Query("SELECT * FROM Relatorio WHERE baralho_id = :baralho")
    fun retrieveByBaralho(baralho: Long): List<RelatorioEntity>


}