package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.viewmodel.ScreenDestination

@Composable
fun SoftBazaarTopBar(
    subtitle: String,
    canGoBack: Boolean = false,
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onWhatsAppClick: () -> Unit = {}
) {
    Surface(
        color = SurfaceContainerLowest.copy(alpha = 0.95f),
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                if (canGoBack) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(40.dp)
                            .background(SurfaceContainer, CircleShape)
                            .testTag("top_bar_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Brand Emblem
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(SecondaryMint.copy(alpha = 0.25f), SurfaceContainerHighest)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(1.dp, SecondaryMint.copy(alpha = 0.35f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Shield,
                        contentDescription = "SoftBazaar Logo",
                        tint = SecondaryMint,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "SoftBazaar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryCyan,
                            letterSpacing = (-0.3).sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .background(SecondaryMint.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "PK",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = SecondaryMint
                            )
                        }
                    }
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = OnSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Right Action Controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier
                        .size(38.dp)
                        .background(SurfaceContainer, CircleShape)
                        .testTag("top_bar_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Instant WhatsApp Support Pill
                Row(
                    modifier = Modifier
                        .height(38.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceContainerHigh)
                        .clickable { onWhatsAppClick() }
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Chat,
                        contentDescription = "WhatsApp Instant",
                        tint = SecondaryMint,
                        modifier = Modifier.size(17.dp)
                    )
                    Text(
                        text = "Instant",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryMint
                    )
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(SecondaryMint, CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun SoftBazaarBottomNav(
    currentDestination: ScreenDestination,
    onTabSelected: (ScreenDestination) -> Unit
) {
    Surface(
        color = SurfaceContainerLowest.copy(alpha = 0.96f),
        shadowElevation = 16.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Filled.Storefront,
                label = "Store",
                isSelected = currentDestination is ScreenDestination.Store,
                onClick = { onTabSelected(ScreenDestination.Store) },
                testTag = "nav_tab_store"
            )
            BottomNavItem(
                icon = Icons.Filled.GridView,
                label = "Categories",
                isSelected = currentDestination is ScreenDestination.Categories,
                onClick = { onTabSelected(ScreenDestination.Categories) },
                testTag = "nav_tab_categories"
            )
            BottomNavItem(
                icon = Icons.Filled.Key,
                label = "My Keys",
                isSelected = currentDestination is ScreenDestination.Vault,
                onClick = { onTabSelected(ScreenDestination.Vault) },
                testTag = "nav_tab_vault"
            )
            BottomNavItem(
                icon = Icons.Filled.AdminPanelSettings,
                label = "Admin",
                isSelected = currentDestination is ScreenDestination.Admin || currentDestination is ScreenDestination.AddSoftware,
                onClick = { onTabSelected(ScreenDestination.Admin) },
                testTag = "nav_tab_admin"
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val activeColor = SecondaryMint
    val inactiveColor = OnSurfaceVariant

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) activeColor else inactiveColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) activeColor else inactiveColor
        )
    }
}

@Composable
fun HighTrustGuaranteeBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(SurfaceContainerHigh, SurfaceContainer, SurfaceContainerHigh)
                )
            )
            .border(1.dp, SecondaryMint.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Bolt,
                    contentDescription = null,
                    tint = SecondaryMint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Instant License Delivery",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryCyan
                    )
                    Box(
                        modifier = Modifier
                            .background(SecondaryMint.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "60 SECS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryMint
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Official setup keys & activation steps straight to your WhatsApp & Email within 1 minute of payment confirmation.",
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun LocalizedPaymentTrustSection() {
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Payments,
                    contentDescription = null,
                    tint = SecondaryMint,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Trusted Payment Methods We Accept",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryCyan
                )
            }

            Box(
                modifier = Modifier
                    .background(SurfaceBright, RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "0% Fee",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryMint
                )
            }
        }

        Text(
            text = "Instant automated verification via Pakistan's primary digital payment networks:",
            fontSize = 12.sp,
            color = OnSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PaymentMethodChip(shortCode = "JC", name = "JazzCash", subtext = "Instant OTP", modifier = Modifier.weight(1f))
            PaymentMethodChip(shortCode = "EP", name = "Easypaisa", subtext = "Auto Verified", modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PaymentMethodChip(shortCode = "RT", name = "Raast Direct", subtext = "State Bank 0%", modifier = Modifier.weight(1f))
            PaymentMethodChip(shortCode = "SP", name = "SadaPay/Naya", subtext = "Debit Card", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun PaymentMethodChip(
    shortCode: String,
    name: String,
    subtext: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerHigh)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceContainerHighest),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = shortCode,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SecondaryMint
            )
        }
        Column {
            Text(
                text = name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryCyan
            )
            Text(
                text = subtext,
                fontSize = 10.sp,
                color = OnSurfaceVariant
            )
        }
    }
}

@Composable
fun RealtimeSupportCard(
    onOpenWhatsApp: () -> Unit
) {
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
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(SurfaceBright),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.SupportAgent,
                    contentDescription = null,
                    tint = SecondaryMint,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(
                    text = "Need Help Activating?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryCyan
                )
                Text(
                    text = "Lahore & Karachi Agents Online",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = SecondaryMint
                )
            }
        }

        Text(
            text = "Having trouble with Microsoft or Antivirus installation? We guide you step-by-step through remote screen or audio call.",
            fontSize = 12.sp,
            color = OnSurfaceVariant,
            lineHeight = 16.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerHighest)
                .clickable { onOpenWhatsApp() }
                .padding(vertical = 12.dp, horizontal = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Chat,
                contentDescription = null,
                tint = SecondaryMint,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Chat with Local Technical Support on WhatsApp",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SecondaryMint
            )
        }
    }
}

fun copyToClipboard(context: Context, text: String, label: String = "License Key") {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard?.setPrimaryClip(clip)
    Toast.makeText(context, "$label copied to clipboard!", Toast.LENGTH_SHORT).show()
}

fun launchWhatsApp(context: Context, message: String = "Hi SoftBazaar, I need support with my software license") {
    try {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=923000000000&text=${Uri.encode(message)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Opening WhatsApp Support...", Toast.LENGTH_SHORT).show()
    }
}
