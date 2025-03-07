package com.example.brash.aprendizado.gestaoDeConteudo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity.PastaEntity

@Dao
interface PastaDAO {
    @Query("SELECT COUNT(*) FROM Pasta")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Pasta")
    fun retrieveAll() : List<PastaEntity>

    @Query("SELECT * FROM Pasta WHERE id = :id")
    fun retrieveById(id: Long): PastaEntity

    @Insert
    fun insert(p: PastaEntity): Long

    @Delete
    fun delete(p: PastaEntity)

    @Update
    fun update(p: PastaEntity): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */


    @Query("SELECT * FROM Pasta WHERE usuario_id = :usuario")
    fun retrieveByUsuario(usuario: Long): PastaEntity

    @Query("SELECT * FROM Pasta WHERE nome LIKE '%' || :nome || '%'")
    fun retrieveByNome(nome: String): List<PastaEntity>

}