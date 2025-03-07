package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model

import com.example.brash.nucleo.repository.data.model.UsuarioModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.time.LocalDateTime


@Entity(tableName="Pasta",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioModel::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
        ),
    ])
class PastaModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "usuario_id")
    var usuarioId: Long = 0

    @ColumnInfo(name = "nome")
    var nome: String = ""

}