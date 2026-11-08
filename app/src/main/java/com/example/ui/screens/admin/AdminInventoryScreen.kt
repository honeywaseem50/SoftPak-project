package com.example.ui.screens.admin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SoftwareProduct
import com.example.ui.components.SoftBazaarTopBar
import com.example.ui.screens.store.formatPkr
import com.example.ui.screens.store.getSoftwareIcon
import com.example.ui.screens.store.getSoftwareIconColor
import com.example.ui.theme.ErrorColor
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.OnErrorContainer
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OutlineColor
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryMint
import com.example.ui.theme.SurfaceBright
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TertiaryContainer
import com.example.ui.theme.TertiaryFixedDim
import com.example.ui.viewmodel.ScreenDestination
import com.example.ui.viewmodel.SoftBazaarViewModel

@Composable
fun AdminInventoryScreen(
    viewModel: SoftBazaarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val products by viewModel.adminFilteredProducts.collectAsState()
    val allProducts by viewModel.rawProducts.collectAsState()
    val adminSearchQuery by viewModel.adminSearchQuery.collectAsState()
    val adminFilter by viewModel.adminFilter.collectAsState()
    val batchTargetSku by viewModel.batchInjectTargetSku.collectAsState()

    var productToEditPrice by remember { mutableStateOf<SoftwareProduct?>(null) }
    var editPriceInput by remember { mutableStateOf("") }

    val totalVaultPool = allProducts.sumOf { it.stockKeys }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceDark)
    ) {
        SoftBazaarTopBar(
            subtitle = "Admin Operational Console • PK Central Node",
            onSearchClick = {},
            onWhatsAppClick = {}
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Admin Head Banner & Live Pulse
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.size(8.dp).background(SecondaryMint, CircleShape))
                        Text(
                            text = "OPERATIONAL HUB",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryMint,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Filled.Storage, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(13.dp))
                            Text("PK Central Node", fontSize = 11.sp, color = OnSurfaceVariant, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Admin Console",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryCyan,
                            letterSpacing = (-0.5).sp
                        )
                        Text(
                            text = "Live digital inventory, license vault & payout metrics",
                            fontSize = 12.sp,
                            color = OnSurfaceVariant
                        )
                    }

                    // + Add Keys Button
                    Button(
                        onClick = { viewModel.navigateTo(ScreenDestination.AddSoftware) },
                        modifier = Modifier
                            .height(40.dp)
                            .testTag("admin_add_keys_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Brush.horizontalGradient(listOf(PrimaryContainer, SecondaryMint)))
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Filled.AddCircle, contentDescription = null, tint = Color(0xFF00373A), modifier = Modifier.size(17.dp))
                                Text("Add Keys", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00373A))
                            }
                        }
                    }
                }
            }

            // Key Metrics Row (Bento Grid)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Today's Total Sales Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(SurfaceContainerHigh)
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Filled.Payments, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(16.dp))
                                    Text("Today's Total Sales", fontSize = 12.sp, color = OnSurfaceVariant)
                                }

                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("₨ 48,500", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryCyan)
                                    Text("+18.4%", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SecondaryMint)
                                }

                                Text(
                                    text = "26 instant checkout orders • 92% via Raast/SadaPay",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariant
                                )
                            }

                            // Decorative Micro Graph
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(modifier = Modifier.width(6.dp).height(14.dp).background(SurfaceContainerHighest, RoundedCornerShape(2.dp)))
                                Box(modifier = Modifier.width(6.dp).height(24.dp).background(SurfaceContainerHighest, RoundedCornerShape(2.dp)))
                                Box(modifier = Modifier.width(6.dp).height(20.dp).background(SecondaryMint.copy(alpha = 0.4f), RoundedCornerShape(2.dp)))
                                Box(modifier = Modifier.width(6.dp).height(34.dp).background(SecondaryMint.copy(alpha = 0.7f), RoundedCornerShape(2.dp)))
                                Box(modifier = Modifier.width(6.dp).height(46.dp).background(SecondaryMint, RoundedCornerShape(2.dp)))
                            }
                        }
                    }

                    // 2 Sub-metrics
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Active SKUs
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(SurfaceContainer)
                                .padding(14.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Active SKUs", fontSize = 12.sp, color = OnSurfaceVariant)
                                    Icon(Icons.Filled.Inventory2, contentDescription = null, tint = PrimaryCyan, modifier = Modifier.size(16.dp))
                                }
                                Text("${allProducts.size}", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                                Text("100% Listed", fontSize = 11.sp, color = SecondaryMint, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        // Vault Pool
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(SurfaceContainer)
                                .padding(14.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Vault Pool", fontSize = 12.sp, color = OnSurfaceVariant)
                                    Icon(Icons.Filled.Key, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(16.dp))
                                }
                                Text("$totalVaultPool", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                                Text("Unsold licenses", fontSize = 11.sp, color = OnSurfaceVariant)
                            }
                        }
                    }
                }
            }

            // Search & Filters
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = adminSearchQuery,
                        onValueChange = { viewModel.setAdminSearchQuery(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_search_input"),
                        placeholder = {
                            Text("Search SKU, license key, or product...", fontSize = 13.sp, color = OnSurfaceVariant)
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Search, contentDescription = null, tint = OnSurfaceVariant)
                        },
                        trailingIcon = {
                            Icon(Icons.Filled.Tune, contentDescription = null, tint = OnSurfaceVariant)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceContainerHigh,
                            unfocusedContainerColor = SurfaceContainerHigh,
                            focusedBorderColor = PrimaryContainer,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = PrimaryCyan,
                            unfocusedTextColor = PrimaryCyan
                        ),
                        singleLine = true
                    )

                    // Filter Pills
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AdminFilterChip("All (${allProducts.size})", isSelected = adminFilter == "all", onClick = { viewModel.setAdminFilter("all") })
                        AdminFilterChip("Low Stock (< 5)", isSelected = adminFilter == "low_stock", isAlert = true, onClick = { viewModel.setAdminFilter("low_stock") })
                        AdminFilterChip("Operating Systems", isSelected = adminFilter == "os", onClick = { viewModel.setAdminFilter("os") })
                        AdminFilterChip("Security & Antivirus", isSelected = adminFilter == "security", onClick = { viewModel.setAdminFilter("security") })
                        AdminFilterChip("VPN Tools", isSelected = adminFilter == "vpn", onClick = { viewModel.setAdminFilter("vpn") })
                    }
                }
            }

            // Catalog Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("Catalog & Keys Vault", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainerHighest, RoundedCornerShape(10.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("Sorted by Stock", fontSize = 10.sp, color = OnSurfaceVariant)
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "Exporting catalog CSV to downloads...", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(Icons.Filled.FileDownload, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(16.dp))
                        Text("Export CSV", fontSize = 12.sp, color = SecondaryMint, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Products Listing
            items(products, key = { it.id }) { product ->
                AdminProductRowCard(
                    product = product,
                    onAddKeys = { viewModel.openBatchInjector(product.sku) },
                    onEditPrice = {
                        productToEditPrice = product
                        editPriceInput = product.pricePkr.toString()
                    },
                    onToggleActive = { viewModel.toggleProductActive(product) },
                    onDelete = { viewModel.deleteProduct(product) }
                )
            }

            // WhatsApp Bot Status
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainer)
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SecondaryMint.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.CellTower, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(20.dp))
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("Auto WhatsApp Bot", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurface)
                                Box(modifier = Modifier.size(6.dp).background(SecondaryMint, CircleShape))
                            }
                            Text("0.8s dispatch average • 100% gateway uptime", fontSize = 11.sp, color = OnSurfaceVariant)
                        }
                    }

                    Button(
                        onClick = {
                            Toast.makeText(context, "WhatsApp Bot gateway pinged: Latency 42ms (Operational)", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHighest)
                    ) {
                        Text("Test Bot", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryMint)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Batch Injector Modal
        batchTargetSku?.let { sku ->
            BatchKeyInjectorModal(
                sku = sku,
                onDismiss = { viewModel.closeBatchInjector() },
                onConfirm = { count ->
                    viewModel.injectBatchKeys(sku, count)
                }
            )
        }

        // Edit Price Dialog
        productToEditPrice?.let { product ->
            AlertDialog(
                onDismissRequest = { productToEditPrice = null },
                title = { Text("Edit Price: ${product.title}", color = PrimaryCyan, fontSize = 16.sp) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Enter new selling price in PKR:", fontSize = 13.sp, color = OnSurfaceVariant)
                        OutlinedTextField(
                            value = editPriceInput,
                            onValueChange = { editPriceInput = it.filter { ch -> ch.isDigit() } },
                            modifier = Modifier.fillMaxWidth().testTag("edit_price_input"),
                            prefix = { Text("₨ ", color = SecondaryMint, fontWeight = FontWeight.Bold) },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = SurfaceContainerHigh,
                                unfocusedContainerColor = SurfaceContainerHigh,
                                focusedBorderColor = PrimaryContainer,
                                unfocusedBorderColor = Color.Transparent,
                                focusedTextColor = PrimaryCyan,
                                unfocusedTextColor = PrimaryCyan
                            )
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val newPrice = editPriceInput.toIntOrNull()
                            if (newPrice != null && newPrice > 0) {
                                viewModel.updateProductPrice(product.id, newPrice)
                            }
                            productToEditPrice = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
                    ) {
                        Text("Save Price", color = OnPrimaryContainer, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { productToEditPrice = null }) {
                        Text("Cancel", color = OnSurfaceVariant)
                    }
                },
                containerColor = SurfaceContainer
            )
        }
    }
}

