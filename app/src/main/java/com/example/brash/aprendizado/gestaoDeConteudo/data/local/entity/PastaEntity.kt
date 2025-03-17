package com.example.brash.aprendizado.gestaoDeConteudo.data.local.entity

import com.example.brash.nucleo.data.local.entity.UsuarioEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

//NOT USED YET

@Entity(tableName="Pasta",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
        ),
    ])
class PastaEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "usuario_id")
    var usuarioId: Long = 0

    @ColumnInfo(name = "nome")
    var nome: String = ""

}