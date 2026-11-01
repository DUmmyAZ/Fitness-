package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AddPrDialog
import com.example.ui.components.BodyMetricsSection
import com.example.ui.components.HeaderSection
import com.example.ui.components.LogWeightDialog
import com.example.ui.components.PersonalGrowthSection
import com.example.ui.components.TrackerSection
import com.example.ui.components.WorkoutScheduleSection
import com.example.ui.theme.BackgroundLight

@Composable
fun DashboardScreen(
    viewModel: FitnessViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val selectedPeriod by viewModel.selectedPeriod.collectAsStateWithLifecycle()
    val selectedDay by viewModel.selectedDay.collectAsStateWithLifecycle()
    val daysSchedule by viewModel.daysSchedule.collectAsStateWithLifecycle()
    val trackerSummary by viewModel.trackerSummary.collectAsStateWithLifecycle()
    val bodyMetrics by viewModel.bodyMetrics.collectAsStateWithLifecycle()
    val weightHistory by viewModel.weightHistory.collectAsStateWithLifecycle()
    val habits by viewModel.habits.collectAsStateWithLifecycle()
    val streakCount by viewModel.streakCount.collectAsStateWithLifecycle()
    val selectedMood by viewModel.selectedMood.collectAsStateWithLifecycle()
    val personalRecords by viewModel.personalRecords.collectAsStateWithLifecycle()
    val currentQuote by viewModel.currentQuote.collectAsStateWithLifecycle()

    var showLogWeightDialog by remember { mutableStateOf(false) }
    var showAddPrDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val statusBarInsets = WindowInsets.statusBars.asPaddingValues()
    val navBarInsets = WindowInsets.navigationBars.asPaddingValues()

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("dashboard_scaffold")
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundLight),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 680.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = statusBarInsets.calculateTopPadding())
                    .padding(bottom = navBarInsets.calculateBottomPadding() + 24.dp)
            ) {
                // 1. Header Section: Greeting, Profile Avatar, Motivational Quote Banner
                HeaderSection(
                    userName = "Alex",
                    streakDays = streakCount,
                    currentQuote = currentQuote,
                    onRefreshQuote = { viewModel.nextQuote() }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 2. Daily, Weekly & Monthly Tracker: Tab switcher, Progress Ring, 3 Metric cards, Consistency Bar Chart
                TrackerSection(
                    period = selectedPeriod,
                    summary = trackerSummary,
                    onPeriodSelected = { viewModel.onPeriodSelected(it) },
                    onDaySelected = { viewModel.onDaySelected(it) }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 3. Interactive Workout Schedule: Horizontal Date picker & dynamic exercise cards with checkboxes
                WorkoutScheduleSection(
                    daysMap = daysSchedule,
                    selectedDay = selectedDay,
                    onDaySelected = { viewModel.onDaySelected(it) },
                    onToggleExercise = { day, exId -> viewModel.toggleExercise(day, exId) },
                    onToggleSet = { day, exId, setNum -> viewModel.toggleSet(day, exId, setNum) },
                    onAddHydration = { day -> viewModel.addHydration(day) },
                    onRemoveHydration = { day -> viewModel.removeHydration(day) }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 4. Body Metrics Section: Weight trend graph & metric preview chips
                BodyMetricsSection(
                    metrics = bodyMetrics,
                    weightHistory = weightHistory,
                    onToggleUnit = { viewModel.toggleWeightUnit() },
                    onOpenLogDialog = { showLogWeightDialog = true }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 5. Personal Growth & Habit Hub: Streak counter, Daily Habit tracker, Mood tracker, PR showcase
                PersonalGrowthSection(
                    streakCount = streakCount,
                    habits = habits,
                    selectedMood = selectedMood,
                    personalRecords = personalRecords,
                    onToggleHabit = { viewModel.toggleHabit(it) },
                    onSelectMood = { viewModel.selectMood(it) },
                    onOpenAddPrDialog = { showAddPrDialog = true }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Dialogs
    if (showLogWeightDialog) {
        LogWeightDialog(
            currentWeightKg = bodyMetrics.currentWeightKg,
            useLbs = bodyMetrics.useLbs,
            onDismiss = { showLogWeightDialog = false },
            onSaveWeight = { newKg ->
                viewModel.logWeight(newKg, "Today")
            }
        )
    }

    if (showAddPrDialog) {
        AddPrDialog(
            onDismiss = { showAddPrDialog = false },
            onSavePr = { name, value ->
                viewModel.addPersonalRecord(name, value)
            }
        )
    }
}
