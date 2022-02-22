package com.amoukaled.androidcodechallenge.utils

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtils {

    /**
     * Shows a Material alert dialog.
     */
    fun showAlertDialog(
        context: Context, title: String, message: String,
        negativeText: String, negativeCallback: (DialogInterface, Int) -> Unit,
        positiveText: String, positiveCallback: ((DialogInterface, Int) -> Unit),
        @DrawableRes background: Int?,
        @DrawableRes icon: Int?
    ) {
        MaterialAlertDialogBuilder(context).apply {
            setTitle(title)
            setMessage(message)
            setNegativeButton(negativeText, negativeCallback)

            // positive
            setPositiveButton(
                positiveText,
                positiveCallback
            )

            // background
            background?.let {
                setBackground(
                    AppCompatResources.getDrawable(
                        context, background
                    )
                )
            }

            icon?.let {
                setIcon(
                    AppCompatResources.getDrawable(
                        context, icon
                    )
                )
            }

            show()
        }
    }

}