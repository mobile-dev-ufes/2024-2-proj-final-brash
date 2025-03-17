package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity.BaralhoEntity
import com.example.brash.nucleo.data.local.entity.UsuarioEntity
import java.time.LocalDateTime

//NOT USED YET

@Entity(tableName="Relatorio",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BaralhoEntity::class,
            parentColumns = ["id"],
            childColumns = ["baralho_id"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
class RelatorioEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "numero_de_cartoes_novos")
    var numeroDeCartoesNovos: Int = 0

    @ColumnInfo(name = "numero_de_cartoesRecentes")
    var numeroDeCartoesRecentes: Int = 0

    @ColumnInfo(name = "numero_de_cartoes_maduros")
    var numeroDeCartoesMaduros: Int = 0

    @ColumnInfo(name = "numero_de_acertos_de_cartoes_maduros")
    var numeroDeAcertosDeCartoesMaduros: Int = 0

    @ColumnInfo(name = "numero_de_cartoes_aprendendo")
    var numeroDeCartoesAprendendo: Int = 0

    @ColumnInfo(name = "numero_de_reaprendendo")
    var numeroDeCartoesReaprendendo: Int = 0

    @ColumnInfo(name = "data")
    var data: LocalDateTime = LocalDateTime.now()

    @ColumnInfo(name = "minutos_revisados")
    var minutosRevisados: Double = 0.0

    @ColumnInfo(name = "data")
    var colunaDiscretizadora: String = ""

    @ColumnInfo(name = "usuario_id")
    var usuarioId: Long? = 0

    @ColumnInfo(name = "baralho_id")
    var baralhoId: Long? = 0
}