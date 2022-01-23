package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun testInsertRetrieveData() = runBlockingTest {
        val reminder = ReminderDTO(
            "title",
            "description",
            "location",
            100.0,
            10.0
        )

        database.reminderDao().saveReminder(reminder)
        val items = database.reminderDao().getReminders()

        MatcherAssert.assertThat(items.size, `is`(1))

        val itemFromDb = items[0]

        MatcherAssert.assertThat(itemFromDb.title, `is`(reminder.title))
        MatcherAssert.assertThat(itemFromDb.id, `is`(reminder.id))
        MatcherAssert.assertThat(itemFromDb.latitude, `is`(reminder.latitude))
        MatcherAssert.assertThat(itemFromDb.longitude, `is`(reminder.longitude))
        MatcherAssert.assertThat(itemFromDb.description, `is`(reminder.description))
        MatcherAssert.assertThat(itemFromDb.location, `is`(reminder.location))
    }
}