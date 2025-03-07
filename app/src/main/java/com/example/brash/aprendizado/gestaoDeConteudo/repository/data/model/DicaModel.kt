package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName="Dica",
    foreignKeys = [
        ForeignKey(
            entity = CartaoModel::class,
            parentColumns = ["id"],
            childColumns = ["cartao_id"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
class DicaModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "cartao_id")
    var cartaoId: Long = 0

    @ColumnInfo(name = "texto")
    var texto: String = ""
}