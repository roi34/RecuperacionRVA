package com.liceolapaz.des.RVA.util

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import com.liceolapaz.des.RVA.R


class Dialog {
    companion object {
        fun createCustomDialog(
            context: Context,
            title: String,
            message: String,
            neutralText: String?,
            neutralAction: (() -> Unit)?,
            negativeText: String?,
            negativeAction: (() -> Unit)?,
            positiveText: String,
            positiveAction: () -> Unit,
        ) {
            val builder = AlertDialog.Builder(context, R.style.CustomAlertDialogTheme)

            builder.setTitle(title)
            builder.setMessage(message)

            if (neutralAction != null) {
                builder.setNeutralButton(neutralText) { _, _ ->
                    neutralAction()
                }
            }

            if (negativeAction != null) {
                builder.setNegativeButton(negativeText) { _, _ ->
                    negativeAction()
                }
            }

            builder.setPositiveButton(positiveText) { _, _ ->
                positiveAction()
            }

            val dialog = builder.create()

            dialog.setOnShowListener {
                val colorInt = context.getColor(R.color.orange)
                val colorString = String.format("#%06X", 0xFFFFFF and colorInt)

                if (neutralText != null) {
                    dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                        ?.setTextColor(Color.parseColor(colorString))
                }
                if (negativeText != null) {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        ?.setTextColor(Color.parseColor(colorString))
                }
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    ?.setTextColor(Color.parseColor(colorString))
            }

            dialog.show()
        }

        fun createMissingFieldsErrorDialog(context: Context) {
            createCustomDialog(
                context,
                context.getString(R.string.error),
                context.getString(R.string.missing_fields),
                null,
                null,
                null,
                null,
                context.getString(R.string.yes)
            ) {}
        }

        fun createPasswordErrorDialog(context: Context) {
            createCustomDialog(
                context,
                context.getString(R.string.error),
                context.getString(R.string.password_error),
                null,
                null,
                null,
                null,
                context.getString(R.string.yes)
            ) {}
        }

        fun createNameErrorDialog(context: Context) {
            createCustomDialog(
                context,
                context.getString(R.string.error),
                context.getString(R.string.name_error),
                null,
                null,
                null,
                null,
                context.getString(R.string.yes)
            ) {}
        }
    }
}