package com.liceolapaz.des.RVA.ui.views

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liceolapaz.des.RVA.R
import com.liceolapaz.des.RVA.models.User
import com.liceolapaz.des.RVA.ui.recyclerview.UserAdapter
import com.liceolapaz.des.RVA.database.DbManager

class HomeActivity : AppCompatActivity() {
    private var usersList: List<User> = mutableListOf()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        usersList = DbManager.getInstance(this).getAllUsers()
        initRecyclerView()

        val counter: TextView = findViewById(R.id.counter)
        counter.text = usersList.size.toString()

        val addUserButton: ImageButton = findViewById(R.id.addUser)
        addUserButton.setOnClickListener {
            goToAddUser()
        }
    }

    private fun goToAddUser() {
        val intent = Intent(this, AddUserActivity::class.java)
        startActivity(intent)
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        val recyclerView = findViewById<RecyclerView>(R.id.rvUser)
        adapter = UserAdapter(usersList) { user ->
            goToUserDetail(user)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(decoration)
    }

    private fun goToUserDetail(user: User) {
        val intent = Intent(this, ModifyUserActivity::class.java)
        intent.putExtra("email", user.email)
        intent.putExtra("password", user.password)
        intent.putExtra("language", user.language)
        intent.putExtra("age", user.age.toString())
        intent.putExtra("name", user.name)
        startActivity(intent)
    }
}


