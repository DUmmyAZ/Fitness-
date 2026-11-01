package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.DayOfWeekSchedule
import com.example.data.repository.FitnessRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("ZoZo", appName)
    }

    @Test
    fun `fitness repository initializes all 7 days with exercises`() {
        val repo = FitnessRepository()
        val schedule = repo.daysSchedule.value
        assertEquals(7, schedule.size)
        assertTrue(schedule.containsKey(DayOfWeekSchedule.SAT))
        assertTrue(schedule.containsKey(DayOfWeekSchedule.SUN))
        assertTrue(schedule.containsKey(DayOfWeekSchedule.MON))
        assertTrue(schedule.containsKey(DayOfWeekSchedule.TUE))
        assertTrue(schedule.containsKey(DayOfWeekSchedule.WED))
        assertTrue(schedule.containsKey(DayOfWeekSchedule.THU))
        assertTrue(schedule.containsKey(DayOfWeekSchedule.FRI))

        val tuesday = schedule[DayOfWeekSchedule.TUE]
        assertNotNull(tuesday)
        assertTrue(tuesday!!.isRestDay)
    }
}
