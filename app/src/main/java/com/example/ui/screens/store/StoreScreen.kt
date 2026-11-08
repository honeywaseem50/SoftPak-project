package com.example.ui.screens.store

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.LaptopWindows
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SoftwareProduct
import com.example.ui.components.HighTrustGuaranteeBanner
import com.example.ui.components.LocalizedPaymentTrustSection
import com.example.ui.components.RealtimeSupportCard
import com.example.ui.components.SoftBazaarTopBar
import com.example.ui.components.launchWhatsApp
import com.example.ui.theme.ErrorColor
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OnErrorContainer
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SecondaryMint
import com.example.ui.theme.SurfaceBright
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.TertiaryContainer
import com.example.ui.theme.TertiaryFixedDim
import com.example.ui.viewmodel.ScreenDestination
import com.example.ui.viewmodel.SoftBazaarViewModel

@Composable
fun StoreScreen(
    viewModel: SoftBazaarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val products by viewModel.filteredProducts.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(com.example.ui.theme.SurfaceDark)
    ) {
        SoftBazaarTopBar(
            subtitle = "Pakistan Official Software Store",
            onSearchClick = {},
            onWhatsAppClick = { launchWhatsApp(context, "Hi SoftBazaar, I want to inquire about genuine software licenses") }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Storefront Live in PK Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(SecondaryMint, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Storefront Live in PK",
                                fontSize = 12.sp,
                                color = OnSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Find Genuine Software",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryCyan,
                            letterSpacing = (-0.5).sp
                        )
                    }

                    // 100% Genuine Badge
                    Row(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VerifiedUser,
                            contentDescription = null,
                            tint = SecondaryMint,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "100% Genuine",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryMint
                        )
                    }
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("store_search_input"),
                    placeholder = {
                        Text(
                            text = "Search Windows 11, VPN, IDM, Antivirus...",
                            fontSize = 13.sp,
                            color = OnSurfaceVariant
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = OnSurfaceVariant
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Clear",
                                    tint = OnSurfaceVariant
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerHigh,
                        unfocusedContainerColor = SurfaceContainerHigh,
                        focusedBorderColor = PrimaryContainer.copy(alpha = 0.5f),
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = PrimaryCyan,
                        unfocusedTextColor = PrimaryCyan
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
                )
            }

            // Quick Search Chips
            item {
                val chips = listOf("Windows 11 Pro", "IDM Lifetime", "NordVPN", "Office 2024")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "POPULAR:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceVariant
                    )
                    chips.forEach { chipText ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(SurfaceContainer)
                                .clickable {
                                    val queryTerm = when (chipText) {
                                        "Windows 11 Pro" -> "Windows"
                                        "IDM Lifetime" -> "IDM"
                                        "NordVPN" -> "NordVPN"
                                        else -> "Office"
                                    }
                                    viewModel.setSearchQuery(queryTerm)
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = chipText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // High-Trust Guarantee Banner
            item {
                HighTrustGuaranteeBanner()
            }

            // Interactive Category Pills
            item {
                val categories = listOf(
                    "all" to "All Software",
                    "windows" to "Windows & Office",
                    "vpn" to "VPNs & Privacy",
                    "security" to "Antivirus & Security",
                    "dev" to "Developer Tools",
                    "creative" to "Graphic & Video"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { (catId, catLabel) ->
                        val isSelected = selectedCategory == catId
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isSelected) PrimaryContainer else SurfaceContainer
                                )
                                .clickable { viewModel.selectCategory(catId) }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .testTag("cat_pill_$catId")
                        ) {
                            Text(
                                text = catLabel,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) OnPrimaryContainer else OnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Best Sellers Header
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
                        Icon(
                            imageVector = Icons.Filled.LocalFireDepartment,
                            contentDescription = null,
                            tint = TertiaryFixedDim,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Best Sellers in Pakistan",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryCyan
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { viewModel.selectCategory("all") }
                    ) {
                        Text(
                            text = "View All",
                            fontSize = 12.sp,
                            color = SecondaryMint,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = null,
                            tint = SecondaryMint,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Products Stream
            if (products.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No software found matching \"$searchQuery\"",
                            color = OnSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(products, key = { it.id }) { product ->
                    ProductCardItem(
                        product = product,
                        onClick = { viewModel.openProductDetail(product.id) },
                        onBuyClick = { viewModel.openCheckout(product.id) }
                    )
                }
            }

            // Localized Payment Methods Trust Banner
            item {
                Spacer(modifier = Modifier.height(4.dp))
                LocalizedPaymentTrustSection()
            }

            // Real-Time Human Support Card
            item {
                RealtimeSupportCard(
                    onOpenWhatsApp = {
                        launchWhatsApp(context, "Hi SoftBazaar, I need support with Windows / Software activation")
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ProductCardItem(
    product: SoftwareProduct,
    onClick: () -> Unit,
    onBuyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SurfaceContainerHigh)
            .border(1.dp, SurfaceContainerHighest, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
            .testTag("product_card_${product.id}"),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top row: Icon + Title & Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getSoftwareIcon(product.iconType),
                    contentDescription = null,
                    tint = getSoftwareIconColor(product.iconType),
                    modifier = Modifier.size(26.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Badge 1: e.g. "Official Retail"
                    Box(
                        modifier = Modifier
                            .background(TertiaryContainer, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = product.badgeText.uppercase(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF875400)
                        )
                    }

                    // Badge 2: Delivery Speed
                    Row(
                        modifier = Modifier
                            .background(SecondaryMint.copy(alpha = 0.12f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Bolt,
                            contentDescription = null,
                            tint = SecondaryMint,
                            modifier = Modifier.size(11.dp)
                        )
                        Text(
                            text = product.deliverySpeedBadge,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryMint
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryCyan,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.description,
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Pricing and rating row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "₨ ${formatPkr(product.pricePkr)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SecondaryMint
                )
                Text(
                    text = "₨ ${formatPkr(product.originalPricePkr)}",
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    textDecoration = TextDecoration.LineThrough
                )
                if (product.discountPercent > 0) {
                    Box(
                        modifier = Modifier
                            .background(ErrorContainer, RoundedCornerShape(10.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "-${product.discountPercent}%",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnErrorContainer
                        )
                    }
                }
            }

            // Rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = TertiaryFixedDim,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = "${product.rating}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TertiaryFixedDim
                )
                Text(
                    text = "(${formatReviewCount(product.reviewCount)})",
                    fontSize = 11.sp,
                    color = OnSurfaceVariant
                )
            }
        }

        // Bottom action & stock row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .background(
                            if (product.stockKeys > 5) SecondaryMint else Color(0xFFFFB95F),
                            CircleShape
                        )
                )
                Text(
                    text = if (product.stockKeys > 0) "${product.stockKeys} Keys in Stock" else "Restocking Soon",
                    fontSize = 12.sp,
                    color = OnSurfaceVariant
                )
            }

            Button(
                onClick = onBuyClick,
                modifier = Modifier
                    .height(38.dp)
                    .testTag("buy_key_btn_${product.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(PrimaryContainer, SecondaryContainer)
                            )
                        )
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Buy Key",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnPrimary
                        )
                        Icon(
                            imageVector = Icons.Filled.ShoppingCartCheckout,
                            contentDescription = null,
                            tint = OnPrimary,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }
    }
}

fun getSoftwareIcon(iconType: String): ImageVector {
    return when (iconType) {
        "window" -> Icons.Filled.Window
        "vpn" -> Icons.Filled.VpnLock
        "download" -> Icons.Filled.DownloadForOffline
        "shield" -> Icons.Filled.Shield
        "office" -> Icons.Filled.Description
        "code" -> Icons.Filled.Code
        "palette" -> Icons.Filled.Palette
        else -> Icons.Filled.LaptopWindows
    }
}

fun getSoftwareIconColor(iconType: String): Color {
    return when (iconType) {
        "window" -> PrimaryContainer
        "vpn" -> PrimaryCyan
        "download" -> SecondaryMint
        "shield" -> SecondaryMint
        "office" -> TertiaryFixedDim
        "code" -> SecondaryMint
        "palette" -> PrimaryContainer
        else -> SecondaryMint
    }
}

fun formatPkr(amount: Int): String {
    return String.format("%,d", amount)
}

fun formatReviewCount(count: Int): String {
    return if (count >= 1000) {
        String.format("%.1fk", count / 1000f)
    } else {
        count.toString()
    }
}
