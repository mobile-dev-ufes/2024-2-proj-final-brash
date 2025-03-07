package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.repository.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.example.brash.nucleo.repository.data.model.UsuarioModel
import java.time.LocalDateTime

@Entity(tableName="Colocacao",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioModel::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
class ColocacaoModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "usuario_id")
    var usuarioId: Long = 0

    @ColumnInfo(name = "data")
    var data: LocalDateTime = LocalDateTime.now()

    @ColumnInfo(name = "posicao")
    var posicao: Int = 0

    @ColumnInfo(name = "pontuacao")
    var pontuacao: Int = 0
}