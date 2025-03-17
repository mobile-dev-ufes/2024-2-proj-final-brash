package com.example.brash.aprendizado.gestaoDeConteudo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity.DicaEntity

//NOT USED YET

@Dao
interface DicaDAO {
    @Query("SELECT COUNT(*) FROM Dica")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Dica")
    fun retrieveAll() : List<DicaEntity>

    @Query("SELECT * FROM Dica WHERE id = :id")
    fun retrieveById(id: Long): DicaEntity

    @Insert
    fun insert(p: DicaEntity): Long

    @Delete
    fun delete(p: DicaEntity)

    @Update
    fun update(p: DicaEntity): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Dica WHERE cartao_id = :cartao")
    fun retrieveByCartao(cartao: Long): List<DicaEntity>


}