package com.example.brash.nucleo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName="Amizade",
    primaryKeys = ["usuario_id_requerente", "usuario_id_requerido"], // Chave primária composta
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id_requerente"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id_requerido"],
            // Exclui entidades da tabela que possuem essa foreign key quando a entidade da foreign key for removida
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class AmizadeEntity {
    @ColumnInfo(name = "usuario_id_requerente")
    var usuarioIdRequerente: Long = 0

    @ColumnInfo(name = "usuario_id_requerido")
    var usuarioIdRequerido: Long = 0

    @ColumnInfo(name = "efetivada")
    var efetivada: Boolean = false
}


/*

@Entity(tableName="Usuario")
class UsuarioModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idUsuario")
    var idUsuario: Long = 0

}

 */