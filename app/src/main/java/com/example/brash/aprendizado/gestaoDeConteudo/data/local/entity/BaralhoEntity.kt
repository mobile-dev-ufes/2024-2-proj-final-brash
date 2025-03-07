package com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity

import com.example.brash.nucleo.data.local.entity.UsuarioEntity



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName="Baralho",foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PastaEntity::class,
            parentColumns = ["id"],
            childColumns = ["pasta_id"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class BaralhoEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "usuario_id")
    var usuarioId: Long? = 0

    @ColumnInfo(name = "pasta_id")
    var pastaId: Long? = 0

    @ColumnInfo(name = "nome")
    var nome: String = ""

    @ColumnInfo(name = "descricao")
    var descricao: String = ""

    @ColumnInfo(name = "publico")
    var publico: Boolean = false

    @ColumnInfo(name = "cartoes_novos_por_dia")
    var cartoesNovosPorDia: Int = 20
}