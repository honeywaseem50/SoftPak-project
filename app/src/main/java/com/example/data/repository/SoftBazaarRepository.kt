package com.example.data.repository

import com.example.data.local.SoftBazaarDao
import com.example.data.model.OrderTransaction
import com.example.data.model.SoftwareProduct
import com.example.data.model.VaultKey
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class SoftBazaarRepository(private val dao: SoftBazaarDao) {

    val allProducts: Flow<List<SoftwareProduct>> = dao.getAllProducts()
    val activeProducts: Flow<List<SoftwareProduct>> = dao.getActiveProducts()
    val allVaultKeys: Flow<List<VaultKey>> = dao.getAllVaultKeys()
    val allOrders: Flow<List<OrderTransaction>> = dao.getAllOrders()
    val totalSales: Flow<Int> = dao.getTotalSales()

    suspend fun getProductById(id: Long): SoftwareProduct? {
        return dao.getProductById(id)
    }

    suspend fun processPurchase(
        product: SoftwareProduct,
        customerName: String,
        customerPhone: String,
        customerEmail: String,
        paymentChannel: String
    ): Pair<OrderTransaction, VaultKey> {
        val orderNum = Random.nextInt(10000, 99999)
        val orderId = "#SB-$orderNum"
        val generatedKey = generateSerialKey(product.iconType)
        val now = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val dateText = dateFormat.format(Date(now))

        val order = OrderTransaction(
            orderId = orderId,
            productId = product.id,
            productTitle = product.title,
            customerName = customerName,
            customerWhatsApp = customerPhone,
            customerEmail = customerEmail,
            paymentChannel = paymentChannel,
            amountPkr = product.pricePkr,
            licenseKey = generatedKey,
            timestamp = now,
            status = "DISPATCHED"
        )

        val vaultKey = VaultKey(
            productId = product.id,
            productTitle = product.title,
            sku = product.sku,
            licenseKey = generatedKey,
            keyLabel = if (product.category == "vpn") "Account Activation Code" else "Product License Key",
            validityTier = product.subtitle,
            purchaseDateText = "Purchased $dateText",
            syncStatusText = "Active",
            isVerified = true,
            remainingPercent = if (product.category == "vpn") 1.0f else null,
            remainingDaysText = if (product.category == "vpn") "730 Days Left" else null,
            category = "active",
            iconType = when (product.iconType) {
                "vpn" -> "vpn_lock"
                "download" -> "cloud_download"
                "shield" -> "shield"
                else -> "laptop_windows"
            },
            note = "Verified Genuine • Sent to WhatsApp $customerPhone"
        )

        dao.insertOrder(order)
        dao.insertVaultKey(vaultKey)
        dao.recordDispatch(product.id)

        return Pair(order, vaultKey)
    }

    suspend fun addNewSoftware(
        title: String,
        category: String,
        sellPrice: Int,
        strikePrice: Int,
        platforms: String,
        tier: String,
        keys: List<String>
    ): Long {
        val sku = "SB-" + title.take(4).uppercase(Locale.ROOT).replace(" ", "") + "-" + Random.nextInt(100, 999)
        val count = if (keys.isNotEmpty()) keys.size else 10
        val categoryLabel = when (category) {
            "vpn" -> "VPNs & Privacy"
            "security" -> "Antivirus & Security"
            "dev" -> "Developer Tools"
            "creative" -> "Graphic & Video"
            else -> "Windows & Office"
        }
        val iconType = when (category) {
            "vpn" -> "vpn"
            "security" -> "shield"
            "dev" -> "code"
            "creative" -> "palette"
            else -> "window"
        }

        val newProduct = SoftwareProduct(
            title = title,
            sku = sku,
            category = category,
            categoryLabel = categoryLabel,
            subtitle = "$tier • $platforms",
            description = "Official License • Instant WhatsApp & Screen Handover",
            pricePkr = sellPrice,
            originalPricePkr = strikePrice,
            stockKeys = count,
            iconType = iconType,
            badgeText = tier,
            deliverySpeedBadge = "Instant Dispatch",
            platforms = platforms,
            isActive = true
        )
        return dao.insertProduct(newProduct)
    }

    suspend fun injectKeysForSku(sku: String, keysCount: Int) {
        dao.addStockBySku(sku, keysCount)
    }

    suspend fun updateProductPrice(productId: Long, newPrice: Int) {
        dao.updatePrice(productId, newPrice)
    }

    suspend fun toggleProductActive(productId: Long, isActive: Boolean) {
        dao.toggleActive(productId, isActive)
    }

    suspend fun deleteProduct(product: SoftwareProduct) {
        dao.deleteProduct(product)
    }

    private fun generateSerialKey(type: String): String {
        val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
        fun chunk(len: Int) = (1..len).map { chars[Random.nextInt(chars.length)] }.joinToString("")
        return when (type) {
            "vpn" -> "NORD-PK26-${chunk(4)}-${chunk(4)}"
            "download" -> "${chunk(5)}-${chunk(5)}-${chunk(5)}-${chunk(5)}"
            else -> "${chunk(5)}-${chunk(5)}-${chunk(5)}-${chunk(5)}-${chunk(5)}"
        }
    }
}
