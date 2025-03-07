package com.example.brash.nucleo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.nucleo.data.local.entity.IconeDeUsuarioEntity

@Dao
interface IconeDeUsuarioDAO {
    @Query("SELECT COUNT(*) FROM IconeDeUsuario")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM IconeDeUsuario")
    fun retrieveAll() : List<IconeDeUsuarioEntity>

    @Query("SELECT * FROM IconeDeUsuario WHERE id = :id")
    fun retrieveById(id: Long): IconeDeUsuarioEntity

    @Insert
    fun insert(p: IconeDeUsuarioEntity): Long

    @Delete
    fun delete(p: IconeDeUsuarioEntity)

    @Update
    fun update(p: IconeDeUsuarioEntity): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM IconeDeUsuario WHERE usuario_id = :usuario")
    fun retrieveByUsuario(usuario: Long): IconeDeUsuarioEntity


}