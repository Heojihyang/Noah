package com.example.noah

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.noah.ui.dashboard.DashboardFragment


class SetAddress : AppCompatActivity() {
    lateinit var mEtAddress: EditText
    lateinit var button_setAddress: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_address)

        mEtAddress = findViewById(R.id.et_address)
        button_setAddress = findViewById(R.id.button_set)

        //block touch
        mEtAddress.isFocusable ?: false
        mEtAddress.setOnClickListener {
            //주소 검색 웹뷰 화면으로 이동
            val intentSear = Intent(this@SetAddress, SearchRoadActivity::class.java)
            getSearchResult.launch(intentSear)
        }

        // 확인 버튼 누르면 마이프로필 화면으로 다시 돌아가기
        button_setAddress.setOnClickListener() {
            //bundle에 넣기
            var bundle=Bundle()
            bundle.putString("Address",mEtAddress.text.toString())
            Log.d("getAddress", "Address_pre: ${mEtAddress.text}")

            val fragmentDash=DashboardFragment()
            fragmentDash.arguments=bundle

            val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.map_view, fragmentDash)
            transaction.commit()

//            val intentMap = Intent(this, DashboardFragment::class.java)
//            intentMap.putExtra("address", mEtAddress.text)
//            setResult(Activity.RESULT_OK, intentMap)
//            finish()
//            startActivity(intentMap)

            //마이프로필로 다시 이동
            val intentProf = Intent(this, MyProfile::class.java)
            startActivity(intentProf)
            getSearchResult.launch(intentProf)
        }
    }

    private val getSearchResult = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        //SearchRoad Activity로부터의 결과 값이 이곳으로 전달
        if (result.resultCode == RESULT_OK) {
            if (result.data != null) {
                val data = result.data!!.getStringExtra("data")
                mEtAddress.setText(data)
            }
        }
    }

}