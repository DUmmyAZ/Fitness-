package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BodyMetrics
import com.example.data.model.WeightPoint
import com.example.ui.theme.GreenSuccess
import com.example.ui.theme.GreenSuccessLight
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceBorderSubtle
import com.example.ui.theme.SurfaceWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZoZoOrange
import com.example.ui.theme.ZoZoOrangeSubtle
import com.example.ui.theme.ZoZoPurple
import com.example.ui.theme.ZoZoPurpleContainer
import com.example.ui.theme.ZoZoPurpleLight
import com.example.ui.theme.ZoZoPurpleSubtle
import java.util.Locale

@Composable
fun BodyMetricsSection(
    metrics: BodyMetrics,
    weightHistory: List<WeightPoint>,
    onToggleUnit: () -> Unit,
    onOpenLogDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPointIndex by remember { mutableStateOf<Int?>(weightHistory.lastIndex) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Body Metrics & Composition",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )
                Text(
                    text = "Weight progression & anthropometric data",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }

            // Unit toggle button (kg <-> lbs)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SurfaceBorderSubtle,
                border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onToggleUnit() }
                    .testTag("toggle_weight_unit_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Toggle unit",
                        tint = ZoZoPurple,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (metrics.useLbs) "LBS" else "KG",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = ZoZoPurple
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Weight Trend Graph Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
                .testTag("weight_trend_card")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ShowChart,
                                contentDescription = null,
                                tint = ZoZoPurple,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Weight Trend (Last 30 Days)",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TextPrimary
                            )
                        }

                        val currentDisplayWeight = if (metrics.useLbs) {
                            String.format(Locale.US, "%.1f lbs", metrics.currentWeightKg * 2.20462f)
                        } else {
                            String.format(Locale.US, "%.1f kg", metrics.currentWeightKg)
                        }

                        Text(
                            text = "Current: $currentDisplayWeight • Down 2.3 kg total",
                            style = MaterialTheme.typography.bodySmall,
                            color = GreenSuccess
                        )
                    }

                    // Log New Weight Button
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = ZoZoPurple,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onOpenLogDialog() }
                            .testTag("log_weight_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Log",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Custom Line Graph Canvas
                WeightTrendLineChart(
                    weightPoints = weightHistory,
                    useLbs = metrics.useLbs,
                    selectedIndex = selectedPointIndex,
                    onSelectIndex = { selectedPointIndex = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                // Tooltip indicator for selected point
                selectedPointIndex?.let { idx ->
                    if (idx in weightHistory.indices) {
                        val pt = weightHistory[idx]
                        val displayVal = if (metrics.useLbs) {
                            String.format(Locale.US, "%.1f lbs", pt.weightKg * 2.20462f)
                        } else {
                            String.format(Locale.US, "%.1f kg", pt.weightKg)
                        }
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = ZoZoPurpleSubtle,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 8.dp)
                        ) {
                            Text(
                                text = "Point: ${pt.dateLabel} → $displayVal",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = ZoZoPurple,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Metric Preview Chips (Body Weight, Body Fat %, Muscle Mass %, Waist, Chest)
        Text(
            text = "BODY COMPOSITION BREAKDOWN",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Grid of 5 Metric Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Chip 1: Body Weight
            MetricPreviewCard(
                label = "Body Weight",
                value = if (metrics.useLbs) {
                    String.format(Locale.US, "%.1f", metrics.currentWeightKg * 2.20462f)
                } else {
                    String.format(Locale.US, "%.1f", metrics.currentWeightKg)
                },
                unit = if (metrics.useLbs) "lbs" else "kg",
                delta = "-2.3 kg",
                isPositive = true,
                badgeBg = GreenSuccessLight,
                badgeColor = GreenSuccess,
                modifier = Modifier.weight(1f),
                onClick = onOpenLogDialog,
                testTag = "chip_body_weight"
            )

            // Chip 2: Body Fat %
            MetricPreviewCard(
                label = "Body Fat",
                value = String.format(Locale.US, "%.1f", metrics.bodyFatPercentage),
                unit = "%",
                delta = "-0.6%",
                isPositive = true,
                badgeBg = GreenSuccessLight,
                badgeColor = GreenSuccess,
                modifier = Modifier.weight(1f),
                onClick = onOpenLogDialog,
                testTag = "chip_body_fat"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Chip 3: Muscle Mass %
            MetricPreviewCard(
                label = "Muscle Mass",
                value = String.format(Locale.US, "%.1f", metrics.muscleMassPercentage),
                unit = "%",
                delta = "+0.8%",
                isPositive = true,
                badgeBg = ZoZoPurpleSubtle,
                badgeColor = ZoZoPurple,
                modifier = Modifier.weight(1f),
                onClick = onOpenLogDialog,
                testTag = "chip_muscle_mass"
            )

            // Chip 4: Waist
            MetricPreviewCard(
                label = "Waist Circ.",
                value = String.format(Locale.US, "%.1f", metrics.waistInches),
                unit = "in",
                delta = "-0.5 in",
                isPositive = true,
                badgeBg = ZoZoOrangeSubtle,
                badgeColor = ZoZoOrange,
                modifier = Modifier.weight(1f),
                onClick = onOpenLogDialog,
                testTag = "chip_waist"
            )

            // Chip 5: Chest
            MetricPreviewCard(
                label = "Chest Circ.",
                value = String.format(Locale.US, "%.1f", metrics.chestInches),
                unit = "in",
                delta = "+0.4 in",
                isPositive = true,
                badgeBg = ZoZoPurpleSubtle,
                badgeColor = ZoZoPurple,
                modifier = Modifier.weight(1f),
                onClick = onOpenLogDialog,
                testTag = "chip_chest"
            )
        }
    }
}

@Composable
private fun MetricPreviewCard(
    label: String,
    value: String,
    unit: String,
    delta: String,
    isPositive: Boolean,
    badgeBg: Color,
    badgeColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    maxLines = 1
                )

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = badgeBg
                ) {
                    Text(
                        text = delta,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = badgeColor,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = unit,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp
                    ),
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun WeightTrendLineChart(
    weightPoints: List<WeightPoint>,
    useLbs: Boolean,
    selectedIndex: Int?,
    onSelectIndex: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (weightPoints.isEmpty()) return

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(weightPoints) {
                    detectTapGestures { tapOffset ->
                        val count = weightPoints.size
                        if (count > 1) {
                            val spacing = size.width / (count - 1)
                            val idx = ((tapOffset.x + spacing / 2f) / spacing).toInt().coerceIn(0, count - 1)
                            onSelectIndex(idx)
                        }
                    }
                }
        ) {
            val chartHeight = size.height - 24.dp.toPx()
            val chartWidth = size.width

            val values = weightPoints.map {
                if (useLbs) it.weightKg * 2.20462f else it.weightKg
            }

            val minVal = (values.minOrNull() ?: 70f) - 1.0f
            val maxVal = (values.maxOrNull() ?: 80f) + 1.0f
            val valRange = (maxVal - minVal).coerceAtLeast(1.0f)

            val count = values.size
            val xStep = chartWidth / (count - 1).coerceAtLeast(1)

            // Calculate coordinates
            val points = values.mapIndexed { index, v ->
                val x = index * xStep
                val normalizedY = (v - minVal) / valRange
                val y = chartHeight * (1f - normalizedY)
                Offset(x, y)
            }

            // Draw horizontal subtle grid lines (3 lines)
            for (i in 0..2) {
                val gridY = chartHeight * (i / 2f)
                drawLine(
                    color = Color(0xFFF1F2F6),
                    start = Offset(0f, gridY),
                    end = Offset(chartWidth, gridY),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Build smooth Bezier path
            val path = Path()
            val fillPath = Path()

            if (points.isNotEmpty()) {
                path.moveTo(points.first().x, points.first().y)
                fillPath.moveTo(points.first().x, chartHeight)
                fillPath.lineTo(points.first().x, points.first().y)

                for (i in 0 until points.size - 1) {
                    val p0 = points[i]
                    val p1 = points[i + 1]
                    val controlX1 = (p0.x + p1.x) / 2f
                    val controlY1 = p0.y
                    val controlX2 = (p0.x + p1.x) / 2f
                    val controlY2 = p1.y

                    path.cubicTo(controlX1, controlY1, controlX2, controlY2, p1.x, p1.y)
                    fillPath.cubicTo(controlX1, controlY1, controlX2, controlY2, p1.x, p1.y)
                }

                fillPath.lineTo(points.last().x, chartHeight)
                fillPath.close()

                // Draw gradient fill below path
                val fillBrush = Brush.verticalGradient(
                    colors = listOf(
                        ZoZoPurple.copy(alpha = 0.28f),
                        ZoZoPurpleLight.copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    startY = 0f,
                    endY = chartHeight
                )
                drawPath(path = fillPath, brush = fillBrush)

                // Draw main curve line
                val strokeBrush = Brush.horizontalGradient(
                    colors = listOf(ZoZoPurple, ZoZoOrange)
                )
                drawPath(
                    path = path,
                    brush = strokeBrush,
                    style = Stroke(width = 3.5.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw dots on each point
                points.forEachIndexed { idx, point ->
                    val isSelected = idx == selectedIndex

                    // Outer halo
                    if (isSelected) {
                        drawCircle(
                            color = ZoZoOrange.copy(alpha = 0.35f),
                            radius = 9.dp.toPx(),
                            center = point
                        )
                    }

                    // Dot fill
                    drawCircle(
                        color = if (isSelected) ZoZoOrange else ZoZoPurple,
                        radius = if (isSelected) 5.dp.toPx() else 4.dp.toPx(),
                        center = point
                    )

                    // White inner core
                    drawCircle(
                        color = Color.White,
                        radius = 2.dp.toPx(),
                        center = point
                    )
                }
            }
        }

        // X-axis Date Labels below canvas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weightPoints.forEachIndexed { index, pt ->
                Text(
                    text = pt.dateLabel,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 10.sp
                    ),
                    color = if (index == selectedIndex) ZoZoPurple else TextMuted
                )
            }
        }
    }
}
