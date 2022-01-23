package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase
    private lateinit var repository: RemindersLocalRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).build()

        repository = RemindersLocalRepository(database.reminderDao())
    }

    @After
    fun cleanUp() = database.close()

    @Test
    fun testInsertRetrieveData() = runBlocking {

        val reminder = ReminderDTO(
            "title",
            "description",
            "location",
            100.0,
            10.0
        )

        repository.saveReminder(reminder)

        val result = repository.getReminder(reminder.id)

        assertThat(result is Result.Success, `is`(true))

        val dataFromDb = (result as Result.Success).data

        assertThat(dataFromDb.id, `is`(reminder.id))
        assertThat(dataFromDb.title, `is`(reminder.title))
        assertThat(dataFromDb.description, `is`(reminder.description))
        assertThat(dataFromDb.location, `is`(reminder.location))
        assertThat(dataFromDb.latitude, `is`(reminder.latitude))
        assertThat(dataFromDb.longitude, `is`(reminder.longitude))
    }

    @Test
    fun testDeleteAllReminders() = runBlocking {
        val reminder = ReminderDTO(
            "title",
            "description",
            "location",
            100.0,
            10.0
        )

        repository.saveReminder(reminder)

        var allReminders = (repository.getReminders() as Result.Success).data
        assertThat(allReminders.size, `is`(1))

        repository.deleteAllReminders()
        allReminders = (repository.getReminders() as Result.Success).data
        assertThat(allReminders.size, `is`(0))
    }

    @Test
    fun testDataNotFound_returnError() = runBlocking {
        val result = repository.getReminder("-1")
        val error =  (result is Result.Error)
        assertThat(error, `is`(true))
    }

}