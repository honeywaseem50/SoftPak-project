package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.OrderTransaction
import com.example.data.model.SoftwareProduct
import com.example.data.model.VaultKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [SoftwareProduct::class, VaultKey::class, OrderTransaction::class],
    version = 1,
    exportSchema = false
)
abstract class SoftBazaarDatabase : RoomDatabase() {
    abstract fun dao(): SoftBazaarDao

    companion object {
        @Volatile
        private var INSTANCE: SoftBazaarDatabase? = null

        fun getDatabase(context: Context): SoftBazaarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SoftBazaarDatabase::class.java,
                    "softbazaar_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.let { database ->
                                    database.dao().insertProducts(InitialCatalogData.initialProducts)
                                    database.dao().insertVaultKeys(InitialCatalogData.initialVaultKeys)
                                    InitialCatalogData.initialOrders.forEach {
                                        database.dao().insertOrder(it)
                                    }
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
