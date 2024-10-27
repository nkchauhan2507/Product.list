package com.develop.productlisting.ui

interface ProgressIndicator {
    fun showProgressbar(mode : ProgressMode)
    fun dismissProgressbar(mode : ProgressMode,message : String = "")
}

enum class ProgressMode {
    SHOW_DIALOG,
    DISMISS_DIALOG
}