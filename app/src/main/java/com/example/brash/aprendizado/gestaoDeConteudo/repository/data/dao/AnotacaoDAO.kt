package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.AnotacaoModel

@Dao
interface AnotacaoDAO {
    @Query("SELECT COUNT(*) FROM Anotacao")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Anotacao")
    fun retrieveAll() : List<AnotacaoModel>

    @Query("SELECT * FROM Anotacao WHERE id = :id")
    fun retrieveById(id: Long): AnotacaoModel

    @Insert
    fun insert(p: AnotacaoModel): Long

    @Delete
    fun delete(p: AnotacaoModel)

    @Update
    fun update(p: AnotacaoModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Anotacao WHERE baralho_id = :baralho")
    fun retrieveByBaralho(baralho: Long): List<AnotacaoModel>

    @Query("SELECT * FROM Anotacao WHERE nome LIKE '%' || :nome || '%'")
    fun retrieveByNome(nome: String): List<AnotacaoModel>


}