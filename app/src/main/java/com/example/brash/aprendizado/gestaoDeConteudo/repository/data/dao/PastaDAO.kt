package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.PastaModel

@Dao
interface PastaDAO {
    @Query("SELECT COUNT(*) FROM Pasta")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Pasta")
    fun retrieveAll() : List<PastaModel>

    @Query("SELECT * FROM Pasta WHERE id = :id")
    fun retrieveById(id: Long): PastaModel

    @Insert
    fun insert(p: PastaModel): Long

    @Delete
    fun delete(p: PastaModel)

    @Update
    fun update(p: PastaModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */


    @Query("SELECT * FROM Pasta WHERE usuario_id = :usuario")
    fun retrieveByUsuario(usuario: Long): PastaModel

    @Query("SELECT * FROM Pasta WHERE nome LIKE '%' || :nome || '%'")
    fun retrieveByNome(nome: String): List<PastaModel>

}