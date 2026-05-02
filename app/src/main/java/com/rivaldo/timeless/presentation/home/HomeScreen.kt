package com.rivaldo.timeless.presentation.home

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import com.rivaldo.timeless.domain.usecase.TimeCanvasData

/**
 * Internal enum representing the three visual states of a dot on the Zen Canvas.
 */
private enum class DotType {
    SPENT,
    CURRENT,
    REMAINING
}

/**
 * The main home screen featuring the Zen Canvas.
 *
 * @param onDiaryClicked Callback triggered when the FAB (diary button) is tapped.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onDiaryClicked: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Permission launcher for POST_NOTIFICATIONS (Android 13+)
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            // Permission denied — show a gentle snackbar explaining
            // that notifications will not appear
        }
    }

    // Request POST_NOTIFICATIONS permission on first composition
    // for Android 13+ (API 33+)
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            val permissionResult = PermissionChecker.checkSelfPermission(
                context,
                permission
            )
            if (permissionResult != PermissionChecker.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(permission)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Timeless",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Light
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onDiaryClicked,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Tulis jurnal harian"
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        when (val currentState = state) {
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                }
            }

            is HomeUiState.Success -> {
                ZenCanvasContent(
                    canvasData = currentState.canvasData,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            is HomeUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentState.message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ZenCanvasContent(
    canvasData: TimeCanvasData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Setiap titik adalah momenmu",
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        ZenCanvasGrid(
            canvasData = canvasData,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Hiduplah dengan penuh kesadaran",
            style = MaterialTheme.typography.bodySmall,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun ZenCanvasGrid(
    canvasData: TimeCanvasData,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "currentWeekBlink")

    LazyVerticalGrid(
        columns = GridCells.Fixed(52),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(1.5.dp),
        verticalArrangement = Arrangement.spacedBy(1.5.dp),
        userScrollEnabled = true,
        modifier = modifier
    ) {
        itemsIndexed(
            items = List(canvasData.totalWeeks) { it },
            key = { index, _ -> index }
        ) { index, _ ->
            val dotType = when {
                index < canvasData.spentWeeks -> DotType.SPENT
                index == canvasData.currentWeekIndex -> DotType.CURRENT
                else -> DotType.REMAINING
            }

            ZenDot(
                type = dotType,
                infiniteTransition = infiniteTransition
            )
        }
    }
}

@Composable
private fun ZenDot(
    type: DotType,
    infiniteTransition: InfiniteTransition
) {
    val spentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
    val currentBaseColor = MaterialTheme.colorScheme.primary
    val remainingColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.40f)

    when (type) {
        DotType.SPENT -> {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(spentColor)
            )
        }

        DotType.CURRENT -> {
            val blinkAlpha by infiniteTransition.animateFloat(
                initialValue = 0.6f,
                targetValue = 1.0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "currentDotBlink"
            )

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(currentBaseColor)
                    .graphicsLayer { alpha = blinkAlpha }
            )
        }

        DotType.REMAINING -> {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(remainingColor)
            )
        }
    }
}
