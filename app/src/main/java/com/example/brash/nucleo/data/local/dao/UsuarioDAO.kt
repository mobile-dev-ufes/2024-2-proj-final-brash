package com.example.brash.nucleo.data.local.dao



//import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.nucleo.data.local.entity.UsuarioEntity

@Dao
interface UsuarioDAO {
    @Query("SELECT COUNT(*) FROM Usuario")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Usuario")
    fun retrieveAll() : List<UsuarioEntity>

    @Query("SELECT * FROM Usuario WHERE id = :id")
    fun retrieveById(id: Long): UsuarioEntity

    @Insert
    fun insert(p: UsuarioEntity): Long

    @Delete
    fun delete(p: UsuarioEntity)

    @Update
    fun update(p: UsuarioEntity): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Usuario WHERE nome_de_exibicao LIKE '%' || :substring || '%'")
    fun retrieveByNomeDeExibicao(substring: String): List<UsuarioEntity>

    @Query("SELECT * FROM Usuario WHERE nome_de_usuario LIKE '%' || :substring || '%'")
    fun retrieveByNomeDeUsuario(substring: String): List<UsuarioEntity>

    @Query("SELECT * FROM Usuario WHERE email = :email")
    fun retrieveByEmail(email: String): UsuarioEntity

    @Query("SELECT * FROM Usuario WHERE codigo_aleatorio = :codigo")
    fun retrieveByCodigoAleatorio(codigo: String): UsuarioEntity

    //@Query("")
    //fun retrieveAmigosByUsuario(usuario: UsuarioModel): List<UsuarioModel>

    @Query("SELECT * FROM Usuario WHERE nome_de_exibicao LIKE '%' || :substring || '%' OR nome_de_usuario LIKE '%' || :substring || '%'")
    fun retrieveByNomes(substring: String): UsuarioEntity


}