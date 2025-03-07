package com.example.brash.aprendizado.gestaoDeConteudo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity.CartaoEntity
import java.time.LocalDateTime

@Dao
interface CartaoDAO {
    @Query("SELECT COUNT(*) FROM Cartao")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Cartao")
    fun retrieveAll() : List<CartaoEntity>

    @Query("SELECT * FROM Cartao WHERE id = :id")
    fun retrieveById(id: Long): CartaoEntity

    @Insert
    fun insert(p: CartaoEntity): Long

    @Delete
    fun delete(p: CartaoEntity)

    @Update
    fun update(p: CartaoEntity): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Cartao WHERE baralho_id = :baralho")
    fun retrieveByBaralho(baralho: Long): List<CartaoEntity>

    @Query("SELECT * FROM Cartao WHERE data_de_revisao = :data ")
    fun retrieveByDataDeRevisao(data: LocalDateTime): List<CartaoEntity>

    @Query("SELECT * FROM Cartao WHERE pergunta LIKE '%' || :substring || '%'")
    fun retrieveByPergunta(substring: String): List<CartaoEntity>

    @Query("SELECT * FROM Cartao WHERE resposta LIKE '%' || :substring || '%'")
    fun retrieveByResposta(substring: String): List<CartaoEntity>

}