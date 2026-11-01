package com.example.data.repository

import com.example.data.model.BodyMetrics
import com.example.data.model.DayConsistency
import com.example.data.model.DayOfWeekSchedule
import com.example.data.model.DaySchedule
import com.example.data.model.ExerciseItem
import com.example.data.model.HabitItem
import com.example.data.model.MoodType
import com.example.data.model.MotivationalQuote
import com.example.data.model.PersonalRecord
import com.example.data.model.TrackerPeriod
import com.example.data.model.TrackerSummary
import com.example.data.model.WeightPoint
import com.example.data.model.WorkoutSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FitnessRepository {

    private val quotes = listOf(
        MotivationalQuote(
            "Small daily improvements over time lead to stunning results.",
            "Robin Sharma"
        ),
        MotivationalQuote(
            "The body achieves what the mind believes.",
            "Napoleon Hill"
        ),
        MotivationalQuote(
            "Action is the foundational key to all success.",
            "Pablo Picasso"
        ),
        MotivationalQuote(
            "Discipline is choosing between what you want now and what you want most.",
            "Abraham Lincoln"
        ),
        MotivationalQuote(
            "Success starts with self-discipline and relentless consistency.",
            "Dwayne Johnson"
        )
    )

    private val _currentQuoteIndex = MutableStateFlow(0)
    val currentQuote: StateFlow<MotivationalQuote> = MutableStateFlow(quotes[0])

    private val _selectedPeriod = MutableStateFlow(TrackerPeriod.DAILY)
    val selectedPeriod: StateFlow<TrackerPeriod> = _selectedPeriod.asStateFlow()

    private val _selectedDay = MutableStateFlow(DayOfWeekSchedule.SAT)
    val selectedDay: StateFlow<DayOfWeekSchedule> = _selectedDay.asStateFlow()

    private val _streakCount = MutableStateFlow(12)
    val streakCount: StateFlow<Int> = _streakCount.asStateFlow()

    private val _selectedMood = MutableStateFlow<MoodType?>(MoodType.ENERGETIC)
    val selectedMood: StateFlow<MoodType?> = _selectedMood.asStateFlow()

    private val _bodyMetrics = MutableStateFlow(
        BodyMetrics(
            currentWeightKg = 74.2f,
            useLbs = false,
            bodyFatPercentage = 14.8f,
            muscleMassPercentage = 43.2f,
            waistInches = 31.5f,
            chestInches = 39.4f
        )
    )
    val bodyMetrics: StateFlow<BodyMetrics> = _bodyMetrics.asStateFlow()

    private val _weightHistory = MutableStateFlow(
        listOf(
            WeightPoint("Oct 5", 76.5f),
            WeightPoint("Oct 12", 75.9f),
            WeightPoint("Oct 19", 75.2f),
            WeightPoint("Oct 26", 74.8f),
            WeightPoint("Nov 01", 74.2f)
        )
    )
    val weightHistory: StateFlow<List<WeightPoint>> = _weightHistory.asStateFlow()

    private val _habits = MutableStateFlow(
        listOf(
            HabitItem("h1", "Daily Hydration", "3.0L Target", "water", true),
            HabitItem("h2", "8-Hour Deep Sleep", "Bedtime 10:30 PM", "sleep", true),
            HabitItem("h3", "Post-Workout Stretch", "15 Min Routine", "stretch", false),
            HabitItem("h4", "Protein Target", "160g Balanced", "nutrition", true),
            HabitItem("h5", "10,000 Daily Steps", "8,420 so far", "steps", false)
        )
    )
    val habits: StateFlow<List<HabitItem>> = _habits.asStateFlow()

    private val _personalRecords = MutableStateFlow(
        listOf(
            PersonalRecord("pr1", "Barbell Squat", "140 kg (308 lbs)", "Oct 28", "1RM"),
            PersonalRecord("pr2", "Max Pull-Ups", "22 clean reps", "Oct 24", "BODYWEIGHT"),
            PersonalRecord("pr3", "Incline Smith Press", "95 kg × 8 reps", "Oct 20", "HYPERTROPHY"),
            PersonalRecord("pr4", "Barbell Deadlift", "175 kg (385 lbs)", "Oct 15", "1RM")
        )
    )
    val personalRecords: StateFlow<List<PersonalRecord>> = _personalRecords.asStateFlow()

    private val _daysSchedule = MutableStateFlow(createInitialSchedule())
    val daysSchedule: StateFlow<Map<DayOfWeekSchedule, DaySchedule>> = _daysSchedule.asStateFlow()

    private fun createInitialSchedule(): Map<DayOfWeekSchedule, DaySchedule> {
        return mapOf(
            DayOfWeekSchedule.SAT to DaySchedule(
                day = DayOfWeekSchedule.SAT,
                dateNumber = 25,
                title = "Back & Triceps",
                focusDescription = "Upper Body Hypertrophy & Pulling Strength",
                exercises = listOf(
                    ExerciseItem(
                        id = "sat_1",
                        name = "Pull Up",
                        category = "Back",
                        targetSetsDescription = "3 sets × Bodyweight to Failure",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "BW", true),
                            WorkoutSet(2, "10 reps", "BW", true),
                            WorkoutSet(3, "8 reps", "BW", false)
                        ),
                        isCompleted = false,
                        muscleGroup = "Lats, Rhomboids, Biceps",
                        tips = "Drive elbows down to hips, full stretch at bottom"
                    ),
                    ExerciseItem(
                        id = "sat_2",
                        name = "Lat Pulldown",
                        category = "Back",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "65 kg", true),
                            WorkoutSet(2, "10 reps", "70 kg", true),
                            WorkoutSet(3, "10 reps", "70 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Latissimus Dorsi",
                        tips = "Slight thoracic extension, avoid excessive swinging"
                    ),
                    ExerciseItem(
                        id = "sat_3",
                        name = "Single Hand Lat Prayer",
                        category = "Back",
                        targetSetsDescription = "3 sets × 12 reps each",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "25 kg", true),
                            WorkoutSet(2, "12 reps", "27.5 kg", true),
                            WorkoutSet(3, "12 reps", "27.5 kg", false)
                        ),
                        isCompleted = false,
                        muscleGroup = "Iliac Lat Division",
                        tips = "Cable at upper height, keep elbow pinned inward"
                    ),
                    ExerciseItem(
                        id = "sat_4",
                        name = "T-Bar Row",
                        category = "Back",
                        targetSetsDescription = "3 sets × 8-10 reps",
                        sets = listOf(
                            WorkoutSet(1, "10 reps", "50 kg", true),
                            WorkoutSet(2, "10 reps", "55 kg", true),
                            WorkoutSet(3, "8 reps", "60 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Mid-Back, Traps & Rear Delts",
                        tips = "Brace core firmly against chest support"
                    ),
                    ExerciseItem(
                        id = "sat_5",
                        name = "Back Extension",
                        category = "Back",
                        targetSetsDescription = "3 sets × 12-15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "+10 kg plate", true),
                            WorkoutSet(2, "15 reps", "+10 kg plate", true),
                            WorkoutSet(3, "12 reps", "+15 kg plate", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Erector Spinae & Glutes",
                        tips = "Pivot at hips, squeeze glutes at the peak"
                    ),
                    ExerciseItem(
                        id = "sat_6",
                        name = "V-Bar Pushdown",
                        category = "Triceps",
                        targetSetsDescription = "3 sets × 12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "32 kg", true),
                            WorkoutSet(2, "12 reps", "36 kg", true),
                            WorkoutSet(3, "10 reps", "41 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Triceps Lateral & Medial Head",
                        tips = "Keep elbows locked near ribs throughout"
                    ),
                    ExerciseItem(
                        id = "sat_7",
                        name = "Rope Overhead Press",
                        category = "Triceps",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "25 kg", true),
                            WorkoutSet(2, "10 reps", "28 kg", false),
                            WorkoutSet(3, "10 reps", "28 kg", false)
                        ),
                        isCompleted = false,
                        muscleGroup = "Triceps Long Head",
                        tips = "Flare rope apart at top lockout"
                    ),
                    ExerciseItem(
                        id = "sat_8",
                        name = "Single Hand Rope Pushdown",
                        category = "Triceps",
                        targetSetsDescription = "3 sets × 12-15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "14 kg", true),
                            WorkoutSet(2, "12 reps", "16 kg", true),
                            WorkoutSet(3, "12 reps", "16 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Triceps Isolation",
                        tips = "Full lock at bottom, pause 1 second"
                    )
                )
            ),
            DayOfWeekSchedule.SUN to DaySchedule(
                day = DayOfWeekSchedule.SUN,
                dateNumber = 26,
                title = "Chest & Biceps",
                focusDescription = "Push Power, Upper Pectorals & Arm Peak",
                exercises = listOf(
                    ExerciseItem(
                        id = "sun_1",
                        name = "Dynamic Shoulder Warm Up",
                        category = "Warm Up",
                        targetSetsDescription = "2 sets × Band Dislocates & Scapular Pushups",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "Light Band", true),
                            WorkoutSet(2, "15 reps", "Light Band", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Rotator Cuff & Scapula"
                    ),
                    ExerciseItem(
                        id = "sun_2",
                        name = "Incline Smith Machine Press",
                        category = "Chest",
                        targetSetsDescription = "4 sets × 8-10 reps",
                        sets = listOf(
                            WorkoutSet(1, "10 reps", "70 kg", true),
                            WorkoutSet(2, "10 reps", "80 kg", true),
                            WorkoutSet(3, "8 reps", "85 kg", true),
                            WorkoutSet(4, "8 reps", "85 kg", false)
                        ),
                        isCompleted = false,
                        muscleGroup = "Clavicular Pectoral Head",
                        tips = "Bench at 30 degrees, touch upper chest gently"
                    ),
                    ExerciseItem(
                        id = "sun_3",
                        name = "Machine Press",
                        category = "Chest",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "75 kg", true),
                            WorkoutSet(2, "10 reps", "80 kg", true),
                            WorkoutSet(3, "10 reps", "80 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Sternal Pectorals",
                        tips = "Slow 3-second negative eccentric"
                    ),
                    ExerciseItem(
                        id = "sun_4",
                        name = "Cable Fly High to Low",
                        category = "Chest",
                        targetSetsDescription = "3 sets × 12-15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "18 kg", true),
                            WorkoutSet(2, "12 reps", "20 kg", true),
                            WorkoutSet(3, "12 reps", "20 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Lower Pectorals",
                        tips = "Cross hands slightly at peak squeeze"
                    ),
                    ExerciseItem(
                        id = "sun_5",
                        name = "Seated Curl",
                        category = "Biceps",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "14 kg DBs", true),
                            WorkoutSet(2, "10 reps", "16 kg DBs", true),
                            WorkoutSet(3, "10 reps", "16 kg DBs", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Biceps Brachii",
                        tips = "Supinate wrists outwards at top"
                    ),
                    ExerciseItem(
                        id = "sun_6",
                        name = "Hammer Curl with Ropes",
                        category = "Biceps",
                        targetSetsDescription = "3 sets × 12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "27 kg", true),
                            WorkoutSet(2, "12 reps", "30 kg", true),
                            WorkoutSet(3, "10 reps", "32 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Brachialis & Forearms",
                        tips = "Neutral grip, elbows stationary"
                    ),
                    ExerciseItem(
                        id = "sun_7",
                        name = "Preacher Curl",
                        category = "Biceps",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "28 kg EZ", true),
                            WorkoutSet(2, "10 reps", "32 kg EZ", false),
                            WorkoutSet(3, "8 reps", "35 kg EZ", false)
                        ),
                        isCompleted = false,
                        muscleGroup = "Short Head Bicep Peak",
                        tips = "Full stretch without hyperextending elbows"
                    )
                )
            ),
            DayOfWeekSchedule.MON to DaySchedule(
                day = DayOfWeekSchedule.MON,
                dateNumber = 27,
                title = "Shoulder, Abs & Cardio",
                focusDescription = "Deltoid Cap, Core Sculpting & Aerobic Flush",
                exercises = listOf(
                    ExerciseItem(
                        id = "mon_1",
                        name = "Rope Front Raise",
                        category = "Shoulder",
                        targetSetsDescription = "3 sets × 12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "20 kg", true),
                            WorkoutSet(2, "12 reps", "22.5 kg", true),
                            WorkoutSet(3, "10 reps", "22.5 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Anterior Deltoid"
                    ),
                    ExerciseItem(
                        id = "mon_2",
                        name = "Rope Lateral Raise",
                        category = "Shoulder",
                        targetSetsDescription = "4 sets × 15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "12 kg", true),
                            WorkoutSet(2, "15 reps", "14 kg", true),
                            WorkoutSet(3, "15 reps", "14 kg", true),
                            WorkoutSet(4, "12 reps", "16 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Lateral Deltoid",
                        tips = "Cable between legs for continuous tension"
                    ),
                    ExerciseItem(
                        id = "mon_3",
                        name = "Face Pull",
                        category = "Shoulder",
                        targetSetsDescription = "3 sets × 15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "25 kg", true),
                            WorkoutSet(2, "15 reps", "27 kg", true),
                            WorkoutSet(3, "15 reps", "27 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Posterior Delts & Rhomboids",
                        tips = "Pull to forehead height, external rotation"
                    ),
                    ExerciseItem(
                        id = "mon_4",
                        name = "Cable Crunches",
                        category = "Abs",
                        targetSetsDescription = "3 sets × 15-20 reps",
                        sets = listOf(
                            WorkoutSet(1, "20 reps", "45 kg", true),
                            WorkoutSet(2, "18 reps", "50 kg", true),
                            WorkoutSet(3, "15 reps", "55 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Rectus Abdominis",
                        tips = "Curl spine down rather than hinging at hips"
                    ),
                    ExerciseItem(
                        id = "mon_5",
                        name = "Hanging Leg Raises",
                        category = "Abs",
                        targetSetsDescription = "3 sets × 12-15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "Bodyweight", true),
                            WorkoutSet(2, "12 reps", "Bodyweight", true),
                            WorkoutSet(3, "12 reps", "Bodyweight", false)
                        ),
                        isCompleted = false,
                        muscleGroup = "Lower Abdominals & Hip Flexors"
                    ),
                    ExerciseItem(
                        id = "mon_6",
                        name = "Dragonfly",
                        category = "Abs",
                        targetSetsDescription = "3 sets × 8-10 reps",
                        sets = listOf(
                            WorkoutSet(1, "8 reps", "Bench grip", false),
                            WorkoutSet(2, "8 reps", "Bench grip", false),
                            WorkoutSet(3, "6 reps", "Bench grip", false)
                        ),
                        isCompleted = false,
                        muscleGroup = "Full Core Anterior Chain",
                        tips = "Bruce Lee signature core move, slow lowering"
                    ),
                    ExerciseItem(
                        id = "mon_7",
                        name = "Cardio (15-20 minutes)",
                        category = "Cardio",
                        targetSetsDescription = "Incline treadmill walking or assault bike",
                        sets = listOf(
                            WorkoutSet(1, "18 min", "Zone 2 HR", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Cardiovascular Conditioning"
                    )
                )
            ),
            DayOfWeekSchedule.TUE to DaySchedule(
                day = DayOfWeekSchedule.TUE,
                dateNumber = 28,
                title = "Rest & Active Recovery",
                focusDescription = "Regenerate CNS, Deep Hydration & Fascial Release",
                isRestDay = true,
                recoveryTips = listOf(
                    "20-30 min gentle walk in fresh air to flush lactic acid",
                    "Foam roll hamstrings, thoracic spine, and lat insertions",
                    "Target 3.0+ Liters of pure water with pinch of sea salt & electrolytes",
                    "Aim for 8-9 hours restorative sleep; keep room cool and dark"
                ),
                hydrationGlasses = 6,
                maxHydrationGlasses = 8
            ),
            DayOfWeekSchedule.WED to DaySchedule(
                day = DayOfWeekSchedule.WED,
                dateNumber = 29,
                title = "Back & Triceps (Volume)",
                focusDescription = "Lat Width, Mid-Back Thickness & Arm Density",
                exercises = listOf(
                    ExerciseItem(
                        id = "wed_1",
                        name = "Pull Up",
                        category = "Back",
                        targetSetsDescription = "3 sets × Max Reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "BW", true),
                            WorkoutSet(2, "10 reps", "BW", true),
                            WorkoutSet(3, "9 reps", "BW", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Lats & Biceps"
                    ),
                    ExerciseItem(
                        id = "wed_2",
                        name = "Lat Pulldown",
                        category = "Back",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "68 kg", true),
                            WorkoutSet(2, "10 reps", "72.5 kg", true),
                            WorkoutSet(3, "10 reps", "72.5 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Lats"
                    ),
                    ExerciseItem(
                        id = "wed_3",
                        name = "Single Hand Lat Prayer",
                        category = "Back",
                        targetSetsDescription = "3 sets × 12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "27.5 kg", true),
                            WorkoutSet(2, "12 reps", "30 kg", true),
                            WorkoutSet(3, "12 reps", "30 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Lower Lat"
                    ),
                    ExerciseItem(
                        id = "wed_4",
                        name = "T-Bar Row",
                        category = "Back",
                        targetSetsDescription = "3 sets × 8-10 reps",
                        sets = listOf(
                            WorkoutSet(1, "10 reps", "55 kg", true),
                            WorkoutSet(2, "10 reps", "60 kg", true),
                            WorkoutSet(3, "8 reps", "65 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Mid-Back Thickness"
                    ),
                    ExerciseItem(
                        id = "wed_5",
                        name = "Back Extension",
                        category = "Back",
                        targetSetsDescription = "3 sets × 15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "+15 kg", true),
                            WorkoutSet(2, "15 reps", "+15 kg", true),
                            WorkoutSet(3, "15 reps", "+15 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Lower Back & Glute Fold"
                    ),
                    ExerciseItem(
                        id = "wed_6",
                        name = "V-Bar Pushdown",
                        category = "Triceps",
                        targetSetsDescription = "3 sets × 12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "36 kg", true),
                            WorkoutSet(2, "12 reps", "38.5 kg", true),
                            WorkoutSet(3, "10 reps", "41 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Triceps Outer Head"
                    ),
                    ExerciseItem(
                        id = "wed_7",
                        name = "Rope Overhead Press",
                        category = "Triceps",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "27 kg", true),
                            WorkoutSet(2, "10 reps", "30 kg", true),
                            WorkoutSet(3, "10 reps", "30 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Long Head Triceps"
                    ),
                    ExerciseItem(
                        id = "wed_8",
                        name = "Single Hand Rope Pushdown",
                        category = "Triceps",
                        targetSetsDescription = "3 sets × 12-15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "16 kg", true),
                            WorkoutSet(2, "12 reps", "18 kg", true),
                            WorkoutSet(3, "12 reps", "18 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Tricep Horseshoe"
                    )
                )
            ),
            DayOfWeekSchedule.THU to DaySchedule(
                day = DayOfWeekSchedule.THU,
                dateNumber = 30,
                title = "Chest & Biceps",
                focusDescription = "Chest Upper Fibers & Bicep Peak Contraction",
                exercises = listOf(
                    ExerciseItem(
                        id = "thu_1",
                        name = "Rotator Cuff Warm Up",
                        category = "Warm Up",
                        targetSetsDescription = "2 sets × Internal & External Rotations",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "5 kg cable", true),
                            WorkoutSet(2, "15 reps", "5 kg cable", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Infraspinatus & Teres Minor"
                    ),
                    ExerciseItem(
                        id = "thu_2",
                        name = "Incline Smith Machine Press",
                        category = "Chest",
                        targetSetsDescription = "4 sets × 8-10 reps",
                        sets = listOf(
                            WorkoutSet(1, "10 reps", "75 kg", true),
                            WorkoutSet(2, "10 reps", "82.5 kg", true),
                            WorkoutSet(3, "8 reps", "87.5 kg", true),
                            WorkoutSet(4, "6 reps", "90 kg", false)
                        ),
                        isCompleted = false,
                        muscleGroup = "Upper Chest"
                    ),
                    ExerciseItem(
                        id = "thu_3",
                        name = "Machine Press",
                        category = "Chest",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "80 kg", true),
                            WorkoutSet(2, "10 reps", "85 kg", true),
                            WorkoutSet(3, "10 reps", "85 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Mid Chest"
                    ),
                    ExerciseItem(
                        id = "thu_4",
                        name = "Cable Fly High to Low",
                        category = "Chest",
                        targetSetsDescription = "3 sets × 12-15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "20 kg", true),
                            WorkoutSet(2, "15 reps", "22 kg", true),
                            WorkoutSet(3, "12 reps", "22 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Lower Chest"
                    ),
                    ExerciseItem(
                        id = "thu_5",
                        name = "Seated Curl",
                        category = "Biceps",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "16 kg DBs", true),
                            WorkoutSet(2, "10 reps", "18 kg DBs", true),
                            WorkoutSet(3, "10 reps", "18 kg DBs", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Biceps Short & Long Head"
                    ),
                    ExerciseItem(
                        id = "thu_6",
                        name = "Hammer Curl with Ropes",
                        category = "Biceps",
                        targetSetsDescription = "3 sets × 12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "32 kg", true),
                            WorkoutSet(2, "12 reps", "34 kg", true),
                            WorkoutSet(3, "10 reps", "36 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Brachialis"
                    ),
                    ExerciseItem(
                        id = "thu_7",
                        name = "Preacher Curl",
                        category = "Biceps",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "30 kg EZ", true),
                            WorkoutSet(2, "10 reps", "32.5 kg EZ", true),
                            WorkoutSet(3, "10 reps", "32.5 kg EZ", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Bicep Peak"
                    )
                )
            ),
            DayOfWeekSchedule.FRI to DaySchedule(
                day = DayOfWeekSchedule.FRI,
                dateNumber = 31,
                title = "Legs, Shoulder & Abs",
                focusDescription = "Quad Dominance, Posterior Chain, Delts & Core",
                exercises = listOf(
                    ExerciseItem(
                        id = "fri_1",
                        name = "Heavy Barbell Squats",
                        category = "Legs",
                        targetSetsDescription = "4 sets × 6-8 reps",
                        sets = listOf(
                            WorkoutSet(1, "8 reps", "110 kg", true),
                            WorkoutSet(2, "8 reps", "125 kg", true),
                            WorkoutSet(3, "6 reps", "135 kg", true),
                            WorkoutSet(4, "6 reps", "140 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Quadriceps & Glute Max",
                        tips = "Brace 360 degrees, depth below parallel"
                    ),
                    ExerciseItem(
                        id = "fri_2",
                        name = "Leg Extension",
                        category = "Legs",
                        targetSetsDescription = "3 sets × 12-15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "60 kg", true),
                            WorkoutSet(2, "15 reps", "65 kg", true),
                            WorkoutSet(3, "12 reps", "70 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Rectus Femoris Quad Peak"
                    ),
                    ExerciseItem(
                        id = "fri_3",
                        name = "Hamstring Curl",
                        category = "Legs",
                        targetSetsDescription = "3 sets × 10-12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "50 kg", true),
                            WorkoutSet(2, "10 reps", "55 kg", true),
                            WorkoutSet(3, "10 reps", "55 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Biceps Femoris & Semitendinosus"
                    ),
                    ExerciseItem(
                        id = "fri_4",
                        name = "Calf Raises",
                        category = "Legs",
                        targetSetsDescription = "4 sets × 15-20 reps",
                        sets = listOf(
                            WorkoutSet(1, "20 reps", "80 kg", true),
                            WorkoutSet(2, "20 reps", "85 kg", true),
                            WorkoutSet(3, "18 reps", "90 kg", true),
                            WorkoutSet(4, "15 reps", "95 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Gastrocnemius & Soleus",
                        tips = "Hold bottom stretch 2 seconds, high peak squeeze"
                    ),
                    ExerciseItem(
                        id = "fri_5",
                        name = "Rope Front Raise",
                        category = "Shoulder",
                        targetSetsDescription = "3 sets × 12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "22.5 kg", true),
                            WorkoutSet(2, "12 reps", "22.5 kg", true),
                            WorkoutSet(3, "12 reps", "22.5 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Anterior Delts"
                    ),
                    ExerciseItem(
                        id = "fri_6",
                        name = "Rope Lateral Raise",
                        category = "Shoulder",
                        targetSetsDescription = "4 sets × 15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "14 kg", true),
                            WorkoutSet(2, "15 reps", "14 kg", true),
                            WorkoutSet(3, "15 reps", "14 kg", true),
                            WorkoutSet(4, "15 reps", "14 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Side Deltoids"
                    ),
                    ExerciseItem(
                        id = "fri_7",
                        name = "Face Pull",
                        category = "Shoulder",
                        targetSetsDescription = "3 sets × 15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "27 kg", true),
                            WorkoutSet(2, "15 reps", "27 kg", true),
                            WorkoutSet(3, "15 reps", "30 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Rear Delts & Rotator"
                    ),
                    ExerciseItem(
                        id = "fri_8",
                        name = "Cable Crunches",
                        category = "Abs",
                        targetSetsDescription = "3 sets × 15 reps",
                        sets = listOf(
                            WorkoutSet(1, "15 reps", "50 kg", true),
                            WorkoutSet(2, "15 reps", "55 kg", true),
                            WorkoutSet(3, "15 reps", "55 kg", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Abs Upper & Middle"
                    ),
                    ExerciseItem(
                        id = "fri_9",
                        name = "Hanging Leg Raises",
                        category = "Abs",
                        targetSetsDescription = "3 sets × 12 reps",
                        sets = listOf(
                            WorkoutSet(1, "12 reps", "BW", true),
                            WorkoutSet(2, "12 reps", "BW", true),
                            WorkoutSet(3, "12 reps", "BW", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Lower Core"
                    ),
                    ExerciseItem(
                        id = "fri_10",
                        name = "Dragonfly",
                        category = "Abs",
                        targetSetsDescription = "3 sets × 8 reps",
                        sets = listOf(
                            WorkoutSet(1, "8 reps", "Bodyweight", true),
                            WorkoutSet(2, "8 reps", "Bodyweight", true),
                            WorkoutSet(3, "8 reps", "Bodyweight", true)
                        ),
                        isCompleted = true,
                        muscleGroup = "Total Core Anterior Wall"
                    )
                )
            )
        )
    }

    fun selectPeriod(period: TrackerPeriod) {
        _selectedPeriod.value = period
    }

    fun selectDay(day: DayOfWeekSchedule) {
        _selectedDay.value = day
    }

    fun toggleExerciseCompletion(day: DayOfWeekSchedule, exerciseId: String) {
        _daysSchedule.update { map ->
            val daySchedule = map[day] ?: return@update map
            val updatedExercises = daySchedule.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    val newCompleted = !exercise.isCompleted
                    val updatedSets = exercise.sets.map { it.copy(isCompleted = newCompleted) }
                    exercise.copy(isCompleted = newCompleted, sets = updatedSets)
                } else exercise
            }
            map + (day to daySchedule.copy(exercises = updatedExercises))
        }
    }

    fun toggleSetCompletion(day: DayOfWeekSchedule, exerciseId: String, setNumber: Int) {
        _daysSchedule.update { map ->
            val daySchedule = map[day] ?: return@update map
            val updatedExercises = daySchedule.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    val updatedSets = exercise.sets.map { s ->
                        if (s.setNumber == setNumber) s.copy(isCompleted = !s.isCompleted) else s
                    }
                    val allSetsDone = updatedSets.all { it.isCompleted }
                    exercise.copy(sets = updatedSets, isCompleted = allSetsDone)
                } else exercise
            }
            map + (day to daySchedule.copy(exercises = updatedExercises))
        }
    }

    fun addHydrationGlass(day: DayOfWeekSchedule) {
        _daysSchedule.update { map ->
            val current = map[day] ?: return@update map
            val newCount = (current.hydrationGlasses + 1).coerceAtMost(current.maxHydrationGlasses + 4)
            map + (day to current.copy(hydrationGlasses = newCount))
        }
    }

    fun removeHydrationGlass(day: DayOfWeekSchedule) {
        _daysSchedule.update { map ->
            val current = map[day] ?: return@update map
            val newCount = (current.hydrationGlasses - 1).coerceAtLeast(0)
            map + (day to current.copy(hydrationGlasses = newCount))
        }
    }

    fun toggleHabit(habitId: String) {
        _habits.update { list ->
            list.map {
                if (it.id == habitId) it.copy(isCompleted = !it.isCompleted) else it
            }
        }
    }

    fun selectMood(mood: MoodType) {
        _selectedMood.update { if (it == mood) null else mood }
    }

    fun toggleWeightUnit() {
        _bodyMetrics.update { it.copy(useLbs = !it.useLbs) }
    }

    fun logNewWeight(newWeightKg: Float, dateLabel: String = "Today") {
        _bodyMetrics.update { it.copy(currentWeightKg = newWeightKg) }
        _weightHistory.update { list ->
            list + WeightPoint(dateLabel, newWeightKg)
        }
    }

    fun updateMeasurements(waist: Float, chest: Float, bodyFat: Float, muscleMass: Float) {
        _bodyMetrics.update {
            it.copy(
                waistInches = waist,
                chestInches = chest,
                bodyFatPercentage = bodyFat,
                muscleMassPercentage = muscleMass
            )
        }
    }

    fun addPersonalRecord(exerciseName: String, valueDisplay: String) {
        val newPr = PersonalRecord(
            id = "pr_${System.currentTimeMillis()}",
            exerciseName = exerciseName,
            valueDisplay = valueDisplay,
            dateLogged = "Today",
            badge = "NEW PR"
        )
        _personalRecords.update { listOf(newPr) + it }
    }

    fun nextQuote() {
        _currentQuoteIndex.update { (it + 1) % quotes.size }
        (currentQuote as MutableStateFlow).value = quotes[_currentQuoteIndex.value]
    }
}