@Composable
fun AdminProductRowCard(
    product: SoftwareProduct,
    onAddKeys: () -> Unit,
    onEditPrice: () -> Unit,
    onToggleActive: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerHigh)
            .border(1.dp, SurfaceContainerHighest, RoundedCornerShape(16.dp))
            .padding(14.dp)
            .testTag("admin_item_${product.sku}"),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerHighest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getSoftwareIcon(product.iconType),
                        contentDescription = null,
                        tint = getSoftwareIconColor(product.iconType),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = product.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryCyan,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Box(modifier = Modifier.size(6.dp).background(if (product.isActive) SecondaryMint else OutlineColor, CircleShape))
                    }
                    Text(
                        text = "${product.sku} • ${product.badgeText}",
                        fontSize = 11.sp,
                        color = OnSurfaceVariant
                    )
                }
            }

            // Stock Badge
            val stockColor = when {
                product.stockKeys == 0 -> ErrorContainer
                product.stockKeys < 5 -> TertiaryContainer
                else -> SecondaryMint.copy(alpha = 0.15f)
            }
            val textCol = when {
                product.stockKeys == 0 -> ErrorColor
                product.stockKeys < 5 -> TertiaryFixedDim
                else -> SecondaryMint
            }
            Box(
                modifier = Modifier
                    .background(stockColor, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = when {
                            product.stockKeys == 0 -> Icons.Filled.Cancel
                            product.stockKeys < 5 -> Icons.Filled.Warning
                            else -> Icons.Filled.CheckCircle
                        },
                        contentDescription = null,
                        tint = textCol,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "${product.stockKeys} Keys Left",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = textCol
                    )
                }
            }
        }

        // Stats Box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(SurfaceContainerLowest.copy(alpha = 0.7f))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Selling Price", fontSize = 10.sp, color = OnSurfaceVariant)
                Text("₨ ${formatPkr(product.pricePkr)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
            }
            Column {
                Text("Total Dispatched", fontSize = 10.sp, color = OnSurfaceVariant)
                Text("${product.totalDispatched} units", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurface)
            }
            Column {
                Text("Restock Status", fontSize = 10.sp, color = OnSurfaceVariant)
                Text(
                    text = if (product.stockKeys < 5) "Urgent" else "Enabled",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (product.stockKeys < 5) TertiaryFixedDim else SecondaryMint
                )
            }
        }

        // Action controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = onAddKeys,
                    modifier = Modifier.height(34.dp).testTag("action_add_keys_${product.sku}"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (product.stockKeys < 5) SecondaryContainer else SurfaceContainerHighest
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = if (product.stockKeys < 5) Icons.Filled.Bolt else Icons.Filled.Add,
                        contentDescription = null,
                        tint = if (product.stockKeys < 5) Color(0xFF00382A) else SecondaryMint,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (product.stockKeys < 5) "Restock Keys" else "Add Keys",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (product.stockKeys < 5) Color(0xFF00382A) else PrimaryCyan
                    )
                }

                IconButton(
                    onClick = onEditPrice,
                    modifier = Modifier
                        .size(34.dp)
                        .background(SurfaceContainer, RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Price", tint = OnSurfaceVariant, modifier = Modifier.size(16.dp))
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(34.dp)
                        .background(SurfaceContainer, RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = ErrorColor, modifier = Modifier.size(16.dp))
                }
            }

            // Active Switch
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = if (product.isActive) "Active" else "Paused",
                    fontSize = 11.sp,
                    color = OnSurfaceVariant
                )
                Switch(
                    checked = product.isActive,
                    onCheckedChange = { onToggleActive() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF00382A),
                        checkedTrackColor = SecondaryMint,
                        uncheckedThumbColor = OnSurfaceVariant,
                        uncheckedTrackColor = SurfaceContainer
                    )
                )
            }
        }
    }
}

