package com.example.brash.nucleo.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.nucleo.repository.data.model.AmizadeModel
import com.example.brash.room.ConstantsBD

@Dao
interface AmizadeDAO {
    @Query("SELECT COUNT(*) FROM Amizade")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Amizade")
    fun retrieveAll() : List<AmizadeModel>

    //@Query("SELECT * FROM Amizade WHERE id = :id")
    //fun retrieveById(id: Int): AmizadeModel

    @Insert
    fun insert(p: AmizadeModel): Long

    @Delete
    fun delete(p: AmizadeModel)

    @Update
    fun update(p: AmizadeModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Amizade WHERE usuario_id_requerido = :usuario AND efetivada = 1")
    fun retrieveNaoConfirmadosByUsuario(usuario: Long): List<AmizadeModel>

    @Query("SELECT * FROM Amizade WHERE (usuario_id_requerente = :usuario1 AND usuario_id_requerido = :usuario2) OR (usuario_id_requerido = :usuario1 AND usuario_id_requerente = :usuario2)")
    fun retrieveByUsuarios(usuario1: Long, usuario2: Long): AmizadeModel?

}