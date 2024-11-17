package com.example.teststudent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: MutableList<StudentModel>,
    private val onEdit: (StudentModel, Int) -> Unit,
    private val onDelete: (StudentModel, Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.text_student_name)
        val idTextView: TextView = itemView.findViewById(R.id.text_student_id)
        val editButton: ImageView = itemView.findViewById(R.id.image_edit)
        val deleteButton: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.nameTextView.text = student.studentName
        holder.idTextView.text = student.studentId

        // Edit Button Click
        holder.editButton.setOnClickListener {
            onEdit(student, position)
        }

        // Delete Button Click
        holder.deleteButton.setOnClickListener {
            onDelete(student, position)
        }
    }

    override fun getItemCount(): Int = students.size
}
