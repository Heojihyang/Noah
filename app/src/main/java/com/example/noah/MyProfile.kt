package com.example.noah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Switch
import com.kakao.sdk.user.UserApiClient

class MyProfile : AppCompatActivity() {

    private lateinit var logoutButton: LinearLayout
    private lateinit var deleteIdButton: LinearLayout
    private lateinit var notificationSwitch: Switch
    private lateinit var setAddress: LinearLayout
    //val notificationManager = this.getSystemService(this.NOTIFICATION_SERVICE) as NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        logoutButton = findViewById(R.id.logoutButton)
        deleteIdButton = findViewById(R.id.deleteIdButton)
        notificationSwitch = findViewById(R.id.notificationSwitch)
        setAddress = findViewById(R.id.set_Address)

        logoutButton.setOnClickListener {

            // 로그아웃
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e("logout", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i("logout", "로그아웃 성공. SDK에서 토큰 삭제됨")
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        deleteIdButton.setOnClickListener {

            // 회원탈퇴
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("unlink", "회원 탈퇴 실패", error)
                }
                else {
                    Log.i("unlink", "회원 탈퇴 성공. SDK에서 토큰 삭제 됨")
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }

        // 스위치 상태 변경 리스너를 설정합니다.
        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            // isChecked 값에 따라 알림 On/Off 기능을 처리하면 됩니다.
            if (isChecked) {
                // 알림을 켜는 처리를 여기에 구현합니다.
                // 예: 알림 기능을 활성화하는 코드
            } else {
                // 알림 취소
                //notificationManager.cancel(NOTIFICATION_ID)
            }
        }

        // set_Address LinearLayout을 클릭했을 때 클릭했을 때 주소지 변경 페이지로 이동
        setAddress.setOnClickListener {

            val intent = Intent(this, SetAddress::class.java)
            startActivity(intent)
            Log.d("intentAdd", "SetAddress intent 실행")
        }
    }

    companion object {
        private const val TAG = "MyProfileActivity"
    }
}





