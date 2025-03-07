package com.example.brash.nucleo.repository.data.dao



//import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.nucleo.repository.data.model.Usuario
import com.example.brash.nucleo.repository.data.model.UsuarioModel

@Dao
interface UsuarioDAO {
    @Query("SELECT COUNT(*) FROM Usuario")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Usuario")
    fun retrieveAll() : List<UsuarioModel>

    @Query("SELECT * FROM Usuario WHERE id = :id")
    fun retrieveById(id: Long): UsuarioModel

    @Insert
    fun insert(p: UsuarioModel): Long

    @Delete
    fun delete(p: UsuarioModel)

    @Update
    fun update(p: UsuarioModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Usuario WHERE nome_de_exibicao LIKE '%' || :substring || '%'")
    fun retrieveByNomeDeExibicao(substring: String): List<UsuarioModel>

    @Query("SELECT * FROM Usuario WHERE nome_de_usuario LIKE '%' || :substring || '%'")
    fun retrieveByNomeDeUsuario(substring: String): List<UsuarioModel>

    @Query("SELECT * FROM Usuario WHERE email = :email")
    fun retrieveByEmail(email: String): UsuarioModel

    @Query("SELECT * FROM Usuario WHERE codigo_aleatorio = :codigo")
    fun retrieveByCodigoAleatorio(codigo: String): UsuarioModel

    //@Query("")
    //fun retrieveAmigosByUsuario(usuario: UsuarioModel): List<UsuarioModel>

    @Query("SELECT * FROM Usuario WHERE nome_de_exibicao LIKE '%' || :substring || '%' OR nome_de_usuario LIKE '%' || :substring || '%'")
    fun retrieveByNomes(substring: String): UsuarioModel


}