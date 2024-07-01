package com.searchnearbylocation.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.searchnearbylocation.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> safeAPICall(dispatcher: CoroutineDispatcher, apiCall: Response<T>): NetworkResult<T> {
    return withContext(dispatcher) {
        try {
            NetworkResult.Success(apiCall.body())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    NetworkResult.Error("IO Exception")
                }

                is HttpException -> {
                    val error = JSONObject(throwable.response()?.errorBody()?.charStream()?.readText() ?: "")
                    NetworkResult.Error(error.toString())
                }

                is CancellationException -> {
                    NetworkResult.Error("Cancellation Exception")
                }

                else -> {
                    NetworkResult.Error("Something went wrong.")
                }
            }
        }
    }
}

fun hideKeyBoard(activity: Activity) {
    val view: View = activity.findViewById(android.R.id.content)
    (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

fun showPermissionDialog(activity: Activity, title: String, message: String) {
    AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton(activity.getString(R.string.label_cancel), null)
        .create()
        .show()
}

fun showSnackBar(view: View, message: String, hasCancelButton: Boolean = false) {
    val showInContextUI = Snackbar.make(view, message, if (hasCancelButton) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_SHORT)
    if (hasCancelButton) {
        showInContextUI.setAction("Cancel") {
            showInContextUI.dismiss()
        }
    }
    showInContextUI.show()
}
