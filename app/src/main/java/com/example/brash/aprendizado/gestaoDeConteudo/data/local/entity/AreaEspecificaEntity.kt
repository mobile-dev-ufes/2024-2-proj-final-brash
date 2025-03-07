package com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName="AreaEspecifica",
    foreignKeys = [
        ForeignKey(
            entity = BaralhoEntity::class,
            parentColumns = ["id"],
            childColumns = ["baralho_id"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
class AreaEspecificaEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "baralho_id")
    var baralhoId: Long = 0

    @ColumnInfo(name = "nome")
    var nome: String = ""
}