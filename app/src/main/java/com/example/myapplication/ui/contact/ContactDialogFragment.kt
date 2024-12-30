package com.example.myapplication.ui.contact

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R

class ContactDialogFragment(private val onContactAdded: (name: String, phone: String) -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_contact_popup, container, false)

        val nameInput = view.findViewById<EditText>(R.id.dialogName)
        val phoneInput = view.findViewById<EditText>(R.id.dialogPhone)
        val saveButton = view.findViewById<Button>(R.id.btn_save)

        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            val phone = phoneInput.text.toString()

            if (name.isBlank() || phone.isBlank()) {
                Toast.makeText(requireContext(), "이름과 전화번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                onContactAdded(name, phone)
                dismiss() // 팝업 닫기
            }
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCancelable(true) // 팝업 외부를 터치하면 닫힙니다.
        }
    }
}
