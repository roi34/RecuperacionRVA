package com.liceolapaz.des.RVA.ui.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.liceolapaz.des.RVA.R
import com.liceolapaz.des.RVA.constants.Credentials
import com.liceolapaz.des.RVA.database.DbManager

class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var errorMessage: TextView
    private var counter = 0
    private lateinit var dbManager: DbManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbManager = DbManager.getInstance(this)

        dbManager.insertAdminUser()

        email = findViewById(R.id.txtEmail)
        password = findViewById(R.id.txtPassword)
        errorMessage = findViewById(R.id.tvErrorMessage)


        val addUserButton: Button = findViewById(R.id.btnLogin)
        addUserButton.setOnClickListener {
            checkPassword()
        }
    }

    private fun checkPassword() {
        if (email.text.toString() == Credentials.ADMIN_USER
            && password.text.toString() == Credentials.ADMIN_PASSWORD
        ) {
            errorMessage.text = ""
            counter = 0
            email.setText("")
            password.setText("")
            email.clearFocus()
            password.clearFocus()
            goToHome()
        } else {
            errorMessage.text = getString(R.string.login_error)
            counter++
            if (counter == 3) {
                finish()
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}