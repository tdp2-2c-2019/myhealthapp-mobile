package com.example.admin.models

import android.graphics.Color
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat

data class Authorization(
    @Expose val id: Int,
    @Expose val title: String,
    @Expose @SerializedName("created_at") val date: String,
    @Expose val status: String,
    @Expose @SerializedName("created_by") val createdBy: User,
    @Expose @SerializedName("created_for") val createdFor: User
)

@BindingAdapter("bindServerDate")
fun bindServerDate(textView: TextView, date: String) {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    val formattedDate = format.parse(date)
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val dateString = formatter.format(formattedDate)
    textView.text = dateString
}

@BindingAdapter("bindFullName")
fun bindFullName(textView: TextView, user: User) {
    textView.text = user.firstName + " " + user.lastName
}

@BindingAdapter("bindAuthName")
fun bindAuthName(textView: TextView, auth: Authorization) {
    textView.text = auth.id.toString() + " - " + auth.title
}

@BindingAdapter("bindColor")
fun bindColor(textView: TextView, status: String) {
    when(status){
        "APROBADO" -> textView.setTextColor(Color.parseColor("#4CAF50"))
        "PENDIENTE" -> textView.setTextColor(Color.parseColor("#FF9800"))
        "RECHAZADO" -> textView.setTextColor(Color.parseColor("#F44336"))
    }
}