package com.rivaldo.timeless.presentation.home

import android.Manifest
import android.net.Uri
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rivaldo.timeless.domain.model.DailyLog
import com.rivaldo.timeless.domain.usecase.TimeCanvasData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
    val selectedWeekState by viewModel.selectedWeekState.collectAsState()
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

    // Reflection bottom sheet
    if (selectedWeekState.isVisible) {
        ReflectionBottomSheet(
            selectedWeek = selectedWeekState,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismiss = { viewModel.dismissReflection() }
        )
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
                    onDotClicked = { weekIndex -> viewModel.onDotClicked(weekIndex) },
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

// ──────────────────────────────────────────────────────────────
// Zen Canvas Content
// ──────────────────────────────────────────────────────────────

@Composable
private fun ZenCanvasContent(
    canvasData: TimeCanvasData,
    onDotClicked: (Int) -> Unit,
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
            onDotClicked = onDotClicked,
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

// ──────────────────────────────────────────────────────────────
// Zen Canvas Grid
// ──────────────────────────────────────────────────────────────

@Composable
private fun ZenCanvasGrid(
    canvasData: TimeCanvasData,
    onDotClicked: (Int) -> Unit,
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
                infiniteTransition = infiniteTransition,
                onClick = if (dotType == DotType.SPENT) {
                    { onDotClicked(index) }
                } else null
            )
        }
    }
}

// ──────────────────────────────────────────────────────────────
// Zen Dot
// ──────────────────────────────────────────────────────────────

@Composable
private fun ZenDot(
    type: DotType,
    infiniteTransition: InfiniteTransition,
    onClick: (() -> Unit)? = null
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
                    .then(
                        if (onClick != null) {
                            Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null, // no ripple for minimal feel
                                onClick = onClick
                            )
                        } else Modifier
                    )
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

// ──────────────────────────────────────────────────────────────
// Reflection Bottom Sheet
// ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReflectionBottomSheet(
    selectedWeek: SelectedWeekState,
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            // ── Header ──
            Text(
                text = "Refleksi Perjalanan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Week range header
            val weekRangeText = formatWeekRange(selectedWeek.weekStartDate, selectedWeek.weekEndDate)
            Text(
                text = weekRangeText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(16.dp))

            // ── Content ──
            if (selectedWeek.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(28.dp)
                    )
                }
            } else if (selectedWeek.logs.isEmpty()) {
                // Empty state — gentle message
                Text(
                    text = "Tidak ada catatan untuk minggu ini...\nSetiap momen adalah kanvas kosong yang menantimu.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                )
            } else {
                // Journal entries list
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    selectedWeek.logs.forEachIndexed { index, log ->
                        DailyLogCard(log = log)
                        if (index < selectedWeek.logs.lastIndex) {
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

// ──────────────────────────────────────────────────────────────
// Daily Log Card (inside Bottom Sheet)
// ──────────────────────────────────────────────────────────────

@Composable
private fun DailyLogCard(log: DailyLog) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Date header
        val displayDate = formatDateDisplay(log.date)
        Text(
            text = displayDate,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Journal text
        Text(
            text = log.content,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            lineHeight = 22.sp,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis
        )

        // Photo attachments
        if (log.mediaAttachments.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            log.mediaAttachments.forEach { attachment ->
                // Render using Coil with crossfade
                // Content URIs with persistable permission are safe
                val imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(Uri.parse(attachment.uri))
                    .crossfade(600)
                    .build()

                AsyncImage(
                    model = imageRequest,
                    contentDescription = "Foto: ${attachment.fileName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            RoundedCornerShape(12.dp)
                        )
                )

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

// ──────────────────────────────────────────────────────────────
// Date Formatting Helpers
// ──────────────────────────────────────────────────────────────

/**
 * Format a YYYY-MM-DD date string to a readable Indonesian format.
 * Example: "12 Januari 2025"
 */
private fun formatDateDisplay(dateStr: String): String {
    return try {
        val date = LocalDate.parse(dateStr)
        val months = arrayOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )
        "${date.dayOfMonth} ${months[date.monthValue - 1]} ${date.year}"
    } catch (e: Exception) {
        dateStr // fallback to raw string
    }
}

/**
 * Format a week range (Monday - Sunday) into a readable string.
 * Example: "12 - 18 Januari 2025"
 * If both dates are in the same month: "12 - 18 Januari 2025"
 * If different months: "30 Januari - 5 Februari 2025"
 */
private fun formatWeekRange(startDate: String, endDate: String): String {
    return try {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        val months = arrayOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )

        if (start.month == end.month && start.year == end.year) {
            "${start.dayOfMonth} - ${end.dayOfMonth} ${months[start.monthValue - 1]} ${start.year}"
        } else if (start.year == end.year) {
            "${start.dayOfMonth} ${months[start.monthValue - 1]} - ${end.dayOfMonth} ${months[end.monthValue - 1]} ${end.year}"
        } else {
            "${start.dayOfMonth} ${months[start.monthValue - 1]} ${start.year} - ${end.dayOfMonth} ${months[end.monthValue - 1]} ${end.year}"
        }
    } catch (e: Exception) {
        "$startDate — $endDate"
    }
}
