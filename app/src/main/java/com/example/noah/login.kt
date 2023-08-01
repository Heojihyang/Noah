package com.example.noah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import android.widget.ImageView
import com.example.noah.ui.dashboard.DashboardFragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause

class login : AppCompatActivity() {
    //로그인 버튼
    private lateinit var loginButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //로그인 버튼 찾기
        loginButton = findViewById(R.id.loginButton)

        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("login_X", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    val kakaoId = user!!.id
                    Log.d("login", kakaoId.toString())
                }
                Log.i("login_o", "카카오계정으로 로그인 성공 ${token.accessToken}")

                // 로그인 성공 시 MainActivity로 이동하는 코드 추가
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                finish() // 로그인 액티비티를 종료하여 뒤로 가기로 다시 돌아가지 않도록 합니다.
            }
        }

            //로그인 필요
            // loginButton을 클릭했을 때 카카오톡 로그인을 시도하는 코드 추가
            loginButton.setOnClickListener {// 로그인 조합 예제

                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                        if (error != null) {
                            Log.e("login_X", "카카오톡으로 로그인 실패 : ${error}")

                            // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                            // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            } else {
                                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                            }
                            // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                            //UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                        } else if (token != null) {

                            Log.d("login_o"," idToken: ${token.idToken}")
                            Log.i("login_o", "카카오톡으로 로그인 성공 ${token.accessToken}")

                            // 사용자 정보 요청 (기본)
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.d("login_o", "사용자 정보 요청 실패: $error")
                                }
                                else if (user != null) {
                                    Log.d("login_o", "사용자 정보 요청 성공" +
                                            "\n회원번호: ${user.id}" +
                                            "\n이메일: ${user.kakaoAccount?.email}" +
                                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}")
                                }
                            }
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }
        }

}



