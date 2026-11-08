package com.example.ui.screens.admin

import android.widget.Toast
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddModerator
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LaptopMac
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SoftBazaarTopBar
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
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TertiaryFixedDim
import com.example.ui.viewmodel.ScreenDestination
import com.example.ui.viewmodel.SoftBazaarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSoftwareScreen(
    viewModel: SoftBazaarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("Microsoft Office 2024 Pro Plus") }
    var selectedCategory by remember { mutableStateOf("office") }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    var sellingPriceText by remember { mutableStateOf("2500") }
    var originalPriceText by remember { mutableStateOf("4999") }

    // Platform checkboxes
    var winChecked by remember { mutableStateOf(true) }
    var macChecked by remember { mutableStateOf(false) }
    var androidChecked by remember { mutableStateOf(false) }
    var iosChecked by remember { mutableStateOf(false) }

    var selectedTier by remember { mutableStateOf("Lifetime") }

    var keysText by remember {
        mutableStateOf(
            "W9X2K-4NM8Q-BRVPT-8K3G1-9PXRT\n7NBT2-H4F8G-Q8P2M-3KD8W-6VTY4\nBK89Q-N2MV8-99TXR-74P8Q-1XCVB"
        )
    }

    var autoDispatchChecked by remember { mutableStateOf(true) }
    var lowStockAlertChecked by remember { mutableStateOf(true) }

    val detectedKeys = keysText.split("\n").filter { it.trim().length > 5 }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceDark)
    ) {
        SoftBazaarTopBar(
            subtitle = "Catalog Management • Add Software",
            canGoBack = true,
            onBackClick = { viewModel.navigateTo(ScreenDestination.Admin) }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Banner
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AddModerator,
                        contentDescription = null,
                        tint = SecondaryMint,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Add Digital Software",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryCyan,
                        letterSpacing = (-0.5).sp
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Create new catalog item, set PKR rates, and inject batch activation keys.",
                    fontSize = 12.sp,
                    color = OnSurfaceVariant
                )
            }

            // Status Progress Card
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerHigh)
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Key, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(22.dp))
                        }
                        Column {
                            Text("Catalog Sync Active", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                            Text("Instant WhatsApp dispatch pipeline", fontSize = 11.sp, color = OnSurfaceVariant)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(SecondaryMint.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(6.dp).background(SecondaryMint, CircleShape))
                            Text("READY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SecondaryMint)
                        }
                    }
                }
            }

            // 1. Basic Details Module
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
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Filled.Edit, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(18.dp))
                            Text("1. Basic Details", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                        }
                        Box(
                            modifier = Modifier.background(SurfaceContainerHighest, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("Required", fontSize = 10.sp, color = SecondaryMint, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Software Title
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Software Title", fontSize = 12.sp, color = OnSurface)
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            modifier = Modifier.fillMaxWidth().testTag("add_software_title_input"),
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

                    // Category Selector
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Category", fontSize = 12.sp, color = OnSurface)
                        val categoryOptions = listOf(
                            "office" to "Office & Productivity",
                            "vpn" to "VPN & Privacy Tools",
                            "security" to "Antivirus & Security Suite",
                            "windows" to "Operating System (Windows / Server)",
                            "dev" to "Developer & Cloud Tools",
                            "creative" to "Graphic & Video Editing"
                        )

                        ExposedDropdownMenuBox(
                            expanded = categoryDropdownExpanded,
                            onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded }
                        ) {
                            OutlinedTextField(
                                value = categoryOptions.find { it.first == selectedCategory }?.second ?: "Office & Productivity",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceContainerHighest,
                                    unfocusedContainerColor = SurfaceContainerHighest,
                                    focusedBorderColor = PrimaryContainer,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = PrimaryCyan,
                                    unfocusedTextColor = PrimaryCyan
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = categoryDropdownExpanded,
                                onDismissRequest = { categoryDropdownExpanded = false },
                                modifier = Modifier.background(SurfaceContainer)
                            ) {
                                categoryOptions.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt.second, color = PrimaryCyan, fontSize = 13.sp) },
                                        onClick = {
                                            selectedCategory = opt.first
                                            categoryDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Pricing Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Selling Price (PKR)", fontSize = 12.sp, color = OnSurface)
                            OutlinedTextField(
                                value = sellingPriceText,
                                onValueChange = { sellingPriceText = it.filter { ch -> ch.isDigit() } },
                                prefix = { Text("₨ ", color = SecondaryMint, fontWeight = FontWeight.Bold) },
                                modifier = Modifier.fillMaxWidth().testTag("add_software_sell_price"),
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceContainerHighest,
                                    unfocusedContainerColor = SurfaceContainerHighest,
                                    focusedBorderColor = PrimaryContainer,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = PrimaryCyan,
                                    unfocusedTextColor = PrimaryCyan
                                )
                            )
                            Text("Final checkout rate", fontSize = 10.sp, color = SecondaryMint)
                        }

                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Original Price", fontSize = 12.sp, color = OnSurfaceVariant)
                            OutlinedTextField(
                                value = originalPriceText,
                                onValueChange = { originalPriceText = it.filter { ch -> ch.isDigit() } },
                                prefix = { Text("₨ ", color = OnSurfaceVariant, fontWeight = FontWeight.Bold) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceContainerHighest,
                                    unfocusedContainerColor = SurfaceContainerHighest,
                                    focusedBorderColor = PrimaryContainer,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = OnSurfaceVariant,
                                    unfocusedTextColor = OnSurfaceVariant
                                )
                            )
                            Text("Strike-through badge", fontSize = 10.sp, color = OnSurfaceVariant)
                        }
                    }
                }
            }

            // 2. Platform & Validity Module
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainer)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Filled.Devices, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(18.dp))
                        Text("2. Platform & Validity", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                    }

                    Text("Supported Platforms", fontSize = 12.sp, color = OnSurface)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PlatformCheckbox(label = "Windows", icon = Icons.Filled.DesktopWindows, checked = winChecked, onCheckedChange = { winChecked = it }, modifier = Modifier.weight(1f))
                        PlatformCheckbox(label = "macOS", icon = Icons.Filled.LaptopMac, checked = macChecked, onCheckedChange = { macChecked = it }, modifier = Modifier.weight(1f))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PlatformCheckbox(label = "Android", icon = Icons.Filled.Smartphone, checked = androidChecked, onCheckedChange = { androidChecked = it }, modifier = Modifier.weight(1f))
                        PlatformCheckbox(label = "iOS", icon = Icons.Filled.PhoneIphone, checked = iosChecked, onCheckedChange = { iosChecked = it }, modifier = Modifier.weight(1f))
                    }

                    Text("License Duration Tier", fontSize = 12.sp, color = OnSurface, modifier = Modifier.padding(top = 4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHighest)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("Lifetime" to "Permanent", "1 Year" to "365 Days", "1 Month" to "Recurring").forEach { (tier, desc) ->
                            val isSel = selectedTier == tier
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) SurfaceContainerHigh else Color.Transparent)
                                    .clickable { selectedTier = tier }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = tier,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSel) SecondaryMint else OnSurfaceVariant
                                    )
                                    Text(
                                        text = desc,
                                        fontSize = 10.sp,
                                        color = if (isSel) SecondaryMint.copy(alpha = 0.8f) else OnSurfaceVariant.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 3. Batch Key Importer
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
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Filled.Inventory2, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(18.dp))
                            Text("3. Batch Key Importer", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                        }

                        Box(
                            modifier = Modifier
                                .background(SecondaryMint.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "${detectedKeys.size} keys detected",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryMint
                            )
                        }
                    }

                    OutlinedTextField(
                        value = keysText,
                        onValueChange = { keysText = it },
                        modifier = Modifier.fillMaxWidth().height(120.dp).testTag("add_keys_textarea"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceContainerHighest,
                            unfocusedContainerColor = SurfaceContainerHighest,
                            focusedBorderColor = PrimaryContainer,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = PrimaryCyan,
                            unfocusedTextColor = PrimaryCyan
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("One alphanumeric code per line", fontSize = 11.sp, color = OnSurfaceVariant)
                        Text(
                            text = "Clear Keys",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryMint,
                            modifier = Modifier.clickable { keysText = "" }
                        )
                    }

                    // CSV / TXT alternative button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHighest)
                            .clickable {
                                keysText = "OFFICE24-PK91-8842-M91B\nOFFICE24-PK91-8843-K82C\nOFFICE24-PK91-8844-Z33X\nOFFICE24-PK91-8845-Q19P"
                                Toast.makeText(context, "Loaded batch sample keys into importer.", Toast.LENGTH_SHORT).show()
                            }
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Filled.UploadFile, contentDescription = null, tint = PrimaryContainer, modifier = Modifier.size(20.dp))
                            Column {
                                Text("Or Upload CSV / TXT File", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                                Text("Automated regex parsing", fontSize = 10.sp, color = OnSurfaceVariant)
                            }
                        }
                        Icon(Icons.Filled.AddCircle, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(18.dp))
                    }
                }
            }

            // 4. Automation & Alerts
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainer)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Filled.Tune, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(18.dp))
                        Text("4. Automation & Alerts", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                    }

                    // Toggle 1: Auto Dispatch
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHighest)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Filled.Bolt, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(15.dp))
                                Text("Instant Auto-Dispatch", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                            }
                            Text(
                                text = "Release next available key via WhatsApp & Email immediately after payment confirmation.",
                                fontSize = 10.sp,
                                color = OnSurfaceVariant
                            )
                        }
                        Switch(
                            checked = autoDispatchChecked,
                            onCheckedChange = { autoDispatchChecked = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF00382A),
                                checkedTrackColor = SecondaryMint
                            )
                        )
                    }

                    // Toggle 2: Low Inventory Alert
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHighest)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Filled.NotificationsActive, contentDescription = null, tint = TertiaryFixedDim, modifier = Modifier.size(15.dp))
                                Text("Low Inventory Warning", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                            }
                            Text(
                                text = "Send push & SMS notification to store administrator when key balance falls below 5.",
                                fontSize = 10.sp,
                                color = OnSurfaceVariant
                            )
                        }
                        Switch(
                            checked = lowStockAlertChecked,
                            onCheckedChange = { lowStockAlertChecked = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF00382A),
                                checkedTrackColor = SecondaryMint
                            )
                        )
                    }
                }
            }

            // Trust guarantee
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerHigh)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Filled.VerifiedUser, contentDescription = null, tint = SecondaryMint, modifier = Modifier.size(26.dp))
                    Column {
                        Text("100% Genuine Activation Guarantee", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                        Text("Keys will be linked to PK payment gateways (Easypaisa, JazzCash, Raast, SadaPay).", fontSize = 10.sp, color = OnSurfaceVariant)
                    }
                }
            }

            // Action Buttons
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            val sellPrice = sellingPriceText.toIntOrNull() ?: 2500
                            val strikePrice = originalPriceText.toIntOrNull() ?: 4999
                            val platformList = mutableListOf<String>()
                            if (winChecked) platformList.add("Windows")
                            if (macChecked) platformList.add("macOS")
                            if (androidChecked) platformList.add("Android")
                            if (iosChecked) platformList.add("iOS")
                            val platformsStr = platformList.ifEmpty { listOf("Windows") }.joinToString(", ")

                            viewModel.addNewSoftware(
                                title = title.ifBlank { "New Software License" },
                                category = selectedCategory,
                                sellPrice = sellPrice,
                                strikePrice = strikePrice,
                                platforms = platformsStr,
                                tier = selectedTier,
                                keys = detectedKeys
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("publish_software_btn"),
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
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Filled.Publish, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(20.dp))
                                Text("Publish Software to Store", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                Toast.makeText(context, "Draft saved in admin repository.", Toast.LENGTH_SHORT).show()
                                viewModel.navigateTo(ScreenDestination.Admin)
                            },
                            modifier = Modifier.weight(1f).height(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh)
                        ) {
                            Icon(Icons.Filled.Save, contentDescription = null, tint = OnSurface, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Save Draft", fontSize = 12.sp, color = OnSurface)
                        }

                        Button(
                            onClick = { viewModel.navigateTo(ScreenDestination.Admin) },
                            modifier = Modifier.weight(1f).height(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerLowest)
                        ) {
                            Text("Cancel", fontSize = 12.sp, color = OnSurfaceVariant)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun PlatformCheckbox(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerHighest)
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = SecondaryMint,
                checkmarkColor = Color(0xFF00382A),
                uncheckedColor = OnSurfaceVariant
            )
        )
        Icon(icon, contentDescription = null, tint = if (checked) SecondaryMint else OnSurfaceVariant, modifier = Modifier.size(16.dp))
        Text(label, fontSize = 11.sp, color = if (checked) PrimaryCyan else OnSurfaceVariant, fontWeight = FontWeight.Medium)
    }
}
