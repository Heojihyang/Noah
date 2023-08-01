package com.example.noah.ui.home


import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.noah.DBManager
import com.example.noah.R
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient


class BoardWrite() : Fragment() {

    lateinit var writeTitleEdit: EditText
    lateinit var writeContentsEdit: EditText
    lateinit var registButton: Button
    lateinit var sqlitedb:SQLiteDatabase

    var user_id: Long = 0
    var id:Long=0


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_board_write, container, false)

        writeTitleEdit = view.findViewById(R.id.title_edit)
        writeContentsEdit = view.findViewById(R.id.contents_edit)
        registButton = view.findViewById(R.id.regist_button)

        val boardDbManager = DBManager(requireContext())

        //회원번호 가져오기
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.me { user, error ->
                user_id = user?.id!!
                Log.d("login_o", user_id.toString())
            }
        }

        //등록 버튼 클릭
        registButton.setOnClickListener {

            //board_id값 랜덤으로 지정
            val random = (1..1000).random()
            id= random.toLong()

            //에디트 텍스트의 Title, contents 받기
            val strTitle = writeTitleEdit.text.toString().trim()
            val strContents = writeContentsEdit.text.toString().trim()



            Log.d("login_o", user_id.toString())
            sqlitedb=boardDbManager.writableDatabase
            if (strTitle.isNotEmpty() && strContents.isNotEmpty()) {
                // 데이터 삽입
                sqlitedb.execSQL("INSERT INTO board VALUES('"+id+"','"+user_id+"','"+strTitle+"','"+strContents+"');")
                Toast.makeText(context, "등록 완료", Toast.LENGTH_SHORT).show()
                Log.d("Write",user_id.toString())

            } else {
                Toast.makeText(context, "글을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            sqlitedb.close()

            //커뮤니티 메인 화면으로 전환
            findNavController().navigate(R.id.navigation_home)


        }
        return view
    }


}