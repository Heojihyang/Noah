package com.example.noah.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.noah.R
import com.example.noah.databinding.FragmentWhatToDoBinding


class WhatToDo : Fragment(R.layout.fragment_what_to_do) {


    // 바인딩 객체 선언
    private var binding: FragmentWhatToDoBinding? = null

    lateinit var earthquakeLayout:LinearLayout
    lateinit var emergencyLayout:LinearLayout
    lateinit var articleLayout:LinearLayout
    lateinit var portalSiteLayout:LinearLayout


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWhatToDoBinding.inflate(inflater, container, false)
        val view = binding?.root

        //국민 재난 안전 포털 레이아웃 클릭
        binding?.portalSiteLayout?.setOnClickListener {
            onClickPortalSite()
        }

        //지진 레이아웃 펼치기 버튼 클릭시
        binding!!.imgMoreEarthquake.setOnClickListener {
            if (binding!!.layoutExpand1.visibility == View.VISIBLE) {
                binding!!.layoutExpand1.visibility = View.GONE
                binding!!.earthquakeLayout.setBackgroundResource(R.drawable.rounded_box_gray1)
                binding!!.imgMoreEarthquake.animate().setDuration(200).rotation(180f)
            } else {
                binding!!.layoutExpand1.visibility = View.VISIBLE
                binding!!.earthquakeLayout.setBackgroundResource(R.drawable.custom_rounded_box_gray1)
                binding!!.imgMoreEarthquake.animate().setDuration(200).rotation(0f)
            }
        }

        //비상사태 레이아웃 펼치기 버튼 클릭시
        binding!!.imgMoreEmergency.setOnClickListener {
            if (binding!!.layoutExpand2.visibility == View.VISIBLE) {
                binding!!.layoutExpand2.visibility = View.GONE
                binding!!.emergencyLayout.setBackgroundResource(R.drawable.rounded_box_gray1)
                binding!!.imgMoreEmergency.animate().setDuration(200).rotation(180f)
            } else {
                binding!!.layoutExpand2.visibility = View.VISIBLE
                binding!!.emergencyLayout.setBackgroundResource(R.drawable.custom_rounded_box_gray1)
                binding!!.imgMoreEmergency.animate().setDuration(200).rotation(0f)
            }
        }

        //대비 물품 레이아웃 펼치기 버튼 클릭시
        binding!!.imgMoreArticle.setOnClickListener {
            if (binding!!.layoutExpand3.visibility == View.VISIBLE) {
                binding!!.layoutExpand3.visibility = View.GONE
                binding!!.articleLayout.setBackgroundResource(R.drawable.rounded_box_gray1)
                binding!!.imgMoreArticle.animate().setDuration(200).rotation(180f)
            } else {
                binding!!.layoutExpand3.visibility = View.VISIBLE
                binding!!.articleLayout.setBackgroundResource(R.drawable.custom_rounded_box_gray1)
                binding!!.imgMoreArticle.animate().setDuration(200).rotation(0f)
            }
        }



        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // 바인딩 객체 해제
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }

    //국민 재난 안전 포털 레이아웃 클릭 함수
    fun onClickPortalSite() {
        Log.i("whatTodo", "onClickPortalSite")
        Toast.makeText(context, "국민 재난 안전 포털", Toast.LENGTH_SHORT).show()
        //웹 페이지 열기 위한 intent
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.safekorea.go.kr/"))
        startActivity(intent)
    }
}