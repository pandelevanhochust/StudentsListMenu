package com.example.teststudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var students: MutableList<StudentModel>
    private lateinit var studentAdapter: StudentAdapter
    private var lastDeletedStudent: StudentModel? = null
    private var lastDeletedPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        students = mutableListOf(
            StudentModel("Nguyễn Văn An", "SV001"),
            StudentModel("Trần Thị Bảo", "SV002"),
            StudentModel("Lê Hoàng Cường", "SV003"),
            StudentModel("Phạm Thị Dung", "SV004"),
            StudentModel("Đỗ Minh Đức", "SV005"),
            StudentModel("Vũ Thị Hoa", "SV006"),
            StudentModel("Hoàng Văn Hải", "SV007"),
            StudentModel("Bùi Thị Hạnh", "SV008"),
            StudentModel("Đinh Văn Hùng", "SV009"),
            StudentModel("Nguyễn Thị Linh", "SV010"),
            StudentModel("Phạm Văn Long", "SV011"),
            StudentModel("Trần Thị Mai", "SV012"),
            StudentModel("Lê Thị Ngọc", "SV013"),
            StudentModel("Vũ Văn Nam", "SV014"),
            StudentModel("Hoàng Thị Phương", "SV015"),
            StudentModel("Đỗ Văn Quân", "SV016"),
            StudentModel("Nguyễn Thị Thu", "SV017"),
            StudentModel("Trần Văn Tài", "SV018"),
            StudentModel("Phạm Thị Tuyết", "SV019"),
            StudentModel("Lê Văn Vũ", "SV020")
        )

        studentAdapter = StudentAdapter(
            students,
            onEdit = { student, position -> showEditStudentDialog(student, position) },
            onDelete = { student, position -> deleteStudent(student, position) }
        )

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun showEditStudentDialog(student: StudentModel, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.input_student_name)
        val idInput = dialogView.findViewById<EditText>(R.id.input_student_id)

        nameInput.setText(student.studentName)
        idInput.setText(student.studentId)

        AlertDialog.Builder(this)
            .setTitle("Edit Student")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = nameInput.text.toString()
                val id = idInput.text.toString()
                if (name.isNotEmpty() && id.isNotEmpty()) {
                    students[position] = StudentModel(name, id)
                    studentAdapter.notifyItemChanged(position)
                } else {
                    Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteStudent(student: StudentModel, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete ${student.studentName}?")
            .setPositiveButton("Delete") { _, _ ->
                lastDeletedStudent = student
                lastDeletedPosition = position
                students.removeAt(position)
                studentAdapter.notifyItemRemoved(position)
                showUndoSnackbar()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showUndoSnackbar() {
        val view = findViewById<View>(R.id.main)
        Snackbar.make(view, "Student deleted", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                lastDeletedStudent?.let {
                    students.add(lastDeletedPosition!!, it)
                    studentAdapter.notifyItemInserted(lastDeletedPosition!!)
                }
            }
            .show()
    }
}
