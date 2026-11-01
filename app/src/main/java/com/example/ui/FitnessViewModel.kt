package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.BodyMetrics
import com.example.data.model.DayConsistency
import com.example.data.model.DayOfWeekSchedule
import com.example.data.model.DaySchedule
import com.example.data.model.HabitItem
import com.example.data.model.MoodType
import com.example.data.model.MotivationalQuote
import com.example.data.model.PersonalRecord
import com.example.data.model.TrackerPeriod
import com.example.data.model.TrackerSummary
import com.example.data.model.WeightPoint
import com.example.data.repository.FitnessRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class FitnessViewModel(
    private val repository: FitnessRepository = FitnessRepository()
) : ViewModel() {

    val selectedPeriod: StateFlow<TrackerPeriod> = repository.selectedPeriod
    val selectedDay: StateFlow<DayOfWeekSchedule> = repository.selectedDay
    val daysSchedule: StateFlow<Map<DayOfWeekSchedule, DaySchedule>> = repository.daysSchedule
    val bodyMetrics: StateFlow<BodyMetrics> = repository.bodyMetrics
    val weightHistory: StateFlow<List<WeightPoint>> = repository.weightHistory
    val habits: StateFlow<List<HabitItem>> = repository.habits
    val streakCount: StateFlow<Int> = repository.streakCount
    val selectedMood: StateFlow<MoodType?> = repository.selectedMood
    val personalRecords: StateFlow<List<PersonalRecord>> = repository.personalRecords
    val currentQuote: StateFlow<MotivationalQuote> = repository.currentQuote

    val currentDaySchedule: StateFlow<DaySchedule> = combine(
        repository.daysSchedule,
        repository.selectedDay
    ) { scheduleMap, day ->
        scheduleMap[day] ?: scheduleMap[DayOfWeekSchedule.SAT]!!
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        repository.daysSchedule.value[DayOfWeekSchedule.SAT]!!
    )

    val trackerSummary: StateFlow<TrackerSummary> = combine(
        repository.selectedPeriod,
        repository.daysSchedule,
        repository.selectedDay
    ) { period, scheduleMap, activeDay ->
        val currentDay = scheduleMap[activeDay] ?: scheduleMap[DayOfWeekSchedule.SAT]!!

        val weeklyConsistency = DayOfWeekSchedule.values().map { dayEnum ->
            val dayData = scheduleMap[dayEnum]
            val ratio = if (dayData == null || dayData.isRestDay) {
                if (dayData?.isRestDay == true) 1.0f else 0.5f
            } else {
                val total = dayData.exercises.size
                val completed = dayData.exercises.count { it.isCompleted }
                if (total == 0) 1.0f else (completed.toFloat() / total.toFloat())
            }
            DayConsistency(
                day = dayEnum,
                dayLabel = dayEnum.shortName,
                completionRatio = ratio,
                isToday = (dayEnum == activeDay)
            )
        }

        when (period) {
            TrackerPeriod.DAILY -> {
                val totalEx = if (currentDay.isRestDay) 4 else currentDay.exercises.size
                val completedEx = if (currentDay.isRestDay) {
                    (currentDay.hydrationGlasses.coerceAtMost(4))
                } else {
                    currentDay.exercises.count { it.isCompleted }
                }
                val pct = if (totalEx == 0) 100 else ((completedEx.toFloat() / totalEx) * 100).toInt()
                val activeMin = if (currentDay.isRestDay) 25 else (completedEx * 8 + 12).coerceAtLeast(15)
                val calories = if (currentDay.isRestDay) 180 else (completedEx * 65 + 110)

                TrackerSummary(
                    completionPercentage = pct.coerceIn(0, 100),
                    activeMinutes = activeMin,
                    targetActiveMinutes = if (currentDay.isRestDay) 30 else 60,
                    completedExercises = completedEx,
                    totalExercises = totalEx,
                    caloriesBurned = calories,
                    targetCalories = if (currentDay.isRestDay) 250 else 600,
                    weeklyConsistency = weeklyConsistency
                )
            }
            TrackerPeriod.WEEKLY -> {
                val allExercises = scheduleMap.values.flatMap { it.exercises }
                val totalEx = allExercises.size
                val completedEx = allExercises.count { it.isCompleted }
                val pct = if (totalEx == 0) 80 else ((completedEx.toFloat() / totalEx) * 100).toInt()

                TrackerSummary(
                    completionPercentage = pct.coerceIn(0, 100),
                    activeMinutes = (completedEx * 8 + 90),
                    targetActiveMinutes = 350,
                    completedExercises = completedEx,
                    totalExercises = totalEx,
                    caloriesBurned = (completedEx * 62 + 750),
                    targetCalories = 3800,
                    weeklyConsistency = weeklyConsistency
                )
            }
            TrackerPeriod.MONTHLY -> {
                TrackerSummary(
                    completionPercentage = 84,
                    activeMinutes = 1420,
                    targetActiveMinutes = 1600,
                    completedExercises = 142,
                    totalExercises = 168,
                    caloriesBurned = 15400,
                    targetCalories = 18000,
                    weeklyConsistency = weeklyConsistency
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TrackerSummary()
    )

    fun onPeriodSelected(period: TrackerPeriod) {
        repository.selectPeriod(period)
    }

    fun onDaySelected(day: DayOfWeekSchedule) {
        repository.selectDay(day)
    }

    fun toggleExercise(day: DayOfWeekSchedule, exerciseId: String) {
        repository.toggleExerciseCompletion(day, exerciseId)
    }

    fun toggleSet(day: DayOfWeekSchedule, exerciseId: String, setNumber: Int) {
        repository.toggleSetCompletion(day, exerciseId, setNumber)
    }

    fun addHydration(day: DayOfWeekSchedule) {
        repository.addHydrationGlass(day)
    }

    fun removeHydration(day: DayOfWeekSchedule) {
        repository.removeHydrationGlass(day)
    }

    fun toggleHabit(habitId: String) {
        repository.toggleHabit(habitId)
    }

    fun selectMood(mood: MoodType) {
        repository.selectMood(mood)
    }

    fun toggleWeightUnit() {
        repository.toggleWeightUnit()
    }

    fun logWeight(weightKg: Float, dateLabel: String) {
        repository.logNewWeight(weightKg, dateLabel)
    }

    fun updateBodyMeasurements(waist: Float, chest: Float, bodyFat: Float, muscleMass: Float) {
        repository.updateMeasurements(waist, chest, bodyFat, muscleMass)
    }

    fun addPersonalRecord(exerciseName: String, valueDisplay: String) {
        repository.addPersonalRecord(exerciseName, valueDisplay)
    }

    fun nextQuote() {
        repository.nextQuote()
    }
}
