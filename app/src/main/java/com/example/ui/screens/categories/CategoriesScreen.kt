package com.example.ui.screens.categories

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material.icons.filled.Window
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.InitialCatalogData
import com.example.ui.components.SoftBazaarTopBar
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryMint
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceDark
import com.example.ui.viewmodel.ScreenDestination
import com.example.ui.viewmodel.SoftBazaarViewModel

@Composable
fun CategoriesScreen(
    viewModel: SoftBazaarViewModel,
    modifier: Modifier = Modifier
) {
    val products by viewModel.rawProducts.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceDark)
    ) {
        SoftBazaarTopBar(
            subtitle = "Software Categories • Genuine Licenses",
            onSearchClick = {},
            onWhatsAppClick = {}
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Explore Software Categories",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryCyan,
                    letterSpacing = (-0.5).sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Choose from verified global retail licenses and developer subscriptions available in Pakistan.",
                    fontSize = 13.sp,
                    color = OnSurfaceVariant,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            items(InitialCatalogData.categories) { category ->
                val matchingCount = if (category.id == "all") {
                    products.size
                } else {
                    products.count { it.category.equals(category.id, ignoreCase = true) }
                }

                CategoryCardItem(
                    title = category.title,
                    description = category.description,
                    count = matchingCount,
                    icon = when (category.iconName) {
                        "window" -> Icons.Filled.Window
                        "vpn_lock" -> Icons.Filled.VpnLock
                        "shield_lock" -> Icons.Filled.Security
                        "terminal" -> Icons.Filled.Terminal
                        "palette" -> Icons.Filled.Palette
                        else -> Icons.Filled.GridView
                    },
                    onClick = {
                        viewModel.selectCategory(category.id)
                        viewModel.navigateTo(ScreenDestination.Store)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun CategoryCardItem(
    title: String,
    description: String,
    count: Int,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainer)
            .border(1.dp, SurfaceContainerHighest, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
            .testTag("category_item_$title"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerHighest),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SecondaryMint,
                modifier = Modifier.size(26.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryCyan
                )
                Box(
                    modifier = Modifier
                        .background(SurfaceContainerHigh, RoundedCornerShape(10.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "$count SKUs",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryMint
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = OnSurfaceVariant
            )
        }

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = OnSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
}
