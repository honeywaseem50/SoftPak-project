package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.OrderTransaction
import com.example.data.model.SoftwareProduct
import com.example.data.model.VaultKey
import kotlinx.coroutines.flow.Flow

@Dao
interface SoftBazaarDao {
    // Products
    @Query("SELECT * FROM software_products ORDER BY id ASC")
    fun getAllProducts(): Flow<List<SoftwareProduct>>

    @Query("SELECT * FROM software_products WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: Long): SoftwareProduct?

    @Query("SELECT * FROM software_products WHERE isActive = 1 ORDER BY id ASC")
    fun getActiveProducts(): Flow<List<SoftwareProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: SoftwareProduct): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<SoftwareProduct>)

    @Update
    suspend fun updateProduct(product: SoftwareProduct)

    @Delete
    suspend fun deleteProduct(product: SoftwareProduct)

    @Query("UPDATE software_products SET stockKeys = stockKeys + :addedCount WHERE sku = :sku")
    suspend fun addStockBySku(sku: String, addedCount: Int)

    @Query("UPDATE software_products SET stockKeys = CASE WHEN stockKeys > 0 THEN stockKeys - 1 ELSE 0 END, totalDispatched = totalDispatched + 1 WHERE id = :productId")
    suspend fun recordDispatch(productId: Long)

    @Query("UPDATE software_products SET pricePkr = :newPrice WHERE id = :productId")
    suspend fun updatePrice(productId: Long, newPrice: Int)

    @Query("UPDATE software_products SET isActive = :isActive WHERE id = :productId")
    suspend fun toggleActive(productId: Long, isActive: Boolean)

    // Vault Keys
    @Query("SELECT * FROM vault_keys ORDER BY id DESC")
    fun getAllVaultKeys(): Flow<List<VaultKey>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaultKey(key: VaultKey): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaultKeys(keys: List<VaultKey>)

    @Query("SELECT COUNT(*) FROM vault_keys")
    fun getVaultKeyCount(): Flow<Int>

    // Orders
    @Query("SELECT * FROM order_transactions ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<OrderTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderTransaction)

    @Query("SELECT COALESCE(SUM(amountPkr), 0) FROM order_transactions")
    fun getTotalSales(): Flow<Int>
}
