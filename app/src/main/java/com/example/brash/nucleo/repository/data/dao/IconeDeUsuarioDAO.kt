package com.example.brash.nucleo.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.nucleo.repository.data.model.IconeDeUsuarioModel
import com.example.brash.nucleo.repository.data.model.Usuario
import com.example.brash.nucleo.repository.data.model.UsuarioModel

@Dao
interface IconeDeUsuarioDAO {
    @Query("SELECT COUNT(*) FROM IconeDeUsuario")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM IconeDeUsuario")
    fun retrieveAll() : List<IconeDeUsuarioModel>

    @Query("SELECT * FROM IconeDeUsuario WHERE id = :id")
    fun retrieveById(id: Long): IconeDeUsuarioModel

    @Insert
    fun insert(p: IconeDeUsuarioModel): Long

    @Delete
    fun delete(p: IconeDeUsuarioModel)

    @Update
    fun update(p: IconeDeUsuarioModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM IconeDeUsuario WHERE usuario_id = :usuario")
    fun retrieveByUsuario(usuario: Long): IconeDeUsuarioModel


}