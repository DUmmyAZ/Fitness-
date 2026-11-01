package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DayOfWeekSchedule
import com.example.data.model.DaySchedule
import com.example.data.model.ExerciseItem
import com.example.data.model.WorkoutSet
import com.example.ui.theme.BlueAccent
import com.example.ui.theme.BlueAccentSubtle
import com.example.ui.theme.GreenSuccess
import com.example.ui.theme.GreenSuccessLight
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceBorderSubtle
import com.example.ui.theme.SurfaceWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZoZoOrange
import com.example.ui.theme.ZoZoOrangeLight
import com.example.ui.theme.ZoZoOrangeSubtle
import com.example.ui.theme.ZoZoPurple
import com.example.ui.theme.ZoZoPurpleContainer
import com.example.ui.theme.ZoZoPurpleLight
import com.example.ui.theme.ZoZoPurpleSubtle

@Composable
fun WorkoutScheduleSection(
    daysMap: Map<DayOfWeekSchedule, DaySchedule>,
    selectedDay: DayOfWeekSchedule,
    onDaySelected: (DayOfWeekSchedule) -> Unit,
    onToggleExercise: (DayOfWeekSchedule, String) -> Unit,
    onToggleSet: (DayOfWeekSchedule, String, Int) -> Unit,
    onAddHydration: (DayOfWeekSchedule) -> Unit,
    onRemoveHydration: (DayOfWeekSchedule) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentDay = daysMap[selectedDay] ?: return

    // Track expanded exercise cards
    val expandedMap = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Workout Schedule",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )
                Text(
                    text = "Select a day to track sets & exercises",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = ZoZoOrangeSubtle
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsRun,
                        contentDescription = null,
                        tint = ZoZoOrange,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (currentDay.isRestDay) "Rest Day" else "${currentDay.exercises.size} Moves",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = ZoZoOrange
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Horizontal Date Selector (Sat, Sun, Mon, Tue, Wed, Thu, Fri)
        HorizontalDateSelector(
            daysMap = daysMap,
            selectedDay = selectedDay,
            onDaySelected = onDaySelected,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Day Summary Banner Card
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SurfaceBorder, RoundedCornerShape(18.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${selectedDay.fullName} • ${currentDay.title}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TextPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = currentDay.focusDescription,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }

                    if (!currentDay.isRestDay) {
                        val completedCount = currentDay.exercises.count { it.isCompleted }
                        val total = currentDay.exercises.size
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (completedCount == total) GreenSuccessLight else ZoZoPurpleSubtle
                        ) {
                            Text(
                                text = "$completedCount/$total Done",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = if (completedCount == total) GreenSuccess else ZoZoPurple,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Check if selected day is REST DAY (Tuesday)
            if (currentDay.isRestDay) {
                RestDayRecoveryCard(
                    daySchedule = currentDay,
                    onAddHydration = { onAddHydration(selectedDay) },
                    onRemoveHydration = { onRemoveHydration(selectedDay) }
                )
            } else {
                // Dynamic Exercise List Cards for the selected day
                // Group by exercise category (e.g. Back, Triceps, Chest, Biceps, etc.)
                val categories = currentDay.exercises.map { it.category }.distinct()
                categories.forEach { categoryName ->
                    val exercisesInCategory = currentDay.exercises.filter { it.category == categoryName }

                    CategoryHeader(
                        categoryName = categoryName,
                        count = exercisesInCategory.size,
                        completedCount = exercisesInCategory.count { it.isCompleted }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    exercisesInCategory.forEach { exercise ->
                        val isExpanded = expandedMap[exercise.id] ?: false
                        ExerciseItemCard(
                            exercise = exercise,
                            isExpanded = isExpanded,
                            onToggleExpand = { expandedMap[exercise.id] = !isExpanded },
                            onToggleComplete = { onToggleExercise(selectedDay, exercise.id) },
                            onToggleSet = { setNum -> onToggleSet(selectedDay, exercise.id, setNum) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
private fun HorizontalDateSelector(
    daysMap: Map<DayOfWeekSchedule, DaySchedule>,
    selectedDay: DayOfWeekSchedule,
    onDaySelected: (DayOfWeekSchedule) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DayOfWeekSchedule.values().forEach { day ->
            val dayData = daysMap[day]
            val isSelected = day == selectedDay
            val isRest = dayData?.isRestDay == true
            val isAllDone = dayData?.exercises?.isNotEmpty() == true && dayData.exercises.all { it.isCompleted }

            val bgColor = when {
                isSelected -> ZoZoPurple
                else -> SurfaceWhite
            }
            val contentColor = when {
                isSelected -> Color.White
                else -> TextPrimary
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = bgColor,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) ZoZoPurple else SurfaceBorder
                ),
                shadowElevation = if (isSelected) 4.dp else 1.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onDaySelected(day) }
                    .testTag("date_chip_${day.name.lowercase()}")
            ) {
                Column(
                    modifier = Modifier
                        .width(52.dp)
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = day.shortName,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = if (isSelected) Color.White.copy(alpha = 0.85f) else TextSecondary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${dayData?.dateNumber ?: 0}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp
                        ),
                        color = contentColor
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Small status dot
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isSelected -> ZoZoOrange
                                    isRest -> BlueAccent
                                    isAllDone -> GreenSuccess
                                    else -> ZoZoPurpleLight.copy(alpha = 0.5f)
                                }
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryHeader(
    categoryName: String,
    count: Int,
    completedCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(6.dp, 16.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        when (categoryName) {
                            "Back" -> ZoZoPurple
                            "Triceps" -> ZoZoOrange
                            "Chest" -> ZoZoPurple
                            "Biceps" -> ZoZoOrange
                            "Shoulder" -> ZoZoPurple
                            "Abs" -> ZoZoOrange
                            "Legs" -> ZoZoPurple
                            "Warm Up" -> BlueAccent
                            "Cardio" -> ZoZoOrange
                            else -> ZoZoPurple
                        }
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = categoryName.uppercase(),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = TextPrimary
            )
        }

        Text(
            text = "$completedCount/$count Completed",
            style = MaterialTheme.typography.labelSmall,
            color = TextMuted
        )
    }
}

@Composable
private fun ExerciseItemCard(
    exercise: ExerciseItem,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onToggleComplete: () -> Unit,
    onToggleSet: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (exercise.isCompleted) Color(0xFFFBFBFE) else SurfaceWhite
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (exercise.isCompleted) GreenSuccess.copy(alpha = 0.35f) else SurfaceBorder,
                RoundedCornerShape(16.dp)
            )
            .testTag("exercise_card_${exercise.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Interactive Checkbox to mark entire exercise complete
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            if (exercise.isCompleted) GreenSuccess else SurfaceBorderSubtle
                        )
                        .border(
                            1.5.dp,
                            if (exercise.isCompleted) GreenSuccess else SurfaceBorder,
                            CircleShape
                        )
                        .clickable { onToggleComplete() }
                        .testTag("checkbox_${exercise.id}"),
                    contentAlignment = Alignment.Center
                ) {
                    if (exercise.isCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Done",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onToggleExpand() }
                ) {
                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            textDecoration = if (exercise.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                        ),
                        color = if (exercise.isCompleted) TextSecondary else TextPrimary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = exercise.targetSetsDescription,
                            style = MaterialTheme.typography.bodySmall,
                            color = ZoZoPurple
                        )

                        if (exercise.muscleGroup.isNotEmpty()) {
                            Text(
                                text = " • ${exercise.muscleGroup}",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted,
                                maxLines = 1
                            )
                        }
                    }
                }

                // Expand button to view sets breakdown
                IconButton(
                    onClick = onToggleExpand,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("expand_sets_${exercise.id}")
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Toggle sets details",
                        tint = TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Expanded Sets Breakdown & Technique tips
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFAFAFD))
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    if (exercise.tips.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = ZoZoOrange,
                                modifier = Modifier
                                    .size(14.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Cue: ${exercise.tips}",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                    }

                    Text(
                        text = "SET-BY-SET TRACKING",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    exercise.sets.forEach { workoutSet ->
                        WorkoutSetRow(
                            set = workoutSet,
                            onToggle = { onToggleSet(workoutSet.setNumber) }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkoutSetRow(
    set: WorkoutSet,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (set.isCompleted) GreenSuccessLight.copy(alpha = 0.5f) else SurfaceWhite,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (set.isCompleted) GreenSuccess.copy(alpha = 0.3f) else SurfaceBorder
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onToggle() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(if (set.isCompleted) GreenSuccess else SurfaceBorderSubtle),
                    contentAlignment = Alignment.Center
                ) {
                    if (set.isCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    } else {
                        Text(
                            text = "${set.setNumber}",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Set ${set.setNumber}: ${set.targetReps}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = TextPrimary
                )
            }

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = ZoZoPurpleSubtle
            ) {
                Text(
                    text = set.targetWeight,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    ),
                    color = ZoZoPurple,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun RestDayRecoveryCard(
    daySchedule: DaySchedule,
    onAddHydration: () -> Unit,
    onRemoveHydration: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
            .testTag("rest_day_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(BlueAccent, ZoZoPurple)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SelfImprovement,
                        contentDescription = "Rest & Recovery",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Active Recovery & Restoration",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextPrimary
                    )
                    Text(
                        text = "Muscle rebuilding, neuro-refresh & hydration",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recovery Tips List
            Text(
                text = "RECOVERY PROTOCOL",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = ZoZoPurple
            )

            Spacer(modifier = Modifier.height(8.dp))

            daySchedule.recoveryTips.forEach { tip ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(GreenSuccessLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = GreenSuccess,
                            modifier = Modifier.size(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodySmall.copy(
                            lineHeight = 18.sp
                        ),
                        color = TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Interactive Hydration Tracker
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = BlueAccentSubtle,
                border = androidx.compose.foundation.BorderStroke(1.dp, BlueAccent.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = null,
                                tint = BlueAccent,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Daily Hydration Tracker",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TextPrimary
                            )
                        }

                        Text(
                            text = "${daySchedule.hydrationGlasses} of ${daySchedule.maxHydrationGlasses} Glasses",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = BlueAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Glasses visual indicator row (8 glasses)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (i in 1..daySchedule.maxHydrationGlasses) {
                            val isDrank = i <= daySchedule.hydrationGlasses
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isDrank) BlueAccent else Color.White)
                                    .border(1.dp, if (isDrank) BlueAccent else SurfaceBorder, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalDrink,
                                    contentDescription = null,
                                    tint = if (isDrank) Color.White else TextMuted.copy(alpha = 0.5f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Plus and minus controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Log 250ml glass:",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            shape = CircleShape,
                            color = SurfaceWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .clickable { onRemoveHydration() }
                                .testTag("hydration_minus_button")
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Remove Glass",
                                    tint = TextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            shape = CircleShape,
                            color = BlueAccent,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .clickable { onAddHydration() }
                                .testTag("hydration_plus_button")
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Glass",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
