package com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.time.LocalDateTime

@Entity(tableName="Cartao",
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
class CartaoEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "baralho_id")
    var baralhoId: Long = 0

    @ColumnInfo(name = "pergunta")
    var pergunta: String = ""

    @ColumnInfo(name = "resposta")
    var resposta: String = ""

    @ColumnInfo(name = "imagem_path")
    var imagemPath: String? = null

    @ColumnInfo(name = "fator_de_revisao")
    var fatorDeRevisao: Double = 0.0

    @ColumnInfo(name = "data_de_revisao")
    var dataDeRevisao: LocalDateTime? = LocalDateTime.now()

    @ColumnInfo(name = "categoria_aprendizado")
    var categoriaAprendizado: String = ""
}