package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model.BaralhoModel
import java.time.LocalDateTime

@Dao
interface BaralhoDAO {
    @Query("SELECT COUNT(*) FROM Baralho")
    fun retrieveCount() : Long

    @Query("SELECT COUNT(*) FROM Baralho")
    fun retrieveAll() : List<BaralhoModel>

    @Query("SELECT * FROM Baralho WHERE id = :id")
    fun retrieveById(id: Long): BaralhoModel

    @Insert
    fun insert(p: BaralhoModel): Long

    @Delete
    fun delete(p: BaralhoModel)

    @Update
    fun update(p: BaralhoModel): Int

    /*
    ---------------------------
    ------- Especificos -------
    ---------------------------
     */

    @Query("SELECT * FROM Baralho WHERE usuario_id = :usuario")
    fun retrieveByUsuario(usuario: Long): List<BaralhoModel>

    @Query("SELECT * FROM Baralho WHERE pasta_id = :pasta")
    fun retrieveByPasta(pasta: Long): List<BaralhoModel>

    @Query("SELECT * FROM Baralho WHERE nome LIKE '%' || :substring || '%'")
    fun retrieveByNome(substring: String): List<BaralhoModel>

    //TODO:: consertar, fazer obter corretamente a area  e se o baralho é publico ou não
    @Query("SELECT * FROM AreaDoConhecimento WHERE baralho_id = :id AND nome LIKE '%' || :substring || '%'")
    fun retrieveByAreaDoConhecimento(id: Long, substring: String, publico: Boolean): List<BaralhoModel>

    //TODO:: consertar, fazer obter corretamente a area  e se o baralho é publico ou não
    @Query("SELECT * FROM AreaEspecifica WHERE baralho_id = :id AND nome LIKE '%' || :substring || '%'")
    fun retrieveByAreaEspecifica(id: Long, substring: String, publico: Boolean): List<BaralhoModel>

    //@Query("SELECT * FROM Baralho WHERE resposta LIKE '%' || :substring || '%'")
    //fun retrievePublicoByNome(substring: String): List<BaralhoModel>

}