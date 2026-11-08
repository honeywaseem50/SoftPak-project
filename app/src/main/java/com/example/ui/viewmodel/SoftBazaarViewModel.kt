package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.InitialCatalogData
import com.example.data.local.SoftBazaarDatabase
import com.example.data.model.CategoryItem
import com.example.data.model.OrderTransaction
import com.example.data.model.PaymentChannel
import com.example.data.model.SoftwareProduct
import com.example.data.model.VaultKey
import com.example.data.repository.SoftBazaarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ScreenDestination {
    object Store : ScreenDestination()
    object Categories : ScreenDestination()
    object Vault : ScreenDestination()
    object Admin : ScreenDestination()
    data class ProductDetail(val productId: Long) : ScreenDestination()
    data class Checkout(val productId: Long) : ScreenDestination()
    object AddSoftware : ScreenDestination()
}

data class OrderSuccessData(
    val order: OrderTransaction,
    val vaultKey: VaultKey
)

class SoftBazaarViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SoftBazaarRepository

    init {
        val database = SoftBazaarDatabase.getDatabase(application)
        repository = SoftBazaarRepository(database.dao())
    }

    // Navigation State
    private val _currentScreen = MutableStateFlow<ScreenDestination>(ScreenDestination.Store)
    val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

    // Store State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("all")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val rawProducts: StateFlow<List<SoftwareProduct>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InitialCatalogData.initialProducts)

    val filteredProducts: StateFlow<List<SoftwareProduct>> = combine(
        rawProducts,
        _searchQuery,
        _selectedCategory
    ) { products, query, cat ->
        products.filter { p ->
            val matchesCategory = (cat == "all") || (p.category.equals(cat, ignoreCase = true))
            val matchesQuery = query.isBlank() ||
                    p.title.contains(query, ignoreCase = true) ||
                    p.subtitle.contains(query, ignoreCase = true) ||
                    p.description.contains(query, ignoreCase = true) ||
                    p.sku.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InitialCatalogData.initialProducts)

    // Vault State
    val vaultKeys: StateFlow<List<VaultKey>> = repository.allVaultKeys
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InitialCatalogData.initialVaultKeys)

    private val _vaultFilter = MutableStateFlow("all") // "all", "active", "help"
    val vaultFilter: StateFlow<String> = _vaultFilter.asStateFlow()

    val filteredVaultKeys: StateFlow<List<VaultKey>> = combine(vaultKeys, _vaultFilter) { keys, filter ->
        when (filter) {
            "active" -> keys.filter { it.category == "active" }
            "help" -> keys.filter { it.category == "help" || it.keyLabel.contains("help", ignoreCase = true) }
            else -> keys
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InitialCatalogData.initialVaultKeys)

    // Admin State
    private val _adminSearchQuery = MutableStateFlow("")
    val adminSearchQuery: StateFlow<String> = _adminSearchQuery.asStateFlow()

    private val _adminFilter = MutableStateFlow("all") // "all", "low_stock", "os", "security", "vpn"
    val adminFilter: StateFlow<String> = _adminFilter.asStateFlow()

    val adminFilteredProducts: StateFlow<List<SoftwareProduct>> = combine(
        rawProducts,
        _adminSearchQuery,
        _adminFilter
    ) { products, query, filter ->
        products.filter { p ->
            val matchesFilter = when (filter) {
                "low_stock" -> p.stockKeys < 5
                "os" -> p.category == "windows"
                "security" -> p.category == "security"
                "vpn" -> p.category == "vpn"
                else -> true
            }
            val matchesQuery = query.isBlank() ||
                    p.title.contains(query, ignoreCase = true) ||
                    p.sku.contains(query, ignoreCase = true) ||
                    p.categoryLabel.contains(query, ignoreCase = true)
            matchesFilter && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InitialCatalogData.initialProducts)

    // Orders & Sales
    val allOrders: StateFlow<List<OrderTransaction>> = repository.allOrders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InitialCatalogData.initialOrders)

    // Active Toast Message
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Batch Key Injector BottomSheet State
    private val _batchInjectTargetSku = MutableStateFlow<String?>(null)
    val batchInjectTargetSku: StateFlow<String?> = _batchInjectTargetSku.asStateFlow()

    // Order Success Dialog State
    private val _orderSuccessModal = MutableStateFlow<OrderSuccessData?>(null)
    val orderSuccessModal: StateFlow<OrderSuccessData?> = _orderSuccessModal.asStateFlow()

    // Navigation Methods
    fun navigateTo(destination: ScreenDestination) {
        _currentScreen.value = destination
    }

    fun openProductDetail(productId: Long) {
        _currentScreen.value = ScreenDestination.ProductDetail(productId)
    }

    fun openCheckout(productId: Long) {
        _currentScreen.value = ScreenDestination.Checkout(productId)
    }

    fun navigateBack() {
        _currentScreen.value = ScreenDestination.Store
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setVaultFilter(filter: String) {
        _vaultFilter.value = filter
    }

    fun setAdminSearchQuery(query: String) {
        _adminSearchQuery.value = query
    }

    fun setAdminFilter(filter: String) {
        _adminFilter.value = filter
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun openBatchInjector(sku: String) {
        _batchInjectTargetSku.value = sku
    }

    fun closeBatchInjector() {
        _batchInjectTargetSku.value = null
    }

    fun dismissOrderSuccessModal() {
        _orderSuccessModal.value = null
    }

    // Business Logic Actions
    fun purchaseProduct(
        product: SoftwareProduct,
        customerName: String,
        customerWhatsApp: String,
        customerEmail: String,
        channel: PaymentChannel,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.processPurchase(
                product = product,
                customerName = customerName,
                customerPhone = customerWhatsApp,
                customerEmail = customerEmail,
                paymentChannel = channel.id
            )
            _orderSuccessModal.value = OrderSuccessData(
                order = result.first,
                vaultKey = result.second
            )
            onSuccess()
        }
    }

    fun injectBatchKeys(sku: String, keysCount: Int) {
        viewModelScope.launch {
            repository.injectKeysForSku(sku, keysCount)
            closeBatchInjector()
            showToast("Successfully injected $keysCount keys into $sku vault!")
        }
    }

    fun toggleProductActive(product: SoftwareProduct) {
        viewModelScope.launch {
            repository.toggleProductActive(product.id, !product.isActive)
            showToast("${product.title} ${if (!product.isActive) "activated" else "paused"}")
        }
    }

    fun updateProductPrice(productId: Long, newPrice: Int) {
        viewModelScope.launch {
            repository.updateProductPrice(productId, newPrice)
            showToast("Price updated to ₨ $newPrice")
        }
    }

    fun addNewSoftware(
        title: String,
        category: String,
        sellPrice: Int,
        strikePrice: Int,
        platforms: String,
        tier: String,
        keys: List<String>
    ) {
        viewModelScope.launch {
            repository.addNewSoftware(
                title = title,
                category = category,
                sellPrice = sellPrice,
                strikePrice = strikePrice,
                platforms = platforms,
                tier = tier,
                keys = keys
            )
            navigateTo(ScreenDestination.Admin)
            showToast("Published $title to SoftBazaar catalog!")
        }
    }

    fun deleteProduct(product: SoftwareProduct) {
        viewModelScope.launch {
            repository.deleteProduct(product)
            showToast("Removed ${product.title} from inventory")
        }
    }
}
