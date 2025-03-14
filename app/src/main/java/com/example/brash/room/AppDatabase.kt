package com.example.brash.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import com.example.app02.repository.data.dao.Frase.FraseDAO
//import com.example.app02.repository.data.model.FraseModel

/*@Database(entities = [FraseModel::class], version = 1)
abstract class AppDatabase(): RoomDatabase() {

    abstract fun FraseDAO(): FraseDAO

    companion object {
        private lateinit var INSTANCE: AppDatabase
        fun getDatabase(context: Context): AppDatabase {

            if(!::INSTANCE.isInitialized) {

                synchronized(AppDatabase::class) {

                    INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "mydatabase.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
    // Métod para limpar todas as tabelas
    fun clearDatabase() {
        runInTransaction {
            FraseDAO().deleteAll()  // Exemplo: Adicione um métod no seu DAO para apagar todos os dados
        }
    }
}*/