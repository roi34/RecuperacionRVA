package com.liceolapaz.des.RVA.ui.views

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.liceolapaz.des.RVA.R
import com.liceolapaz.des.RVA.database.DbManager
import com.liceolapaz.des.RVA.util.Dialog
import com.liceolapaz.des.RVA.util.StringExtensions.Companion.isValidEmail
import com.liceolapaz.des.RVA.util.StringExtensions.Companion.isValidName
import com.liceolapaz.des.RVA.util.StringExtensions.Companion.isValidPassword

class AddUserActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var language: Spinner
    private lateinit var age: EditText
    private lateinit var name: EditText

    private lateinit var acceptButton: Button
    private lateinit var cancelButton: Button

    private lateinit var dbManager: DbManager

    private val context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        dbManager = DbManager.getInstance(context)

        val spinnerAdapter = ArrayAdapter.createFromResource(
            this, R.array.languages,
            R.layout.spinner_item
        )
        spinnerAdapter.setDropDownViewResource(
            R.layout.spinner_dropdown_item
        )
        language.adapter = spinnerAdapter

        acceptButton.setOnClickListener {
            createAcceptDialogs()
        }

        cancelButton.setOnClickListener {
            createCancelDialog()
        }
    }

    private fun initUI() {
        setContentView(R.layout.activity_add_user)
        email = findViewById(R.id.txtEmail)
        password = findViewById(R.id.txtPassword)
        language = findViewById(R.id.spinnerLanguage)
        age = findViewById(R.id.txtAge)
        name = findViewById(R.id.txtName)
        acceptButton = findViewById(R.id.btnAccept)
        cancelButton = findViewById(R.id.btnCancel)
    }

    private fun createAcceptDialogs() {
        val user = dbManager.getUserByEmail(email.text.toString())

        if (email.text.isNotEmpty() && password.text.isNotEmpty() && age.text.isNotEmpty() && name.text.isNotEmpty()) {
            if (user != null) {
                createEmailAlreadyExistsErrorDialog()
            } else {
                if (email.text.isValidEmail()) {
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
                    createInvalidEmailErrorDialog()
                }
            }
        } else {
            Dialog.createMissingFieldsErrorDialog(context)
        }
    }

    private fun createCorrectInfoAcceptDialog() {
        Dialog.createCustomDialog(
            context,
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
                    put("email", email.text.toString())
                    put("password", password.text.toString())
                    put("language", language.selectedItem.toString())
                    put("age", age.text.toString().toInt())
                    put("name", name.text.toString())
                }

                dbManager.insertUserIntoDb(userInfo)

                val intent = Intent(context, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            })
    }

    private fun createInvalidEmailErrorDialog() {
        Dialog.createCustomDialog(
            context,
            getString(R.string.error),
            getString(R.string.invalid_email),
            null,
            null,
            null,
            null,
            getString(R.string.yes)
        ) {}
    }

    private fun createEmailAlreadyExistsErrorDialog() {
        Dialog.createCustomDialog(
            context,
            getString(R.string.error),
            getString(R.string.email_already_exists),
            null,
            null,
            null,
            null,
            getString(R.string.yes)
        ) {}
    }

    private fun createCancelDialog() {
        Dialog.createCustomDialog(
            context,
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
}