package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private var students = mutableListOf(
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(students)

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      addNewStudent()
    }
  }
  private fun addNewStudent() {
    val dialog = AlertDialog.Builder(this)
    val inflater = layoutInflater
    val dialogView = inflater.inflate(R.layout.dialog_add_student, null)
    val nameEditText = dialogView.findViewById<EditText>(R.id.edit_text_name)
    val idEditText = dialogView.findViewById<EditText>(R.id.edit_text_id)

    dialog.setView(dialogView)
    dialog.setTitle("Add new student")
    dialog.setPositiveButton("Add") { dialog, _ ->
      val name = nameEditText.text.toString()
      val id = idEditText.text.toString()
      students.add(StudentModel(name, id))
      studentAdapter.notifyDataSetChanged()
      dialog.dismiss()
    }
    dialog.show()
  }
  fun editStudent(position: Int) {
    val dialog = AlertDialog.Builder(this)
    val inflater = layoutInflater
    val dialogView = inflater.inflate(R.layout.dialog_edit_student, null)
    val nameEditText = dialogView.findViewById<EditText>(R.id.edit_text_name)
    nameEditText.setText(students[position].studentName)
    val idEditText = dialogView.findViewById<EditText>(R.id.edit_text_id)
    idEditText.setText(students[position].studentId)

    dialog.setView(dialogView)
    dialog.setTitle("Edit student")
    dialog.setPositiveButton("Save") { dialog, _ ->
      students[position].studentName = nameEditText.text.toString()
      students[position].studentId = idEditText.text.toString()
      studentAdapter.notifyDataSetChanged()
      dialog.dismiss()
    }
    dialog.setNegativeButton("Cancel") { dialog, _ ->
      dialog.dismiss()
    }
    dialog.show()
  }
  fun deleteStudent(position: Int) {
    val dialog = AlertDialog.Builder(this)
    dialog.setTitle("Delete student")
    dialog.setMessage("Are you sure you want to delete this student?")
    dialog.setPositiveButton("Yes") { dialog, _ ->
      val student = students.removeAt(position)
      studentAdapter.notifyDataSetChanged()
      Snackbar.make(findViewById(R.id.main), "Student deleted", Snackbar.LENGTH_LONG)
        .setAction("Undo") {
          students.add(position, student)
          studentAdapter.notifyDataSetChanged()
        }
        .show()
      dialog.dismiss()
    }
    dialog.setNegativeButton("No") { dialog, _ ->
      dialog.dismiss()
    }
    dialog.show()
  }
}