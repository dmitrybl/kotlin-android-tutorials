package ru.belyaev.dmitry.test

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(
    context: Context,
    private val workerParameters: WorkerParameters
): Worker(context, workerParameters) {

    companion object {
        private const val PAGE = "page"
        const val WORK_NAME = "work_name"

        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>()
                .apply {
                    setInputData(workDataOf(PAGE to page))
                    setConstraints(makeConstraints())
                }
                .build()
        }

        private fun makeConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiresCharging(true)
                .build()
        }
    }

    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0 until 10) {
            Thread.sleep(1000)
            log("Timer: page: $page result: $i")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyWorker: $message")
    }


}