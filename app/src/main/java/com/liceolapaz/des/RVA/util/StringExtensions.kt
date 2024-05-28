package com.liceolapaz.des.RVA.util

import android.util.Patterns

class StringExtensions {
    companion object {
        fun CharSequence?.isValidEmail(): Boolean {
            if (this.isNullOrEmpty()) {
                return false
            }
            return Patterns.EMAIL_ADDRESS.matcher(this).matches()
        }

        fun CharSequence?.isValidPassword(): Boolean {
            return !(this.isNullOrEmpty() || this.length < 5)
        }

        fun CharSequence?.isValidName(): Boolean {
            return !(this.isNullOrEmpty() || this.length < 3)
        }

        fun CharSequence?.getLocale(): String? {
            val regex = "\\((.*?)\\)".toRegex()
            val matchResult = regex.find(this ?: return null)
            return matchResult?.groupValues?.get(1)
        }
    }
}