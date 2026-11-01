package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HabitItem
import com.example.data.model.MoodType
import com.example.data.model.PersonalRecord
import com.example.ui.theme.GreenSuccess
import com.example.ui.theme.GreenSuccessLight
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceBorderSubtle
import com.example.ui.theme.SurfaceWhite
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZoZoOrange
import com.example.ui.theme.ZoZoOrangeContainer
import com.example.ui.theme.ZoZoOrangeSubtle
import com.example.ui.theme.ZoZoPurple
import com.example.ui.theme.ZoZoPurpleLight
import com.example.ui.theme.ZoZoPurpleSubtle

@Composable
fun PersonalGrowthSection(
    streakCount: Int,
    habits: List<HabitItem>,
    selectedMood: MoodType?,
    personalRecords: List<PersonalRecord>,
    onToggleHabit: (String) -> Unit,
    onSelectMood: (MoodType) -> Unit,
    onOpenAddPrDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    text = "Personal Growth & Habit Hub",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )
                Text(
                    text = "Daily habits, streak momentum & personal bests",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 1. Streak Counter Widget Card
        StreakCounterCard(
            streakCount = streakCount,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 2. Quick Mood Tracker Selector Card
        QuickMoodTrackerCard(
            selectedMood = selectedMood,
            onSelectMood = onSelectMood,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 3. Daily Habit Tracker Checklist Card
        DailyHabitTrackerCard(
            habits = habits,
            onToggleHabit = onToggleHabit,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 4. Personal Bests (PR) Showcase Section
        PersonalBestsShowcase(
            records = personalRecords,
            onOpenAddDialog = onOpenAddPrDialog,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun StreakCounterCard(
    streakCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
            .testTag("streak_counter_card")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            ZoZoOrangeSubtle.copy(alpha = 0.8f),
                            ZoZoPurpleSubtle.copy(alpha = 0.4f),
                            SurfaceWhite
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(ZoZoOrange, Color(0xFFFF9100))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Fire Streak",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "🔥 $streakCount Day Workout Streak",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                color = TextPrimary
                            )
                            Text(
                                text = "Next milestone: 14 Days (Titan Shield)",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                    }

                    // Shield Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = ZoZoPurple,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Shield Active",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = ZoZoPurple
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Streak milestone progress bar (12/14)
                LinearProgressIndicator(
                    progress = { (streakCount.toFloat() / 14f).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = ZoZoOrange,
                    trackColor = Color(0xFFFFE0D4)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "12/14 days completed",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                    Text(
                        text = "All-Time Best: 28 Days",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = ZoZoPurple
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickMoodTrackerCard(
    selectedMood: MoodType?,
    onSelectMood: (MoodType) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
            .testTag("mood_tracker_card")
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
                Text(
                    text = "How are you feeling today?",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )

                selectedMood?.let { mood ->
                    Text(
                        text = mood.label,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = ZoZoPurple
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mood Emojis Row: Happy, Energetic, Tired, Sore
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MoodType.values().forEach { mood ->
                    val isSelected = mood == selectedMood

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) ZoZoPurpleSubtle else SurfaceBorderSubtle,
                        border = androidx.compose.foundation.BorderStroke(
                            1.5.dp,
                            if (isSelected) ZoZoPurple else Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onSelectMood(mood) }
                            .testTag("mood_item_${mood.name.lowercase()}")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = mood.emoji,
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = mood.label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                ),
                                color = if (isSelected) ZoZoPurple else TextSecondary,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            selectedMood?.let { mood ->
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = ZoZoPurpleSubtle.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tip: ${mood.subtitle} — Training intensity will adjust accordingly.",
                        style = MaterialTheme.typography.labelSmall,
                        color = ZoZoPurple,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyHabitTrackerCard(
    habits: List<HabitItem>,
    onToggleHabit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedCount = habits.count { it.isCompleted }
    val totalCount = habits.size

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .border(1.dp, SurfaceBorder, RoundedCornerShape(20.dp))
            .testTag("habit_tracker_card")
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
                    Text(
                        text = "Daily Habits Checklist",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextPrimary
                    )
                    Text(
                        text = "$completedCount of $totalCount completed today",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (completedCount == totalCount) GreenSuccessLight else ZoZoPurpleSubtle
                ) {
                    Text(
                        text = "${(completedCount * 100 / totalCount.coerceAtLeast(1))}%",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = if (completedCount == totalCount) GreenSuccess else ZoZoPurple,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            habits.forEach { habit ->
                HabitCheckItem(
                    habit = habit,
                    onToggle = { onToggleHabit(habit.id) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun HabitCheckItem(
    habit: HabitItem,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val checkScale by animateFloatAsState(
        targetValue = if (habit.isCompleted) 1.0f else 0.85f,
        animationSpec = spring(dampingRatio = 0.6f),
        label = "habit_scale"
    )

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (habit.isCompleted) Color(0xFFF9FDFB) else SurfaceWhite,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (habit.isCompleted) GreenSuccess.copy(alpha = 0.35f) else SurfaceBorder
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("habit_item_${habit.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Category Icon
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            when (habit.iconType) {
                                "water" -> Color(0xFFE0F2FE)
                                "sleep" -> ZoZoPurpleSubtle
                                "stretch" -> ZoZoOrangeSubtle
                                "nutrition" -> GreenSuccessLight
                                else -> Color(0xFFF1F5F9)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getHabitIcon(habit.iconType),
                        contentDescription = null,
                        tint = when (habit.iconType) {
                            "water" -> Color(0xFF0284C7)
                            "sleep" -> ZoZoPurple
                            "stretch" -> ZoZoOrange
                            "nutrition" -> GreenSuccess
                            else -> TextSecondary
                        },
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = habit.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextPrimary
                    )
                    Text(
                        text = habit.target,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            // 28px Habit Check Circle
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .scale(checkScale)
                    .clip(CircleShape)
                    .background(if (habit.isCompleted) GreenSuccess else Color.Transparent)
                    .border(
                        2.dp,
                        if (habit.isCompleted) GreenSuccess else Color(0xFFD1D5DB),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (habit.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

private fun getHabitIcon(iconType: String): ImageVector {
    return when (iconType) {
        "water" -> Icons.Default.WaterDrop
        "sleep" -> Icons.Default.Bedtime
        "stretch" -> Icons.Default.SelfImprovement
        "nutrition" -> Icons.Default.Restaurant
        "steps" -> Icons.Default.DirectionsWalk
        else -> Icons.Default.Check
    }
}

@Composable
private fun PersonalBestsShowcase(
    records: List<PersonalRecord>,
    onOpenAddDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = ZoZoOrange,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Personal Bests (PR) Showcase",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = ZoZoPurple,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onOpenAddDialog() }
                    .testTag("add_pr_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add PR",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "New PR",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Horizontal scrolling cards for PRs
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            records.forEach { record ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .width(170.dp)
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
                        .testTag("pr_card_${record.id}")
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
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = ZoZoOrangeSubtle
                            ) {
                                Text(
                                    text = record.badge,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    ),
                                    color = ZoZoOrange,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            Text(
                                text = record.dateLogged,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = TextMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = record.exerciseName,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextPrimary,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = record.valueDisplay,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                            color = ZoZoPurple
                        )
                    }
                }
            }
        }
    }
}
