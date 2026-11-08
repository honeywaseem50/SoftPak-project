package com.example.ui.screens.vault

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.LaptopWindows
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VaultKey
import com.example.ui.components.SoftBazaarTopBar
import com.example.ui.components.copyToClipboard
import com.example.ui.components.launchWhatsApp
import com.example.ui.screens.store.getSoftwareIcon
import com.example.ui.screens.store.getSoftwareIconColor
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OutlineColor
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.SecondaryMint
import com.example.ui.theme.SurfaceBright
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TertiaryFixedDim
import com.example.ui.viewmodel.SoftBazaarViewModel

@Composable
fun VaultScreen(
    viewModel: SoftBazaarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val keys by viewModel.filteredVaultKeys.collectAsState()
    val allKeysCount by viewModel.vaultKeys.collectAsState()
    val vaultFilter by viewModel.vaultFilter.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceDark)
    ) {
        SoftBazaarTopBar(
            subtitle = "My Digital Vault • 100% Genuine Licenses",
            onSearchClick = {},
            onWhatsAppClick = {
                launchWhatsApp(context, "Hi SoftBazaar, I have a question about my saved vault license keys")
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Vault Overview Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Security,
                            contentDescription = null,
                            tint = SecondaryMint,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "My Digital Vault",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryCyan,
                            letterSpacing = (-0.5).sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .background(SecondaryMint, CircleShape)
                            )
                            Text(
                                text = "${allKeysCount.size} SYNCED",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryMint
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "All your authentic lifetime licenses, instant activation keys & official downloads in one safe place.",
                    fontSize = 13.sp,
                    color = OnSurfaceVariant,
                    lineHeight = 18.sp
                )
            }

            // Interactive Filter Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    VaultFilterChip(
                        label = "All Keys (${allKeysCount.size})",
                        isSelected = vaultFilter == "all",
                        onClick = { viewModel.setVaultFilter("all") }
                    )
                    VaultFilterChip(
                        label = "Active",
                        isSelected = vaultFilter == "active",
                        onClick = { viewModel.setVaultFilter("active") }
                    )
                    VaultFilterChip(
                        label = "Need Help?",
                        icon = Icons.Filled.SupportAgent,
                        isSelected = vaultFilter == "help",
                        onClick = { viewModel.setVaultFilter("help") }
                    )
                }
            }

            // Cards List
            if (keys.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No keys matching filter",
                            color = OnSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(keys, key = { it.id }) { vaultKey ->
                    VaultKeyCardItem(vaultKey = vaultKey)
                }
            }

            // Help & Support Callout (Pakistan Dedicated Helpline)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerHigh)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Support,
                                contentDescription = null,
                                tint = SecondaryMint,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Facing Activation Hurdles?",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryCyan
                            )
                            Text(
                                text = "Instant Urdu & English remote assistance via WhatsApp",
                                fontSize = 12.sp,
                                color = OnSurfaceVariant
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Filled.Schedule, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(15.dp))
                            Text("Avg response ~3 mins", fontSize = 12.sp, color = SecondaryMint, fontWeight = FontWeight.Medium)
                        }

                        Button(
                            onClick = {
                                launchWhatsApp(context, "Hi SoftBazaar Support Team, I am facing trouble activating my license key")
                            },
                            modifier = Modifier.height(40.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SecondaryMint)
                        ) {
                            Icon(Icons.Filled.Chat, contentDescription = null, tint = Color(0xFF00382A), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("WhatsApp Helpline", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00382A))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun VaultKeyCardItem(vaultKey: VaultKey) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainer)
            .border(1.dp, SurfaceContainerHighest, RoundedCornerShape(16.dp))
            .padding(14.dp)
            .testTag("vault_card_${vaultKey.id}"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Top Row: Software Icon, Title, Verified Badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerHighest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (vaultKey.iconType) {
                            "vpn_lock" -> Icons.Filled.VpnLock
                            "cloud_download" -> Icons.Filled.CloudDownload
                            else -> Icons.Filled.LaptopWindows
                        },
                        contentDescription = null,
                        tint = when (vaultKey.iconType) {
                            "vpn_lock" -> PrimaryCyan
                            "cloud_download" -> SecondaryMint
                            else -> PrimaryContainer
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Text(
                        text = vaultKey.productTitle,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryCyan,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = vaultKey.validityTier,
                        fontSize = 11.sp,
                        color = OnSurfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(SurfaceContainerHighest, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = if (vaultKey.remainingPercent != null) Icons.Filled.HourglassTop else Icons.Filled.Verified,
                        contentDescription = null,
                        tint = if (vaultKey.remainingPercent != null) TertiaryFixedDim else SecondaryMint,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = vaultKey.remainingDaysText ?: if (vaultKey.isVerified) "Verified" else "Active",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (vaultKey.remainingPercent != null) TertiaryFixedDim else SecondaryMint
                    )
                }
            }
        }

        // Subscription Progress Bar (If applicable)
        if (vaultKey.remainingPercent != null) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subscription Health", fontSize = 11.sp, color = OnSurfaceVariant)
                    Text("92% Remaining", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryMint)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(SurfaceContainerLowest)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(vaultKey.remainingPercent)
                            .height(6.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(PrimaryContainer, SecondaryMint)
                                )
                            )
                    )
                }
            }
        }

        // Date and bind note
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(Icons.Filled.CalendarToday, contentDescription = null, tint = OutlineColor, modifier = Modifier.size(13.dp))
                Text(vaultKey.purchaseDateText, fontSize = 11.sp, color = OnSurfaceVariant)
            }
            Text(vaultKey.note, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = SecondaryMint)
        }

        // License Key Display Box
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(SurfaceContainerLowest)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = vaultKey.keyLabel.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = OutlineColor,
                    letterSpacing = 0.5.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(modifier = Modifier.size(6.dp).background(SecondaryMint, CircleShape))
                    Text(
                        text = vaultKey.syncStatusText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SecondaryMint
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = vaultKey.licenseKey,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryCyan,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        copyToClipboard(context, vaultKey.licenseKey, vaultKey.productTitle)
                    },
                    modifier = Modifier
                        .height(34.dp)
                        .testTag("copy_vault_key_${vaultKey.id}"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy",
                            tint = OnPrimaryContainer,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "Copy",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnPrimaryContainer
                        )
                    }
                }
            }
        }

        // Quick Action Resource Links
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (vaultKey.category == "active" && vaultKey.productTitle.contains("Windows", ignoreCase = true)) {
                VaultActionButton(
                    icon = Icons.Filled.Download,
                    label = "Official ISO",
                    iconColor = PrimaryCyan,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        Toast.makeText(context, "Opening Microsoft Official Windows 11 ISO portal...", Toast.LENGTH_SHORT).show()
                    }
                )
                VaultActionButton(
                    icon = Icons.Filled.Translate,
                    label = "اردو Guide",
                    iconColor = TertiaryFixedDim,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        launchWhatsApp(context, "Hi SoftBazaar, please send the Urdu activation guide for ${vaultKey.productTitle}")
                    }
                )
                VaultActionButton(
                    icon = Icons.Filled.ReceiptLong,
                    label = "Invoice PDF",
                    iconColor = SecondaryMint,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        Toast.makeText(context, "Official PDF invoice generated with SBP Raast stamp.", Toast.LENGTH_SHORT).show()
                    }
                )
            } else if (vaultKey.productTitle.contains("NordVPN", ignoreCase = true)) {
                VaultActionButton(
                    icon = Icons.Filled.AccountCircle,
                    label = "Login Details",
                    iconColor = PrimaryCyan,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        Toast.makeText(context, "Accessing NordVPN portal credentials...", Toast.LENGTH_SHORT).show()
                    }
                )
                VaultActionButton(
                    icon = Icons.Filled.Devices,
                    label = "Manage Devices",
                    iconColor = SecondaryMint,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        Toast.makeText(context, "Managing 6 active device slots.", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                VaultActionButton(
                    icon = Icons.Filled.MenuBook,
                    label = "Setup Guide",
                    iconColor = SecondaryMint,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        Toast.makeText(context, "Opening official IDM registration guide...", Toast.LENGTH_SHORT).show()
                    }
                )
                VaultActionButton(
                    icon = Icons.Filled.OpenInNew,
                    label = "Official Site",
                    iconColor = PrimaryCyan,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        Toast.makeText(context, "Opening official vendor portal...", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
private fun VaultActionButton(
    icon: ImageVector,
    label: String,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerHigh)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = iconColor, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = label, fontSize = 11.sp, color = OnSurfaceVariant, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun VaultFilterChip(
    label: String,
    icon: ImageVector? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) PrimaryContainer else SurfaceContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) OnPrimaryContainer else OnSurfaceVariant,
                    modifier = Modifier.size(15.dp)
                )
            }
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) OnPrimaryContainer else OnSurfaceVariant
            )
        }
    }
}
