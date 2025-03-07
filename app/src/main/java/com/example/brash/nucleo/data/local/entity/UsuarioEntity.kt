package com.example.brash.nucleo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName="Usuario")
class UsuarioEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name="nome_de_exibicao")
    val nomeDeExibicao: String = ""

    @ColumnInfo(name="nome_de_usuario")
    val nomeDeUsuario: String = ""

    @ColumnInfo(name="senha")
    val senha: String = ""

    @ColumnInfo(name="data_de_criacao")
    val dataDeCriacao: LocalDateTime = LocalDateTime.now()

    @ColumnInfo(name="email")
    val email: String = ""

    @ColumnInfo(name="idioma")
    val idioma : String = ""

    @ColumnInfo(name="codigo_aleatorio")
    val codigoAleatorio : String? = null

}