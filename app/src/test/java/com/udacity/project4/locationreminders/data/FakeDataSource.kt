package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

class FakeDataSource : ReminderDataSource {

    var reminderDTOList = mutableListOf<ReminderDTO>()
    private var throwException = false
    private val REQUESTED_EXCEPTION_MESSAGE = "Requested exception thrown"

    fun setShouldThrowException(value: Boolean) {
        throwException = value
    }

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        return try {
            if(throwException) throw Exception("Reminders not found")

            Result.Success(ArrayList(reminderDTOList))
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminderDTOList.add(reminder)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        return try {
            if(throwException) throw Exception("Reminders not found")

            val reminder = reminderDTOList.find { dto ->
                dto.id == id
            }

            if (reminder == null) {
                throw Exception("Reminders not found")
            } else {
                Result.Success(reminder)
            }
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

    override suspend fun deleteAllReminders() {
        reminderDTOList.clear()
    }
}