package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.InitialCatalogData
import com.example.data.local.SoftBazaarDatabase
import com.example.data.repository.SoftBazaarRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var database: SoftBazaarDatabase
    private lateinit var repository: SoftBazaarRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, SoftBazaarDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = SoftBazaarRepository(database.dao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `read string from context verifies SoftBazaar app name`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("SoftBazaar", appName)
    }

    @Test
    fun `catalog seed and purchase process flow`() = runBlocking {
        // Insert seed products
        database.dao().insertProducts(InitialCatalogData.initialProducts)
        val products = repository.allProducts.first()
        assertTrue(products.isNotEmpty())

        val win11 = products.first { it.sku == "SB-WIN11-PRO" }
        assertEquals("Windows 11 Professional", win11.title)
        assertEquals(1850, win11.pricePkr)

        // Process a simulated purchase
        val (order, vaultKey) = repository.processPurchase(
            product = win11,
            customerName = "Ali Raza",
            customerPhone = "+92 300 1234567",
            customerEmail = "ali@example.com",
            paymentChannel = "easypaisa"
        )

        assertNotNull(order.orderId)
        assertTrue(order.orderId.startsWith("#SB-"))
        assertEquals(1850, order.amountPkr)
        assertNotNull(vaultKey.licenseKey)

        // Verify Vault key was persisted
        val vaultKeys = repository.allVaultKeys.first()
        assertTrue(vaultKeys.any { it.licenseKey == vaultKey.licenseKey })
    }
}
