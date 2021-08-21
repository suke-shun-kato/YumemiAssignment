package xyz.goodistory.yumemiassignment.http

import android.content.Context
import android.widget.Toast
import xyz.goodistory.yumemiassignment.R

class HttpCommon {
    companion object {
        fun showApiErrorMessage(context: Context) {
            Toast.makeText(context, R.string.contributors_list_api_error_message, Toast.LENGTH_LONG).show()
        }
    }
}