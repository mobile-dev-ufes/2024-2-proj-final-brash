package com.example.brash.nucleo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey


@Entity(tableName="IconeDeUsuario",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        ),
    ])
class IconeDeUsuarioEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name = "usuario_id")
    var usuarioId: Long = 0

    @ColumnInfo(name = "cor")
    var cor: String = ""

    @ColumnInfo(name = "cor")
    var imagemPath: String = ""


}