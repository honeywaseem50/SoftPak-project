package com.example.ui.screens.checkout

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.PaymentChannel
import com.example.ui.components.SoftBazaarTopBar
import com.example.ui.components.copyToClipboard
import com.example.ui.components.launchWhatsApp
import com.example.ui.screens.store.formatPkr
import com.example.ui.screens.store.getSoftwareIcon
import com.example.ui.screens.store.getSoftwareIconColor
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
import com.example.ui.theme.TertiaryFixedDim
import com.example.ui.viewmodel.OrderSuccessData
import com.example.ui.viewmodel.ScreenDestination
import com.example.ui.viewmodel.SoftBazaarViewModel

@Composable
fun CheckoutScreen(
    productId: Long,
    viewModel: SoftBazaarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val products by viewModel.rawProducts.collectAsState()
    val product = products.find { it.id == productId } ?: products.firstOrNull()

    var fullName by remember { mutableStateOf("Hamza Ali") }
    var whatsappNumber by remember { mutableStateOf("0302 8492019") }
    var emailAddress by remember { mutableStateOf("hamza.ali@gmail.com") }
    var selectedChannel by remember { mutableStateOf(PaymentChannel.EASYPAISA) }
    var isProcessing by remember { mutableStateOf(false) }

    val orderSuccessModal by viewModel.orderSuccessModal.collectAsState()

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Product not found", color = PrimaryCyan)
        }
        return
    }

    Box(modifier = modifier.fillMaxSize().background(SurfaceDark)) {
        Column(modifier = Modifier.fillMaxSize()) {
            SoftBazaarTopBar(
                subtitle = "Instant Checkout • SoftBazaar PK",
                canGoBack = true,
                onBackClick = { viewModel.navigateBack() },
                onWhatsAppClick = {
                    launchWhatsApp(context, "Hi SoftBazaar, I need assistance with checkout for ${product.title}")
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
                    // Delivery Guarantee Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerHigh)
                            .padding(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(SecondaryMint.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Bolt,
                                    contentDescription = null,
                                    tint = SecondaryMint,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "Instant Automated Dispatch",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryCyan
                                    )
                                    Box(
                                        modifier = Modifier
                                            .background(SecondaryMint.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "~3 Mins",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SecondaryMint
                                        )
                                    }
                                }
                                Text(
                                    text = "Official OEM Key sent straight to WhatsApp & Screen",
                                    fontSize = 12.sp,
                                    color = OnSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // Order Summary Card
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
                            Text(
                                text = "ORDER ITEM",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceVariant
                            )
                            Row(
                                modifier = Modifier
                                    .background(SecondaryMint.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = null,
                                    tint = SecondaryMint,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "Verified Official",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryMint
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerHighest),
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
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        // Total Payable Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLow)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Total Payable (All-Inclusive)",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariant
                                )
                                Text(
                                    text = "Zero GST & Processing Fees",
                                    fontSize = 10.sp,
                                    color = SecondaryMint,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "₨ ${formatPkr(product.pricePkr)}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PrimaryContainer
                                )
                                Text(
                                    text = "₨ ${formatPkr(product.originalPricePkr)}",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariant,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                        }
                    }
                }

                // Delivery Details Form
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
                            Text(
                                text = "Delivery Details",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryCyan
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = null,
                                    tint = SecondaryMint,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "Encrypted Transfer",
                                    fontSize = 11.sp,
                                    color = SecondaryMint
                                )
                            }
                        }

                        // Full Name Input
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Full Name", fontSize = 12.sp, color = OnSurface, fontWeight = FontWeight.Medium)
                            OutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkout_name_input"),
                                leadingIcon = {
                                    Icon(Icons.Filled.Person, contentDescription = null, tint = OnSurfaceVariant)
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceContainerHighest,
                                    unfocusedContainerColor = SurfaceContainerHighest,
                                    focusedBorderColor = PrimaryContainer,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = PrimaryCyan,
                                    unfocusedTextColor = PrimaryCyan
                                ),
                                singleLine = true
                            )
                        }

                        // WhatsApp Input
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("WhatsApp Number", fontSize = 12.sp, color = OnSurface, fontWeight = FontWeight.Medium)
                                Text("For Immediate Key Dispatch", fontSize = 11.sp, color = SecondaryMint)
                            }
                            OutlinedTextField(
                                value = whatsappNumber,
                                onValueChange = { whatsappNumber = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkout_phone_input"),
                                leadingIcon = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 12.dp, end = 6.dp)
                                    ) {
                                        Text("🇵🇰 +92", fontSize = 12.sp, color = PrimaryCyan, fontWeight = FontWeight.Bold)
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceContainerHighest,
                                    unfocusedContainerColor = SurfaceContainerHighest,
                                    focusedBorderColor = PrimaryContainer,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = PrimaryCyan,
                                    unfocusedTextColor = PrimaryCyan
                                ),
                                singleLine = true
                            )
                        }

                        // Email Input (Optional)
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Email Address (Optional Backup)", fontSize = 12.sp, color = OnSurface, fontWeight = FontWeight.Medium)
                            OutlinedTextField(
                                value = emailAddress,
                                onValueChange = { emailAddress = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("checkout_email_input"),
                                leadingIcon = {
                                    Icon(Icons.Filled.Mail, contentDescription = null, tint = OnSurfaceVariant)
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceContainerHighest,
                                    unfocusedContainerColor = SurfaceContainerHighest,
                                    focusedBorderColor = PrimaryContainer,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = PrimaryCyan,
                                    unfocusedTextColor = PrimaryCyan
                                ),
                                singleLine = true
                            )
                        }
                    }
                }

                // Payment Method Selection
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
                            Text(
                                text = "Payment Channel",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryCyan
                            )
                            Text(
                                text = "Instant Local Verification",
                                fontSize = 11.sp,
                                color = OnSurfaceVariant
                            )
                        }

                        PaymentChannel.values().forEach { channel ->
                            PaymentOptionItem(
                                channel = channel,
                                isSelected = selectedChannel == channel,
                                onSelect = { selectedChannel = channel }
                            )
                        }
                    }
                }

                // Security & Guarantee Module
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceContainerLow)
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryContainer.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Security,
                                    contentDescription = null,
                                    tint = PrimaryContainer,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "100% Activation Guarantee",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryCyan
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Full money-back guarantee or immediate key swap if the license fails activation. Live WhatsApp technician standby in Lahore & Karachi.",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariant,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }

                // CTA Button
                item {
                    Button(
                        onClick = {
                            isProcessing = true
                            viewModel.purchaseProduct(
                                product = product,
                                customerName = fullName.ifBlank { "Valued Customer" },
                                customerWhatsApp = if (whatsappNumber.startsWith("+92")) whatsappNumber else "+92 $whatsappNumber",
                                customerEmail = emailAddress,
                                channel = selectedChannel,
                                onSuccess = {
                                    isProcessing = false
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("confirm_and_pay_button"),
                        enabled = !isProcessing,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(PrimaryContainer, SecondaryMint)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isProcessing) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = OnPrimary,
                                        strokeWidth = 2.dp
                                    )
                                    Text(
                                        text = "Initiating Payment Prompt...",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnPrimary
                                    )
                                }
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Confirm & Pay ₨ ${formatPkr(product.pricePkr)}",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnPrimary
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = OnPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = SecondaryMint,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Key and download link appear immediately after checkout.",
                            fontSize = 11.sp,
                            color = OnSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        // Order Success Modal Dialog
        orderSuccessModal?.let { successData ->
            OrderSuccessDialog(
                data = successData,
                onDismiss = {
                    viewModel.dismissOrderSuccessModal()
                    viewModel.navigateTo(ScreenDestination.Vault)
                },
                onOpenWhatsApp = {
                    launchWhatsApp(
                        context,
                        "Hi SoftBazaar, I just confirmed Order ${successData.order.orderId} for ${successData.order.productTitle}."
                    )
                }
            )
        }
    }
}

