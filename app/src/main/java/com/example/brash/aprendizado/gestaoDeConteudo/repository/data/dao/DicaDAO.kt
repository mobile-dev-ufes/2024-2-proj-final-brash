package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.DicaModel

@Dao
interface DicaDAO {
    @Query("SELECT COUNT(*) FROM Dica")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Dica")
    fun retrieveAll() : List<DicaModel>

    @Query("SELECT * FROM Dica WHERE id = :id")
    fun retrieveById(id: Long): DicaModel

    @Insert
    fun insert(p: DicaModel): Long

    @Delete
    fun delete(p: DicaModel)

    @Update
    fun update(p: DicaModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Dica WHERE cartao_id = :cartao")
    fun retrieveByCartao(cartao: Long): List<DicaModel>


}