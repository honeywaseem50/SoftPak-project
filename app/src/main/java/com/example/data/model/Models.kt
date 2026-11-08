package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "software_products")
data class SoftwareProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val sku: String,
    val category: String, // "windows", "vpn", "security", "office", "dev", "creative"
    val categoryLabel: String,
    val subtitle: String,
    val description: String,
    val pricePkr: Int,
    val originalPricePkr: Int,
    val discountPercent: Int = if (originalPricePkr > pricePkr) ((originalPricePkr - pricePkr) * 100 / originalPricePkr) else 0,
    val rating: Double = 4.9,
    val reviewCount: Int = 1200,
    val stockKeys: Int = 42,
    val iconType: String = "window",
    val badgeText: String = "Official Retail",
    val deliverySpeedBadge: String = "Instant Delivery",
    val platforms: String = "Windows 11 • 64-Bit / 32-Bit • Global & Pakistan",
    val benefit1Title: String = "Lifetime Validity - 1 PC Activation",
    val benefit1Desc: String = "Permanent license tied to your motherboard. Reinstall anytime without losing validity.",
    val benefit2Title: String = "Clean Install or Upgrade Home",
    val benefit2Desc: String = "Seamlessly upgrades from Windows 10/11 Home to Pro without wiping documents or apps.",
    val benefit3Title: String = "Official Online Microsoft Servers",
    val benefit3Desc: String = "Activates directly in Settings > System > Activation via legitimate Microsoft servers.",
    val benefit4Title: String = "Urdu & English Video Guide Included",
    val benefit4Desc: String = "Get a step-by-step 2-minute walkthrough on WhatsApp with instant remote support if needed.",
    val isActive: Boolean = true,
    val totalDispatched: Int = 184,
    val autoRestockEnabled: Boolean = true
)

@Entity(tableName = "vault_keys")
data class VaultKey(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: Long,
    val productTitle: String,
    val sku: String,
    val licenseKey: String,
    val keyLabel: String = "Product License Key",
    val validityTier: String = "Retail Key • 1 PC Lifetime",
    val purchaseDateText: String = "Just now",
    val syncStatusText: String = "Ready to Activate",
    val isVerified: Boolean = true,
    val remainingPercent: Float? = null, // for subscriptions (e.g. 0.92f)
    val remainingDaysText: String? = null,
    val category: String = "active", // "active", "help"
    val iconType: String = "laptop_windows",
    val note: String = "Official Microsoft Retail Server Verified"
)

@Entity(tableName = "order_transactions")
data class OrderTransaction(
    @PrimaryKey
    val orderId: String,
    val productId: Long,
    val productTitle: String,
    val customerName: String,
    val customerWhatsApp: String,
    val customerEmail: String = "",
    val paymentChannel: String, // "easypaisa", "jazzcash", "raast", "card"
    val amountPkr: Int,
    val licenseKey: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "DISPATCHED"
)

data class CategoryItem(
    val id: String,
    val title: String,
    val iconName: String,
    val count: Int,
    val description: String
)

enum class PaymentChannel(
    val id: String,
    val title: String,
    val badge: String,
    val subtext: String,
    val shortCode: String,
    val feeText: String = "0% Fee"
) {
    EASYPAISA("easypaisa", "Easypaisa", "Popular", "Instant In-App PIN Prompt or Mobile Debit", "EP"),
    JAZZCASH("jazzcash", "JazzCash", "Fast", "Automated MPIN Push Request to Wallet", "JC"),
    RAAST("raast", "Raast P2P Instant", "0% Surcharge", "State Bank of Pakistan Fast Raast IBAN", "RT"),
    CARD("card", "SadaPay / NayaPay / Cards", "Debit/Credit", "Visa, Mastercard, SadaBiz, PayPak", "SP")
}
