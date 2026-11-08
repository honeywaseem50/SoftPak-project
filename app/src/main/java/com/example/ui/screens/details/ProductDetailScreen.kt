package com.example.ui.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LaptopWindows
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Quickreply
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SoftwareProduct
import com.example.ui.components.SoftBazaarTopBar
import com.example.ui.components.launchWhatsApp
import com.example.ui.screens.store.formatPkr
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
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
import com.example.ui.viewmodel.SoftBazaarViewModel

@Composable
fun ProductDetailScreen(
    productId: Long,
    viewModel: SoftBazaarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val products by viewModel.rawProducts.collectAsState()
    val product = products.find { it.id == productId } ?: products.firstOrNull()

    var isHowItWorksExpanded by remember { mutableStateOf(true) }

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Product not found", color = PrimaryCyan)
        }
        return
    }

    Box(modifier = modifier.fillMaxSize().background(SurfaceDark)) {
        Column(modifier = Modifier.fillMaxSize()) {
            SoftBazaarTopBar(
                subtitle = "License Details • SoftBazaar PK",
                canGoBack = true,
                onBackClick = { viewModel.navigateBack() },
                onWhatsAppClick = {
                    launchWhatsApp(context, "Hi SoftBazaar, I want to inquire about ${product.title} (${product.sku})")
                }
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    // Breadcrumb
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Storefront,
                            contentDescription = null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "Store",
                            fontSize = 12.sp,
                            color = OnSurfaceVariant,
                            modifier = Modifier.clickable { viewModel.navigateBack() }
                        )
                        Icon(
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = product.categoryLabel,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SecondaryMint
                        )
                    }
                }

                // Hero Product Overview Card
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(SurfaceContainerHigh)
                            .border(1.dp, SurfaceContainerHighest, RoundedCornerShape(18.dp))
                            .padding(16.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                // Verified Emblem
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(SurfaceContainerHighest),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.VerifiedUser,
                                        contentDescription = "Verified Emblem",
                                        tint = SecondaryMint,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(16.dp)
                                            .background(SecondaryMint, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = null,
                                            tint = OnPrimary,
                                            modifier = Modifier.size(11.dp)
                                        )
                                    }
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Box(
                                        modifier = Modifier
                                            .background(SurfaceBright, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Verified,
                                                contentDescription = null,
                                                tint = TertiaryFixedDim,
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Text(
                                                text = product.badgeText.uppercase(),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TertiaryFixedDim
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = product.title,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryCyan,
                                        lineHeight = 26.sp
                                    )
                                    Text(
                                        text = product.subtitle,
                                        fontSize = 12.sp,
                                        color = OnSurfaceVariant
                                    )
                                }
                            }

                            // Rating & Realtime Proof Row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainer.copy(alpha = 0.8f))
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    repeat(5) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            contentDescription = null,
                                            tint = TertiaryFixedDim,
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${product.rating}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryCyan
                                    )
                                    Text(
                                        text = "(${product.reviewCount}+ verified)",
                                        fontSize = 11.sp,
                                        color = OnSurfaceVariant
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.LocationOn,
                                        contentDescription = null,
                                        tint = SecondaryMint,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "PK Verified",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SecondaryMint
                                    )
                                }
                            }
                        }
                    }
                }

                // Pricing & Stock Callout
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainer)
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
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
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SecondaryMint
                                )
                                Text(
                                    text = "₨ ${formatPkr(product.originalPricePkr)}",
                                    fontSize = 13.sp,
                                    color = OnSurfaceVariant,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .background(SecondaryMint.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Save ${product.discountPercent}% Today",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryMint
                                )
                            }
                        }

                        // Stock ticker
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
                                        .size(8.dp)
                                        .background(SecondaryMint, CircleShape)
                                )
                                Text(
                                    text = "${product.stockKeys} keys available in vault",
                                    fontSize = 12.sp,
                                    color = OnSurface
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Bolt,
                                    contentDescription = null,
                                    tint = SecondaryMint,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "Instant Dispatch",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SecondaryMint
                                )
                            }
                        }

                        // Supported Checkout Gateways
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerLow)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Supported Instant Checkout:",
                                fontSize = 11.sp,
                                color = OnSurfaceVariant
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf("JazzCash", "Easypaisa", "Raast", "SadaPay").forEach { method ->
                                    Box(
                                        modifier = Modifier
                                            .background(SurfaceContainerHigh, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 5.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = method,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (method == "Raast") SecondaryMint else PrimaryCyan
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Compatibility Tags
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CompatibilityTag(icon = Icons.Filled.LaptopWindows, text = "Windows 11")
                        CompatibilityTag(icon = Icons.Filled.Memory, text = "64-Bit / 32-Bit")
                        CompatibilityTag(icon = Icons.Filled.Public, text = "Global & PK")
                    }
                }

                // Key License Benefits
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Key License Benefits",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryCyan
                        )

                        BenefitRowCard(
                            icon = Icons.Filled.AllInclusive,
                            title = product.benefit1Title,
                            desc = product.benefit1Desc
                        )
                        BenefitRowCard(
                            icon = Icons.Filled.Upgrade,
                            title = product.benefit2Title,
                            desc = product.benefit2Desc
                        )
                        BenefitRowCard(
                            icon = Icons.Filled.CloudDone,
                            title = product.benefit3Title,
                            desc = product.benefit3Desc
                        )
                        BenefitRowCard(
                            icon = Icons.Filled.OndemandVideo,
                            title = product.benefit4Title,
                            desc = product.benefit4Desc
                        )
                    }
                }

                // Interactive Accordion: How will I receive my key?
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerHigh)
                            .border(1.dp, SurfaceContainerHighest, RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isHowItWorksExpanded = !isHowItWorksExpanded },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Quickreply,
                                    contentDescription = null,
                                    tint = SecondaryMint,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "How will I receive my key?",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryCyan
                                )
                            }
                            Icon(
                                imageVector = if (isHowItWorksExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                contentDescription = null,
                                tint = OnSurfaceVariant
                            )
                        }

                        AnimatedVisibility(visible = isHowItWorksExpanded) {
                            Column(
                                modifier = Modifier.padding(top = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                HowItWorksStep(
                                    num = "1",
                                    title = "Quick Mobile Payment",
                                    desc = "Pay via JazzCash, Easypaisa, SadaPay, or Raast using standard QR or mobile account transfer."
                                )
                                HowItWorksStep(
                                    num = "2",
                                    title = "Instant Delivery on Screen & WhatsApp",
                                    desc = "Within 60-180 seconds, your unique 25-digit activation key is displayed on screen and texted to WhatsApp."
                                )
                                HowItWorksStep(
                                    num = "3",
                                    title = "Paste & Activate",
                                    desc = "Go to Settings > Update > Activation. Enter key and click 'Activate'. All features unlock immediately."
                                )
                            }
                        }
                    }
                }

                // Verified Buyer Review Highlight
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLow)
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(SurfaceContainerHighest),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "H",
                                        fontWeight = FontWeight.Bold,
                                        color = SecondaryMint,
                                        fontSize = 14.sp
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Hamza Tariq",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryCyan
                                    )
                                    Text(
                                        text = "Gulberg, Lahore • Verified Purchase",
                                        fontSize = 11.sp,
                                        color = OnSurfaceVariant
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.VerifiedUser,
                                    contentDescription = null,
                                    tint = SecondaryMint,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "Active Key",
                                    fontSize = 11.sp,
                                    color = SecondaryMint,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Text(
                            text = "\"Paid via JazzCash and received key on WhatsApp in literally 2 minutes. Upgraded my Dell laptop from Win 11 Home without any format. Genuine MS activation!\"",
                            fontSize = 12.sp,
                            color = OnSurface,
                            lineHeight = 17.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        // Sticky Bottom Bar
        Surface(
            color = SurfaceContainerLowest.copy(alpha = 0.95f),
            shadowElevation = 16.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // WhatsApp Outline Inquiry
                Button(
                    onClick = {
                        launchWhatsApp(context, "Hi SoftBazaar, I want to inquire about ${product.title} (₨ ${product.pricePkr})")
                    },
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceContainerHigh
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Chat,
                        contentDescription = "WhatsApp",
                        tint = SecondaryMint,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "WhatsApp",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryMint
                    )
                }

                // Primary Buy Now CTA
                Button(
                    onClick = { viewModel.openCheckout(product.id) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("detail_buy_now_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(PrimaryContainer, SecondaryContainer)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingBag,
                                contentDescription = null,
                                tint = OnPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Buy Now • ₨ ${formatPkr(product.pricePkr)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CompatibilityTag(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .background(SurfaceContainerHigh, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SecondaryMint,
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = text,
            fontSize = 11.sp,
            color = OnSurface,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun BenefitRowCard(icon: ImageVector, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .padding(10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceContainerHigh),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SecondaryMint,
                modifier = Modifier.size(18.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryCyan
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = desc,
                fontSize = 11.sp,
                color = OnSurfaceVariant,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun HowItWorksStep(num: String, title: String, desc: String) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(SecondaryMint),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = num,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF00382A)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryCyan
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = desc,
                fontSize = 11.sp,
                color = OnSurfaceVariant,
                lineHeight = 15.sp
            )
        }
    }
}
