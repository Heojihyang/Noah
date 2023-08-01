package com.example.noah.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noah.R

class NotificationsFragment : Fragment() {


    // This property is only valid between onCreateView and
    // onDestroyView.

    val dataList = mutableListOf<NotifyModel>()
    lateinit var notifyAdapter : NotifyAdapter
    lateinit var notifyRecyclerView: RecyclerView

    //private var comment : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        var comment=arguments?.getString("comments")
        Log.d("_comment : ", comment.toString())
        dataList.add(NotifyModel(comment.toString()))
        Log.d("Notify comment: dataList", dataList.toString())

        val view=inflater.inflate(R.layout.fragment_notifications, container, false)
        notifyRecyclerView = view.findViewById(R.id.recyclerview_comment)

        // 댓글 목록을 로드하고 어댑터 설정
        notifyAdapter = NotifyAdapter(dataList)
        notifyRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        notifyRecyclerView.adapter = notifyAdapter

        return view
    }

   // fun getComment(comment1 : String)
   // {
   //     comment = comment1
   //     dataList.add(NotifyModel(comment))
   //     Log.d("Notify comment: dataList", dataList.toString())
   // }

}