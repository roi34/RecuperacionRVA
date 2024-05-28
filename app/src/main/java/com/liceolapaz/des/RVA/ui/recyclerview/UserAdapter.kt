package com.liceolapaz.des.RVA.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liceolapaz.des.RVA.R
import com.liceolapaz.des.RVA.models.User



class UserAdapter(
    private val usersList: List<User>,
    private val onClickListener: (User) -> Unit
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(layoutInflater.inflate(R.layout.user_rv, parent, false))
    }

    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        val item = usersList[position]
        viewHolder.bind(item, onClickListener)
    }


    override fun getItemCount(): Int = usersList.size
}

