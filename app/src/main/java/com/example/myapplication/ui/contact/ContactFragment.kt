package com.example.myapplication.ui.contact

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentContactBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private val firstContactList = mutableListOf<ContactFirstItem>()
    private lateinit var firstAdapter: ContactFirstAdapter

    private val secondContactList = mutableListOf<ContactSecondItem>()
    private lateinit var secondAdapter: ContactSecondAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)

        initializeJsonFileIfNotExists("ContactFirst.json")
        initializeJsonFileIfNotExists("ContactSecond.json")

        setUpFirstRecyclerView()
        setUpSecondRecyclerView()
        setUpAddButton()
        setUpAddButton2()

        val textTitleView: TextView = binding.contactTitle
        val textSubtitleView: TextView = binding.contactSubtitle

        textTitleView.text = getString(R.string.title_contact)
        textSubtitleView.text = getString(R.string.subtitle_contact)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshFirstRecyclerView() // Fragment가 다시 표시될 때 데이터를 갱신
        refreshSecondRecyclerView()
    }

    private fun setUpFirstRecyclerView() {
        val fileName = "ContactFirst.json"
        val data = parseJsonData<ContactFirstItem>(fileName)

        // 기존 리스트에 데이터를 추가
        firstContactList.clear()
        firstContactList.addAll(data ?: emptyList())

        // 어댑터 초기화
        firstAdapter = ContactFirstAdapter(requireContext(), firstContactList)
        binding.firstRecyclerView.adapter = firstAdapter

        // 레이아웃 매니저 설정
        binding.firstRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        refreshFirstRecyclerView()
        enableSwipeToDelete()
    }

    private fun refreshFirstRecyclerView() {
        val fileName = "ContactFirst.json"
        val data = parseJsonData<ContactFirstItem>(fileName)
        firstContactList.clear()
        firstContactList.addAll(data ?: emptyList())
        firstAdapter.notifyDataSetChanged() // 어댑터 갱신
    }

    private fun setUpSecondRecyclerView() {
        val fileName = "ContactSecond.json"
        val data = parseJsonData<ContactSecondItem>(fileName)

        secondContactList.clear()
        secondContactList.addAll(data ?: emptyList())

        secondAdapter = ContactSecondAdapter(requireContext(), secondContactList)
        binding.secondRecyclerView.adapter = secondAdapter

        binding.secondRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        refreshSecondRecyclerView()
        enableSwipeToDelete()
        enableSwipeToDelete2()
    }

    private fun refreshSecondRecyclerView() {
        val fileName = "ContactSecond.json"
        val data = parseJsonData<ContactSecondItem>(fileName)
        secondContactList.clear()
        secondContactList.addAll(data ?: emptyList())
        secondAdapter.notifyDataSetChanged() // 어댑터 갱신
    }

    private fun setUpAddButton() {
        binding.btnAdd.setOnClickListener {
            val popup = ContactDialogFragment { name, phone ->
                val newContact = ContactFirstItem(name, phone)
                firstContactList.add(newContact)
                firstAdapter.notifyItemInserted(firstContactList.size - 1)

                FileUtils.appendToJsonFile(requireContext(), "ContactFirst.json", newContact)
                Toast.makeText(requireContext(), "연락처가 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
            popup.show(parentFragmentManager, "ContactPopupFragment")
        }
    }

    private fun setUpAddButton2() {
        binding.btnAdd2.setOnClickListener {
            val popup = ContactDialogFragment { name, phone ->

                val newContact2 = ContactSecondItem(name, phone)
                secondContactList.add(newContact2)
                secondAdapter.notifyItemInserted(secondContactList.size - 1)

                FileUtils.appendToJsonFile2(requireContext(), "ContactSecond.json", newContact2)
                Toast.makeText(requireContext(), "연락처가 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
            popup.show(parentFragmentManager, "ContactPopupFragment")
        }
    }

    private fun initializeJsonFileIfNotExists(fileName: String) {
        val file = File(requireContext().filesDir, fileName)
        if (!file.exists()) {
            try {
                val inputStream = requireContext().assets.open(fileName)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                file.writeText(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private inline fun <reified T> parseJsonData(fileName: String): List<T>? {
        return try {
            val file = File(requireContext().filesDir, fileName) // 내부 저장소에서 읽기
            if (!file.exists()) return emptyList() // 파일이 없으면 빈 리스트 반환

            val jsonString = file.readText()
            val gson = Gson()
            val type = object : TypeToken<List<T>>() {}.type
            gson.fromJson<List<T>>(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun enableSwipeToDelete() {
        val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // 드래그 동작은 처리하지 않음
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // 스와이프된 항목의 위치 가져오기
                val position = viewHolder.adapterPosition

                // 삭제 처리
                val contact = firstContactList[position]
                deleteFirstContact(contact, position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView

                    // Paint 객체 생성
                    val paint = Paint().apply {
                        color = Color.RED
                        isAntiAlias = true // 둥근 모서리를 부드럽게
                    }

                    // 모서리 반지름 (10dp 변환)
                    val radius = 10 * recyclerView.context.resources.displayMetrics.density

                    // 배경의 RectF 정의
                    val rectF = RectF(
                        itemView.right + dX, // 배경의 왼쪽 (스와이프 거리만큼 확장)
                        itemView.top.toFloat(), // 배경의 위쪽
                        itemView.right.toFloat(), // 배경의 오른쪽
                        itemView.bottom.toFloat() // 배경의 아래쪽
                    )

                    // 둥근 배경 그리기
                    c.drawRoundRect(rectF, radius, radius, paint)

                    // 삭제 아이콘 설정
                    val deleteIcon = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
                    deleteIcon?.let {
                        val iconMargin = (itemView.height - it.intrinsicHeight) / 2
                        val iconTop = itemView.top + iconMargin
                        val iconBottom = iconTop + it.intrinsicHeight
                        val iconLeft = itemView.right - iconMargin - it.intrinsicWidth
                        val iconRight = itemView.right - iconMargin

                        it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        it.draw(c)
                    }
                }

                // 기본 동작 호출
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }

        // ItemTouchHelper 연결
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.firstRecyclerView)
    }

    private fun deleteFirstContact(contact: ContactFirstItem, position: Int) {
        // 리스트에서 항목 삭제
        firstContactList.removeAt(position)
        firstAdapter.notifyItemRemoved(position)

        // JSON 파일 업데이트
        FileUtils.updateJsonFile(requireContext(), "ContactFirst.json", firstContactList)

        // 사용자에게 알림
        Toast.makeText(requireContext(), "${contact.name}(이)가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
    }


    private fun enableSwipeToDelete2() {
        val swipeToDeleteCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    // 드래그 동작은 처리하지 않음
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    // 스와이프된 항목의 위치 가져오기
                    val position = viewHolder.adapterPosition

                    // 삭제 처리
                    val contact = secondContactList[position]
                    deleteSecondContact(contact, position)
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        val itemView = viewHolder.itemView

                        // Paint 객체 생성
                        val paint = Paint().apply {
                            color = Color.RED
                            isAntiAlias = true // 둥근 모서리를 부드럽게
                        }

                        // 모서리 반지름 (10dp 변환)
                        val radius = 10 * recyclerView.context.resources.displayMetrics.density

                        // 배경의 RectF 정의
                        val rectF = RectF(
                            itemView.right + dX, // 배경의 왼쪽 (스와이프 거리만큼 확장)
                            itemView.top.toFloat(), // 배경의 위쪽
                            itemView.right.toFloat(), // 배경의 오른쪽
                            itemView.bottom.toFloat() // 배경의 아래쪽
                        )

                        // 둥근 배경 그리기
                        c.drawRoundRect(rectF, radius, radius, paint)

                        // 삭제 아이콘 설정
                        val deleteIcon = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
                        deleteIcon?.let {
                            val iconMargin = (itemView.height - it.intrinsicHeight) / 2
                            val iconTop = itemView.top + iconMargin
                            val iconBottom = iconTop + it.intrinsicHeight
                            val iconLeft = itemView.right - iconMargin - it.intrinsicWidth
                            val iconRight = itemView.right - iconMargin

                            it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                            it.draw(c)
                        }
                    }

                    // 기본 동작 호출
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }


            }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.secondRecyclerView)
    }

    private fun deleteSecondContact(contact: ContactSecondItem, position: Int) {
        // 리스트에서 항목 삭제
        secondContactList.removeAt(position)
        secondAdapter.notifyItemRemoved(position)

        // JSON 파일 업데이트
        FileUtils.updateJsonFile2(requireContext(), "ContactSecond.json", secondContactList)

        // 사용자에게 알림
        Toast.makeText(requireContext(), "${contact.name}(이)가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
    }
}
