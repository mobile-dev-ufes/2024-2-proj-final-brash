package com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

//NOT USED YET

@Entity(tableName="Dica",
    foreignKeys = [
        ForeignKey(
            entity = CartaoEntity::class,
            parentColumns = ["id"],
            childColumns = ["cartao_id"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
class DicaEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "cartao_id")
    var cartaoId: Long = 0

    @ColumnInfo(name = "texto")
    var texto: String = ""
}