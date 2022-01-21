package com.dh.gymhelper.presentation.extensions

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.dh.gymhelper.domain.error.ErrorModel
import com.dh.gymhelper.domain.error.ViewError
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

fun Fragment.showErrorDialog(error: ViewError, cancelable: Boolean = true, dismissAction: (() -> Unit)? = null) {
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle(error.title)
    builder.setMessage(error.message)
    builder.setPositiveButton("Ok") { _, _ ->
        ViewErrorController.isShowingError = false
    }
    builder.setOnDismissListener {
        ViewErrorController.isShowingError = false
        dismissAction?.invoke()
    }
    if (!ViewErrorController.isShowingError) {
        ViewErrorController.isShowingError = true
        val dialog = builder.show()
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(cancelable)
    }
}

var jsonObj: String? = null
fun ErrorModel.mapToViewError(): ViewError {
    return when (this) {
        is ErrorModel.Http.Forbidden,
        is ErrorModel.Http.Unauthorized -> {
            ViewError(
                title = "Unauthorized",
                message = "No bearer token provided",
            )
        }
        is ErrorModel.Http.ServerError -> {
            val message = try {
                this.errorBody ?: "Error"
            } catch (e: Throwable) {
                "Unknown Error"
            }
            ViewError(
                title = "Server Error",
                message = message
            )
        }
        is ErrorModel.Http.Custom -> {
            val message = try {
                this.errorBody ?: "Error"
            } catch (e: Throwable) {
                e.message!!
            }
            ViewError(
                title = "Server Error",
                message = message
            )
        }
        is ErrorModel.Http -> {
            ViewError(
                title = "Http",
                message = "Http error",
            )
        }
        is ErrorModel.Connection -> {
            ViewError(
                title = "Connection Error",
                message = "Some sort of connection error",
            )
        }
        is ErrorModel.DataError.ParseError -> {
            ViewError(
                title = "Parse Error",
                message = "No result data available"
            )
        }
        else -> {
            ViewError(
                title = "ABSOLUTELY UNKNOWN ERROR",
                message = "Something interesting happened",
            )
        }
    }
}

class ViewErrorController @Inject constructor() {
    companion object {
        var isShowingError = false
    }
}
