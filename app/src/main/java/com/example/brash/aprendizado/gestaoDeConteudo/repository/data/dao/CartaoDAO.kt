package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.CartaoModel
import java.time.LocalDateTime

@Dao
interface CartaoDAO {
    @Query("SELECT COUNT(*) FROM Cartao")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Cartao")
    fun retrieveAll() : List<CartaoModel>

    @Query("SELECT * FROM Cartao WHERE id = :id")
    fun retrieveById(id: Long): CartaoModel

    @Insert
    fun insert(p: CartaoModel): Long

    @Delete
    fun delete(p: CartaoModel)

    @Update
    fun update(p: CartaoModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Cartao WHERE baralho_id = :baralho")
    fun retrieveByBaralho(baralho: Long): List<CartaoModel>

    @Query("SELECT * FROM Cartao WHERE data_de_revisao = :data ")
    fun retrieveByDataDeRevisao(data: LocalDateTime): List<CartaoModel>

    @Query("SELECT * FROM Cartao WHERE pergunta LIKE '%' || :substring || '%'")
    fun retrieveByPergunta(substring: String): List<CartaoModel>

    @Query("SELECT * FROM Cartao WHERE resposta LIKE '%' || :substring || '%'")
    fun retrieveByResposta(substring: String): List<CartaoModel>

}