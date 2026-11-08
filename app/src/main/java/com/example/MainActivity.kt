package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.SoftBazaarBottomNav
import com.example.ui.screens.admin.AddSoftwareScreen
import com.example.ui.screens.admin.AdminInventoryScreen
import com.example.ui.screens.categories.CategoriesScreen
import com.example.ui.screens.checkout.CheckoutScreen
import com.example.ui.screens.details.ProductDetailScreen
import com.example.ui.screens.store.StoreScreen
import com.example.ui.screens.vault.VaultScreen
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryMint
import com.example.ui.theme.SoftBazaarTheme
import com.example.ui.theme.SurfaceBright
import com.example.ui.viewmodel.ScreenDestination
import com.example.ui.viewmodel.SoftBazaarViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoftBazaarTheme {
                val viewModel: SoftBazaarViewModel = viewModel()
                SoftBazaarApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun SoftBazaarApp(viewModel: SoftBazaarViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    // Handle Android system back button when in secondary screens
    BackHandler(enabled = currentScreen !is ScreenDestination.Store) {
        when (currentScreen) {
            is ScreenDestination.ProductDetail -> viewModel.navigateTo(ScreenDestination.Store)
            is ScreenDestination.Checkout -> viewModel.navigateTo(ScreenDestination.Store)
            is ScreenDestination.AddSoftware -> viewModel.navigateTo(ScreenDestination.Admin)
            is ScreenDestination.Categories -> viewModel.navigateTo(ScreenDestination.Store)
            is ScreenDestination.Vault -> viewModel.navigateTo(ScreenDestination.Store)
            is ScreenDestination.Admin -> viewModel.navigateTo(ScreenDestination.Store)
            else -> viewModel.navigateTo(ScreenDestination.Store)
        }
    }

    // Auto-clear toast after 2.5 seconds
    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            delay(2500)
            viewModel.clearToast()
        }
    }

    val showBottomNav = currentScreen is ScreenDestination.Store ||
            currentScreen is ScreenDestination.Categories ||
            currentScreen is ScreenDestination.Vault ||
            currentScreen is ScreenDestination.Admin

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomNav) {
                SoftBazaarBottomNav(
                    currentDestination = currentScreen,
                    onTabSelected = { dest -> viewModel.navigateTo(dest) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is ScreenDestination.Store -> {
                    StoreScreen(viewModel = viewModel)
                }
                is ScreenDestination.Categories -> {
                    CategoriesScreen(viewModel = viewModel)
                }
                is ScreenDestination.Vault -> {
                    VaultScreen(viewModel = viewModel)
                }
                is ScreenDestination.Admin -> {
                    AdminInventoryScreen(viewModel = viewModel)
                }
                is ScreenDestination.ProductDetail -> {
                    ProductDetailScreen(productId = screen.productId, viewModel = viewModel)
                }
                is ScreenDestination.Checkout -> {
                    CheckoutScreen(productId = screen.productId, viewModel = viewModel)
                }
                is ScreenDestination.AddSoftware -> {
                    AddSoftwareScreen(viewModel = viewModel)
                }
            }

            // Floating Toast notification
            AnimatedVisibility(
                visible = toastMessage != null,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = if (showBottomNav) 16.dp else 24.dp)
            ) {
                toastMessage?.let { msg ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(SurfaceBright)
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = SecondaryMint,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = msg,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryCyan
                            )
                        }
                    }
                }
            }
        }
    }
}
