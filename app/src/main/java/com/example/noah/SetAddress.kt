package com.example.noah

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class SetAddress : AppCompatActivity() {
    lateinit var mEtAddress: EditText
    lateinit var button_setAddress: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_address)

        mEtAddress = findViewById(R.id.et_address)

        //block touch
        mEtAddress.isFocusable ?: false
        mEtAddress.setOnClickListener {
            //주소 검색 웹뷰 화면으로 이동
            val intent = Intent(this@SetAddress, SearchRoadActivity::class.java)
            getSearchResult.launch(intent)
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

                val intentMap = Intent()
                intentMap.putExtra("address", data)
                Log.d("getAddress", "getAddress: $data")
//                setResult(Activity.RESULT_OK, intentMap)
//                finish()
//                startActivity(intentMap)
            }
        }
    }

}