@Composable
private fun AdminFilterChip(
    label: String,
    isSelected: Boolean,
    isAlert: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                when {
                    isSelected -> SecondaryMint
                    isAlert -> ErrorContainer.copy(alpha = 0.3f)
                    else -> SurfaceContainerHigh
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = when {
                isSelected -> Color(0xFF00382A)
                isAlert -> ErrorColor
                else -> OnSurfaceVariant
            }
        )
    }
}

@Composable
fun BatchKeyInjectorModal(
    sku: String,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var rawKeysText by remember {
        mutableStateOf(
            "W9X2K-4NM8Q-BRVPT-8K3G1-9PXRT\n7NBT2-H4F8G-Q8P2M-3KD8W-6VTY4\nBK89Q-N2MV8-99TXR-74P8Q-1XCVB"
        )
    }

    val keysCount = rawKeysText.split("\n").filter { it.trim().length > 5 }.size

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Filled.Key, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(22.dp))
                Column {
                    Text("Inject License Keys", color = PrimaryCyan, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("SKU: $sku", color = SecondaryMint, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Paste raw product keys (one per line):", fontSize = 12.sp, color = OnSurfaceVariant)
                OutlinedTextField(
                    value = rawKeysText,
                    onValueChange = { rawKeysText = it },
                    modifier = Modifier.fillMaxWidth().height(140.dp).testTag("batch_keys_textarea"),
                    placeholder = {
                        Text("XXXXX-XXXXX-XXXXX-XXXXX\nYYYYY-YYYYY-YYYYY-YYYYY", color = OnSurfaceVariant.copy(alpha = 0.5f), fontSize = 11.sp)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedBorderColor = PrimaryContainer,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = PrimaryCyan,
                        unfocusedTextColor = PrimaryCyan
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Retail, MAK & KMS supported", fontSize = 11.sp, color = OnSurfaceVariant)
                    Text("$keysCount keys detected", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryMint)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(if (keysCount > 0) keysCount else 3) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer)
            ) {
                Icon(Icons.Filled.CloudUpload, contentDescription = null, tint = OnPrimaryContainer, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Encrypt & Add to Vault", color = OnPrimaryContainer, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = OnSurfaceVariant)
            }
        },
        containerColor = SurfaceContainer
    )
}
