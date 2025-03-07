package com.example.brash.nucleo.repository.data.dao

class UsuarioDAO {
}

/*import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.app02.repository.data.model.Usuario

@Dao
interface FraseDAO {

    @Insert
    fun insert(p: FraseModel): Long

    @Update
    fun update(p: FraseModel): Int

    @Delete
    fun delete(p: FraseModel)

    @Query("SELECT * FROM Frases WHERE id = :id")
    fun getById(id: Int): FraseModel

    @Query("SELECT * FROM Frases WHERE coluna_tipo = :colunaDiscriminatoria ORDER BY RANDOM() LIMIT 1")
    fun getRandomByTipo(colunaDiscriminatoria: String): FraseModel

    @Query("DELETE FROM Frases")
    fun deleteAll()  // Apaga todos os registros da tabela Frases
}*/