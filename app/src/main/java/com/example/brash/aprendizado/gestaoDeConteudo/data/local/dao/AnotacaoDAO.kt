package com.example.brash.aprendizado.gestaoDeConteudo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity.AnotacaoEntity

//NOT USED YET

@Dao
interface AnotacaoDAO {
    @Query("SELECT COUNT(*) FROM Anotacao")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Anotacao")
    fun retrieveAll() : List<AnotacaoEntity>

    @Query("SELECT * FROM Anotacao WHERE id = :id")
    fun retrieveById(id: Long): AnotacaoEntity

    @Insert
    fun insert(p: AnotacaoEntity): Long

    @Delete
    fun delete(p: AnotacaoEntity)

    @Update
    fun update(p: AnotacaoEntity): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Anotacao WHERE baralho_id = :baralho")
    fun retrieveByBaralho(baralho: Long): List<AnotacaoEntity>

    @Query("SELECT * FROM Anotacao WHERE nome LIKE '%' || :nome || '%'")
    fun retrieveByNome(nome: String): List<AnotacaoEntity>


}