package com.liceolapaz.des.RVA.ui.views

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.liceolapaz.des.RVA.R
import com.liceolapaz.des.RVA.constants.Credentials
import com.liceolapaz.des.RVA.database.DbManager
import com.liceolapaz.des.RVA.util.Dialog
import com.liceolapaz.des.RVA.util.StringExtensions.Companion.isValidName
import com.liceolapaz.des.RVA.util.StringExtensions.Companion.isValidPassword

class ModifyUserActivity : AppCompatActivity() {
    private lateinit var actionBarTextView: TextView

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var language: Spinner
    private lateinit var age: EditText
    private lateinit var name: EditText

    private lateinit var acceptButton: Button
    private lateinit var cancelButton: Button
    private lateinit var deleteButton: Button

    private lateinit var dbManager: DbManager

    private val context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()

        dbManager = DbManager.getInstance(context)

        val spinnerAdapter =
            ArrayAdapter.createFromResource(
                this,
                R.array.languages,
                R.layout.spinner_item
            )

        spinnerAdapter.setDropDownViewResource(
            R.layout.spinner_dropdown_item
        )

        language.adapter = spinnerAdapter

        val languagesArray = resources.getStringArray(R.array.languages)

        when (intent.getStringExtra("language")) {
            languagesArray[0] -> language.setSelection(0)
            languagesArray[1] -> language.setSelection(1)
            languagesArray[2] -> language.setSelection(2)
        }

        acceptButton.setOnClickListener {
            createAcceptDialog()
        }

        cancelButton.setOnClickListener {
            createCancelDialog()
        }

        deleteButton.setOnClickListener {
            createDeleteDialog()
        }
    }

    private fun initUI() {
        setContentView(R.layout.activity_modify_user)

        actionBarTextView = findViewById(R.id.tvActionBar)
        email = findViewById(R.id.txtEmail)
        password = findViewById(R.id.txtPassword)
        language = findViewById(R.id.spinnerLanguage)
        age = findViewById(R.id.txtAge)
        name = findViewById(R.id.txtName)

        acceptButton = findViewById(R.id.btnAccept)
        cancelButton = findViewById(R.id.btnCancel)
        deleteButton = findViewById(R.id.btnDelete)

        email.setText(intent.getStringExtra("email"))
        password.setText(intent.getStringExtra("password"))
        age.setText(intent.getStringExtra("age"))
        name.setText(intent.getStringExtra("name"))

        actionBarTextView.text =
            getString(R.string.modify_user_action_bar_message, name.text, email.text)
    }

    private fun createAcceptDialog() {
        if (email.text.toString() == Credentials.ADMIN_USER) {
            Dialog.createCustomDialog(
                context,
                getString(R.string.error),
                getString(R.string.admin_user_modify_error),
                null,
                null,
                null,
                null,
                getString(R.string.yes)
            ) {}
        } else {
            if (password.text.isNotEmpty() && age.text.isNotEmpty() && name.text.isNotEmpty()) {
                if (password.text.isValidPassword()) {
                    if (name.text.isValidName()) {
                        createCorrectInfoAcceptDialog()
                    } else {
                        Dialog.createNameErrorDialog(context)
                    }
                } else {
                    Dialog.createPasswordErrorDialog(context)
                }
            } else {
                Dialog.createMissingFieldsErrorDialog(context)
            }
        }
    }

    private fun createCorrectInfoAcceptDialog() {
        Dialog.createCustomDialog(context,
            getString(R.string.accept),
            getString(R.string.acceptMessage),
            getString(R.string.cancel),
            {},
            getString(R.string.no),
            {
                val intent = Intent(context, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            },
            getString(R.string.yes),
            {
                val userInfo = ContentValues().apply {
                    put("password", password.text.toString())
                    put("language", language.selectedItem.toString())
                    put("age", age.text.toString().toInt())
                    put("name", name.text.toString())
                }

                dbManager.updateUser(email.text.toString(), userInfo)

                val intent = Intent(context, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            })
    }

    private fun createCancelDialog() {
        Dialog.createCustomDialog(context,
            getString(R.string.cancel),
            getString(R.string.cancelMessage),
            null,
            null,
            getString(R.string.no),
            {},
            getString(R.string.yes),
            {
                val intent = Intent(context, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            })
    }

    private fun createDeleteDialog() {
        if (email.text.toString() == Credentials.ADMIN_USER) {
            Dialog.createCustomDialog(
                context,
                getString(R.string.error),
                getString(R.string.admin_user_delete_error),
                null,
                null,
                null,
                null,
                getString(R.string.yes)
            ) {}
        } else {
            Dialog.createCustomDialog(context,
                getString(R.string.delete),
                getString(R.string.deleteMessage),
                getString(R.string.cancel),
                {},
                getString(R.string.no),
                {
                    val intent = Intent(context, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                },
                getString(R.string.yes),
                {
                    dbManager.deleteUser(email.text.toString())

                    val intent = Intent(context, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                })
        }
    }
}