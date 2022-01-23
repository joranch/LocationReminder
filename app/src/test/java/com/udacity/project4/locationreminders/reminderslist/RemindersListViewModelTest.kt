package com.udacity.project4.locationreminders.reminderslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
//@Config(sdk = [Build.VERSION_CODES.P])
class RemindersListViewModelTest : AutoCloseKoinTest() {

    private lateinit var fakeReminderDataSource: FakeDataSource
    private lateinit var viewModel: RemindersListViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setupViewModel() {
        fakeReminderDataSource = FakeDataSource()
        viewModel = RemindersListViewModel(
            ApplicationProvider.getApplicationContext(),
            fakeReminderDataSource
        )
    }

    @Test
    fun shouldReturnError() {
        runBlockingTest {
            fakeReminderDataSource.setShouldThrowException(true)
            saveReminder()
            viewModel.loadReminders()

            MatcherAssert.assertThat(
                viewModel.showSnackBar.value, CoreMatchers.`is`("Reminders not found")
            )
        }
    }

    @Test
    fun check_loading() = runBlockingTest {
        mainCoroutineRule.pauseDispatcher()
        saveReminder()
        viewModel.loadReminders()

        MatcherAssert.assertThat(viewModel.showLoading.value, CoreMatchers.`is`(true))
        mainCoroutineRule.resumeDispatcher()
        MatcherAssert.assertThat(viewModel.showLoading.value, CoreMatchers.`is`(false))
    }

    private suspend fun saveReminder() {
        fakeReminderDataSource.saveReminder(
            ReminderDTO("title",
                "description",
                "location",
                100.00,
                10.00)
        )
    }

}