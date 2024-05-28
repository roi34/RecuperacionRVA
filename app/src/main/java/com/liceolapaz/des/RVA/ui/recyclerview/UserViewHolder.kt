package com.liceolapaz.des.RVA.ui.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.liceolapaz.des.RVA.R
import com.liceolapaz.des.RVA.models.User
import com.liceolapaz.des.RVA.util.StringExtensions.Companion.getLocale


class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var name: TextView = view.findViewById(R.id.rvName)
    private var language: TextView = view.findViewById(R.id.rvLanguage)
    private var age: TextView = view.findViewById(R.id.rvAge)
    private val context = view.context

    fun bind(user: User, onClickListener: (User) -> Unit) {
        name.text = user.name
        language.text = user.language.getLocale()
        age.text = context.getString(R.string.user_age, user.age)
        itemView.setOnClickListener {
            onClickListener(user)
        }
    }
}