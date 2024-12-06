package com.app.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class InteractorRunner @Inject constructor() {
    operator fun <Result> invoke(
        job: CoroutineScope,
        action: suspend () -> Result,
        onError: (Exception) -> Unit = {},
        onSuccess: suspend (Result) -> Unit,
        preAction: suspend () -> Unit = {},
    ) = job.launch {
        try {
            preAction()
            onSuccess(action())
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e)
        }
    }
}
