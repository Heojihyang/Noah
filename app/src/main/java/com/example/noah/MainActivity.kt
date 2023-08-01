package com.example.noah

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.noah.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.common.util.Utility
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.noah.ui.dashboard.DashboardFragment
import com.example.noah.ui.home.Comment
import com.example.noah.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)

        // 토큰 있는지 확인
        if (AuthApiClient.instance.hasToken()) {
            // Validate the token
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {

                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        // 토큰이 유효하지 않은 경우 -> 로그인 화면으로 이동
                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        // 기타 에러 처리
                            // 토큰이 유효하지 않은 경우 -> 로그인 화면으로 이동
                            val intent = Intent(this, login::class.java)
                            startActivity(intent)
                            finish()
                    }
                } else {
                    // 토큰 유효성 체크 성공(필요 시 토큰 갱신됨) -> 홈 화면으로 이동
                    val manager:FragmentManager=supportFragmentManager
                    val transaction:FragmentTransaction=manager.beginTransaction()
                    transaction.replace(R.id.frameLayout, DashboardFragment()).commit()
                }
            }
        } else {
            // 토큰이 없는 경우 -> 로그인 화면으로 이동
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)



    }

    fun fragmentChange_for_adapter(frag: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, frag).commit()
    }
}

