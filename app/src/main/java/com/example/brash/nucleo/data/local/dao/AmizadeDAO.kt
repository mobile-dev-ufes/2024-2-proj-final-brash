package com.example.brash.nucleo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.nucleo.data.local.entity.AmizadeEntity

@Dao
interface AmizadeDAO {
    @Query("SELECT COUNT(*) FROM Amizade")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Amizade")
    fun retrieveAll() : List<AmizadeEntity>

    //@Query("SELECT * FROM Amizade WHERE id = :id")
    //fun retrieveById(id: Int): AmizadeModel

    @Insert
    fun insert(p: AmizadeEntity): Long

    @Delete
    fun delete(p: AmizadeEntity)

    @Update
    fun update(p: AmizadeEntity): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Amizade WHERE usuario_id_requerido = :usuario AND efetivada = 1")
    fun retrieveNaoConfirmadosByUsuario(usuario: Long): List<AmizadeEntity>

    @Query("SELECT * FROM Amizade WHERE (usuario_id_requerente = :usuario1 AND usuario_id_requerido = :usuario2) OR (usuario_id_requerido = :usuario1 AND usuario_id_requerente = :usuario2)")
    fun retrieveByUsuarios(usuario1: Long, usuario2: Long): AmizadeEntity?

}