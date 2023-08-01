package com.example.noah.ui.home

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noah.R
import androidx.navigation.fragment.findNavController
import com.example.noah.DBManager
import com.example.noah.MainActivity

class HomeFragment : Fragment(R.layout.fragment_home) {


    lateinit var recyclerView:RecyclerView
    lateinit var sqliteDB:SQLiteDatabase
    private lateinit var adapter:BoardAdapter

    private val dataList= mutableListOf<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_home, container, false)

        //리사이클러뷰 초기화
        recyclerView=view.findViewById(R.id.main_recycler_view)

        return view
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val writeButton = view.findViewById<Button>(R.id.main_write_button)
        writeButton.setOnClickListener {
            //글쓰기 화면으로 전환
            findNavController().navigate(R.id.navigation_boardWrite)
        }
        val whatToDoButton = view.findViewById<Button>(R.id.home_button_action)
        whatToDoButton.setOnClickListener {
            //조치사항 화면으로 전환
            findNavController().navigate(R.id.navigation_whatToDo)
        }
        val myButton = view.findViewById<Button>(R.id.main_My_button)
        myButton.setOnClickListener {
            //내 정보 화면으로 전환
            findNavController().navigate(R.id.navigation_my_profile)
        }


        val boradDbManager = DBManager(requireContext())

        //데이터 리스트에 커뮤니티 제목, 글 넣음
        sqliteDB=boradDbManager.readableDatabase
        val cursor:Cursor
        cursor=sqliteDB.rawQuery("SELECT * FROM board;",null)
        dataList.clear()
        while(cursor.moveToNext()) {
            val id=cursor.getLong(cursor.getColumnIndex("id"))
            val title = cursor.getString(cursor.getColumnIndex("title")).toString()
            val contents = cursor.getString(cursor.getColumnIndex("contents")).toString()

            dataList.add(HomeViewModel(id,title, contents))
            Log.d("dataList", dataList.toString())
        }

        cursor.close()
        sqliteDB.close()
        boradDbManager.close()

        val mActivity=activity as MainActivity
        adapter = BoardAdapter(dataList)

        //글 클릭했을 때 해당 글 데이터 bundle에 넣고 comment프래그먼트로 이동
        adapter.setItemClickListener(object: BoardAdapter.OnItemClickListner {
            override fun onClick(v:View,position: Int){
                var clickedItem=dataList[position]
                val itmeTitle=clickedItem.title
                val itemId=clickedItem.id
                val itemContents=clickedItem.contents
                //val intent=Intent(context,Comment::class.java)
                var fragment:Fragment=Comment()
                var bundle:Bundle=Bundle()
                if (itemId != null) {
                    bundle.putLong("itemId",itemId)
                }
                bundle.putString("itemTitle",itmeTitle)
                bundle.putString("itemContents",itemContents)
                //fragment.arguments=bundle

                mActivity.fragmentChange_for_adapter(fragment)
                Log.d("setItemClickListener","클릭 됨")
                findNavController().navigate(R.id.navigation_comment,bundle)

            }
        })

        //adapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        recyclerView.adapter = adapter

    }


}
