package com.example.data.model

enum class DayOfWeekSchedule(val shortName: String, val fullName: String) {
    SAT("Sat", "Saturday"),
    SUN("Sun", "Sunday"),
    MON("Mon", "Monday"),
    TUE("Tue", "Tuesday"),
    WED("Wed", "Wednesday"),
    THU("Thu", "Thursday"),
    FRI("Fri", "Friday")
}

data class WorkoutSet(
    val setNumber: Int,
    val targetReps: String = "10-12",
    val targetWeight: String = "Moderate",
    val isCompleted: Boolean = false
)

data class ExerciseItem(
    val id: String,
    val name: String,
    val category: String,
    val targetSetsDescription: String = "3 sets × 10-12 reps",
    val sets: List<WorkoutSet> = listOf(
        WorkoutSet(1, "12 reps", "Warm set", false),
        WorkoutSet(2, "10 reps", "Working set", false),
        WorkoutSet(3, "8-10 reps", "Top set", false)
    ),
    val isCompleted: Boolean = false,
    val muscleGroup: String = "",
    val tips: String = ""
)

data class DaySchedule(
    val day: DayOfWeekSchedule,
    val dateNumber: Int,
    val title: String,
    val focusDescription: String,
    val isRestDay: Boolean = false,
    val exercises: List<ExerciseItem> = emptyList(),
    val recoveryTips: List<String> = emptyList(),
    val hydrationGlasses: Int = 5,
    val maxHydrationGlasses: Int = 8
)

enum class TrackerPeriod(val label: String) {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly")
}

data class DayConsistency(
    val day: DayOfWeekSchedule,
    val dayLabel: String,
    val completionRatio: Float, // 0.0f - 1.0f
    val isToday: Boolean = false
)

data class TrackerSummary(
    val completionPercentage: Int = 75,
    val activeMinutes: Int = 52,
    val targetActiveMinutes: Int = 60,
    val completedExercises: Int = 6,
    val totalExercises: Int = 8,
    val caloriesBurned: Int = 485,
    val targetCalories: Int = 600,
    val weeklyConsistency: List<DayConsistency> = emptyList()
)

data class WeightPoint(
    val dateLabel: String,
    val weightKg: Float
)

data class BodyMetrics(
    val currentWeightKg: Float = 74.5f,
    val useLbs: Boolean = false,
    val bodyFatPercentage: Float = 14.8f,
    val muscleMassPercentage: Float = 43.2f,
    val waistInches: Float = 31.5f,
    val chestInches: Float = 39.4f
)

data class HabitItem(
    val id: String,
    val title: String,
    val target: String,
    val iconType: String,
    val isCompleted: Boolean = false
)

enum class MoodType(val emoji: String, val label: String, val subtitle: String) {
    HAPPY("😊", "Happy", "Feeling great & balanced"),
    ENERGETIC("⚡", "Energetic", "Ready to crush goals"),
    TIRED("🥱", "Tired", "Need extra recovery time"),
    SORE("🏋️‍♂️", "Sore", "Muscles recovering deeply")
}

data class PersonalRecord(
    val id: String,
    val exerciseName: String,
    val valueDisplay: String,
    val dateLogged: String,
    val badge: String = "PR"
)

data class MotivationalQuote(
    val quote: String,
    val author: String
)