@Composable
private fun PaymentOptionItem(
    channel: PaymentChannel,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) SurfaceContainerHigh else SurfaceContainer)
            .border(
                1.dp,
                if (isSelected) PrimaryContainer.copy(alpha = 0.5f) else Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onSelect)
            .padding(12.dp)
            .testTag("payment_option_${channel.id}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Radio circle
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(if (isSelected) PrimaryContainer else SurfaceContainerHighest),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(OnPrimary)
                )
            }
        }

        // Gateway Icon Badge
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceContainerHighest),
            contentAlignment = Alignment.Center
        ) {
            when (channel) {
                PaymentChannel.RAAST -> Icon(Icons.Filled.QrCode2, contentDescription = null, tint = PrimaryCyan, modifier = Modifier.size(22.dp))
                PaymentChannel.CARD -> Icon(Icons.Filled.CreditCard, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(20.dp))
                else -> Text(channel.shortCode, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (channel == PaymentChannel.EASYPAISA) SecondaryMint else TertiaryFixedDim)
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = channel.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryCyan
                )
                Box(
                    modifier = Modifier
                        .background(SurfaceBright, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = channel.badge,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryMint
                    )
                }
            }
            Text(
                text = channel.subtext,
                fontSize = 11.sp,
                color = OnSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Icon(
            imageVector = when (channel) {
                PaymentChannel.EASYPAISA -> Icons.Filled.FlashOn
                PaymentChannel.JAZZCASH -> Icons.Filled.AccountBalanceWallet
                PaymentChannel.RAAST -> Icons.Filled.SyncAlt
                PaymentChannel.CARD -> Icons.Filled.Lock
            },
            contentDescription = null,
            tint = if (isSelected) SecondaryMint else OnSurfaceVariant,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun OrderSuccessDialog(
    data: OrderSuccessData,
    onDismiss: () -> Unit,
    onOpenWhatsApp: () -> Unit
) {
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(20.dp)),
            color = SurfaceContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, SecondaryMint.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .background(SurfaceContainerHigh, CircleShape)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = OnSurfaceVariant, modifier = Modifier.size(18.dp))
                    }
                }

                // Checkmark emblem
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(SecondaryMint.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Success",
                        tint = SecondaryMint,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .background(SecondaryMint.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Filled.Bolt, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(14.dp))
                        Text("Instant Dispatch Complete", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryMint)
                    }
                }

                Text(
                    text = "Order Confirmed!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryCyan
                )

                Text(
                    text = "Your genuine official OEM license has been generated and dispatched.",
                    fontSize = 12.sp,
                    color = OnSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    lineHeight = 16.sp
                )

                // WhatsApp Key Dispatch card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerLow)
                        .border(1.dp, SecondaryMint.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Filled.Chat, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(16.dp))
                            Text("WhatsApp Key Dispatch", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                        }
                        Text("Sent ~10s ago", fontSize = 10.sp, color = SecondaryMint, fontWeight = FontWeight.SemiBold)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Recipient: ${data.order.customerWhatsApp}", fontSize = 11.sp, color = OnSurfaceVariant)
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainerHighest, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("Automated Bot", fontSize = 9.sp, color = SecondaryMint, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Key Display Box
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerHighest)
                            .padding(10.dp)
                    ) {
                        Text("PRODUCT LICENSE KEY", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = OnSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = data.order.licenseKey,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryContainer,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { copyToClipboard(context, data.order.licenseKey, "License Key") },
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(SurfaceContainer, RoundedCornerShape(8.dp))
                                    .testTag("copy_order_key_btn")
                            ) {
                                Icon(Icons.Filled.ContentCopy, contentDescription = "Copy", tint = PrimaryCyan, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }

                // Reference details
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerHigh)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Order Reference", fontSize = 11.sp, color = OnSurfaceVariant)
                        Text(data.order.orderId, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Product", fontSize = 11.sp, color = OnSurfaceVariant)
                        Text(data.order.productTitle, fontSize = 11.sp, color = PrimaryCyan)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Paid Amount", fontSize = 11.sp, color = OnSurfaceVariant)
                        Text("₨ ${formatPkr(data.order.amountPkr)} (via ${data.order.paymentChannel.capitalize()} • 0% Fee)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryMint)
                    }
                }

                // Action Buttons
                Button(
                    onClick = onOpenWhatsApp,
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Brush.horizontalGradient(listOf(PrimaryContainer, SecondaryMint))),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Filled.Chat, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(18.dp))
                            Text("Open WhatsApp for Setup & ISO", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
                        }
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh)
                ) {
                    Text("View in My Digital Vault", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                }
            }
        }
    }
}
