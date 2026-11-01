package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DayOfWeekSchedule
import com.example.data.model.TrackerPeriod
import com.example.data.model.TrackerSummary
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
import com.example.ui.theme.ZoZoPurpleDark
import com.example.ui.theme.ZoZoPurpleLight
import com.example.ui.theme.ZoZoPurpleSubtle

@Composable
fun TrackerSection(
    period: TrackerPeriod,
    summary: TrackerSummary,
    onPeriodSelected: (TrackerPeriod) -> Unit,
    onDaySelected: (DayOfWeekSchedule) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        // Section Title & Segmented Switcher
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Activity Tracker",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )
                Text(
                    text = "Daily goals & consistency overview",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }

            // Segmented Tab Switcher [Daily | Weekly | Monthly]
            SegmentedPeriodSwitcher(
                selectedPeriod = period,
                onPeriodSelected = onPeriodSelected
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Progress Ring Card + Key Summary
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
                .testTag("tracker_progress_card")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Visual Progress Ring
                Box(
                    modifier = Modifier.size(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    GoalProgressRing(
                        percentage = summary.completionPercentage,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${summary.completionPercentage}%",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = (-0.5).sp
                            ),
                            color = TextPrimary
                        )
                        Text(
                            text = "Achieved",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = ZoZoPurple
                        )
                    }
                }

                Spacer(modifier = Modifier.width(18.dp))

                // Activity status message & quick metrics
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (summary.completionPercentage >= 70) GreenSuccess else ZoZoOrange)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (summary.completionPercentage >= 80) "Optimal Momentum" else "Active Progress",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = if (summary.completionPercentage >= 80) GreenSuccess else ZoZoOrange
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${summary.completedExercises} of ${summary.totalExercises} exercises completed",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Mini Progress bar
                    LinearProgressIndicator(
                        progress = { summary.completionPercentage / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = ZoZoPurple,
                        trackColor = ZoZoPurpleSubtle
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "${summary.activeMinutes} min workout • ${summary.caloriesBurned} kcal burned",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Activity Summary 3 Cards Row: Active Minutes, Completed Exercises, Calories Burned
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Card 1: Active Minutes
            ActivityMetricCard(
                title = "Active Time",
                value = "${summary.activeMinutes}",
                unit = "min",
                target = "/ ${summary.targetActiveMinutes}m",
                icon = Icons.Default.Schedule,
                iconColor = ZoZoPurple,
                iconBg = ZoZoPurpleSubtle,
                modifier = Modifier.weight(1f),
                testTag = "metric_active_minutes"
            )

            // Card 2: Completed Exercises
            ActivityMetricCard(
                title = "Exercises",
                value = "${summary.completedExercises}",
                unit = "sets",
                target = "/ ${summary.totalExercises}",
                icon = Icons.Default.FitnessCenter,
                iconColor = GreenSuccess,
                iconBg = GreenSuccessLight,
                modifier = Modifier.weight(1f),
                testTag = "metric_completed_exercises"
            )

            // Card 3: Calories Burned
            ActivityMetricCard(
                title = "Calories",
                value = "${summary.caloriesBurned}",
                unit = "kcal",
                target = "/ ${summary.targetCalories}",
                icon = Icons.Default.LocalFireDepartment,
                iconColor = ZoZoOrange,
                iconBg = ZoZoOrangeSubtle,
                modifier = Modifier.weight(1f),
                testTag = "metric_calories_burned"
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Weekly Consistency Bar Chart Visualizer Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
                .testTag("weekly_consistency_card")
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = ZoZoPurple,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Weekly Consistency",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextPrimary
                        )
                    }

                    // Benchmark indicator label
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = ZoZoPurpleSubtle
                    ) {
                        Text(
                            text = "Target: 70%+",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = ZoZoPurple,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Custom Bar Chart Canvas
                WeeklyConsistencyBarChart(
                    consistencyList = summary.weeklyConsistency,
                    onDaySelected = onDaySelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Tap any day bar to inspect or log workouts for that day",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun SegmentedPeriodSwitcher(
    selectedPeriod: TrackerPeriod,
    onPeriodSelected: (TrackerPeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = SurfaceBorderSubtle,
        modifier = modifier.testTag("period_segmented_switcher")
    ) {
        Row(
            modifier = Modifier.padding(3.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            TrackerPeriod.values().forEach { period ->
                val isSelected = period == selectedPeriod
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(11.dp))
                        .background(if (isSelected) ZoZoPurple else Color.Transparent)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onPeriodSelected(period)
                        }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("period_tab_${period.name.lowercase()}"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = period.label,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = if (isSelected) Color.White else TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun GoalProgressRing(
    percentage: Int,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = percentage / 100f,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "ring_progress"
    )

    Canvas(modifier = modifier) {
        val strokeWidth = 10.dp.toPx()
        val diameter = size.minDimension - strokeWidth
        val topLeft = Offset(strokeWidth / 2f, strokeWidth / 2f)
        val arcSize = Size(diameter, diameter)

        // Background track circle
        drawArc(
            color = Color(0xFFF0EFFF),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Gradient active arc
        val gradient = Brush.sweepGradient(
            0.0f to ZoZoPurple,
            0.6f to ZoZoOrange,
            1.0f to ZoZoPurpleLight
        )

        drawArc(
            brush = gradient,
            startAngle = -90f,
            sweepAngle = 360f * animatedProgress,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun ActivityMetricCard(
    title: String,
    value: String,
    unit: String,
    target: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    iconBg: Color,
    modifier: Modifier = Modifier,
    testTag: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
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
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = target,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall.copy(
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
                        fontSize = 10.sp
                    ),
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }
    }
}

@Composable
private fun WeeklyConsistencyBarChart(
    consistencyList: List<com.example.data.model.DayConsistency>,
    onDaySelected: (DayOfWeekSchedule) -> Unit,
    modifier: Modifier = Modifier
) {
    if (consistencyList.isEmpty()) return

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(consistencyList) {
                    detectTapGestures { tapOffset ->
                        val barSpacing = size.width / consistencyList.size
                        val tappedIndex = (tapOffset.x / barSpacing).toInt().coerceIn(0, consistencyList.size - 1)
                        onDaySelected(consistencyList[tappedIndex].day)
                    }
                }
        ) {
            val chartHeight = size.height - 28.dp.toPx() // leave room for labels
            val count = consistencyList.size
            val slotWidth = size.width / count
            val barWidth = 24.dp.toPx().coerceAtMost(slotWidth * 0.65f)

            // Draw target line at 70%
            val targetY = chartHeight * (1f - 0.70f)
            drawLine(
                color = ZoZoPurple.copy(alpha = 0.25f),
                start = Offset(0f, targetY),
                end = Offset(size.width, targetY),
                strokeWidth = 1.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            consistencyList.forEachIndexed { index, item ->
                val centerX = slotWidth * index + slotWidth / 2f
                val left = centerX - barWidth / 2f
                val cornerRadius = androidx.compose.ui.geometry.CornerRadius(barWidth / 2f, barWidth / 2f)

                // Background bar track
                drawRoundRect(
                    color = Color(0xFFF1F2F6),
                    topLeft = Offset(left, 0f),
                    size = Size(barWidth, chartHeight),
                    cornerRadius = cornerRadius
                )

                // Active bar fill
                val fillRatio = item.completionRatio.coerceIn(0.08f, 1f)
                val fillHeight = chartHeight * fillRatio
                val topY = chartHeight - fillHeight

                val barBrush = if (item.isToday) {
                    Brush.verticalGradient(
                        colors = listOf(ZoZoOrange, ZoZoPurple),
                        startY = topY,
                        endY = chartHeight
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(ZoZoPurple, ZoZoPurpleDark),
                        startY = topY,
                        endY = chartHeight
                    )
                }

                drawRoundRect(
                    brush = barBrush,
                    topLeft = Offset(left, topY),
                    size = Size(barWidth, fillHeight),
                    cornerRadius = cornerRadius
                )

                // If active/today, draw indicator dot/glow above bar
                if (item.isToday) {
                    drawCircle(
                        color = ZoZoOrange,
                        radius = 3.dp.toPx(),
                        center = Offset(centerX, (topY - 6.dp.toPx()).coerceAtLeast(4.dp.toPx()))
                    )
                }
            }
        }

        // Labels row beneath canvas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            consistencyList.forEach { item ->
                Text(
                    text = item.dayLabel,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (item.isToday) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 11.sp
                    ),
                    color = if (item.isToday) ZoZoPurple else TextSecondary,
                    modifier = Modifier.clickable { onDaySelected(item.day) }
                )
            }
        }
    }
}
