package com.example.noah.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.noah.databinding.FragmentDashboardBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    var address: String? =""
    val geocoder = this.context?.let { Geocoder(it) }
    var list: List<Address>? = address?.let { geocoder?.getFromLocationName(it, 10) }
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //MapView 띄우기 : 가상기기에는 띄울 수 없는 기능으로 실제 기기를 연결하여 실행해야 함
        //해시 키를 kakao developers 사이트에서 등록해준 기기에서만 실행 가능
        val binding = FragmentDashboardBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val mapView = MapView(context)
        binding.mapView.addView(mapView)

//        val intentMap = Intent(this.context, SetAddress::class.java)
//        getAdressResult.launch(intentMap)

//        list = address?.let { geocoder!!.getFromLocationName(address!!, 10) }
//        val addressMap: Address = address?.let { geocoder!!.getFromLocationName(address!!, 10) }.get(0)
//        val addressMap: Address? = address?.let { geocoder?.getFromLocationName(it, 10)?.firstOrNull() }
        if (list != null) {
            val addressMap: Address = list!![0]
            val lat = addressMap.latitude
            val lon = addressMap.longitude

            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat, lon), true)
        }

//        val lat: Double = addressMap.getLatitude()
//        val lon: Double = addressMap.getLongitude()
//        if (list != null) {
//            val city = ""
//            val country = ""
//            if ((list as MutableList<Address>).size === 0) {
//                address_result.setText("올바른 주소를 입력해주세요. ")
//            } else {
//                val addressMap: Address = (list as MutableList<Address>).get(0)
//                val lat: Double = addressMap.getLatitude()
//                val lon: Double = addressMap.getLongitude()
//            }
//        }


        //마커 추가
        val apiData = TestApiData()
        val dataArr = apiData.getData()

        val markerArr = ArrayList<MapPOIItem>()
        for (data in dataArr!!) {
            val marker = MapPOIItem()
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(data.latitude!!, data.longitude!!)
            marker.itemName = data.value.toString()
            markerArr.add(marker)
        }
        mapView.addPOIItems(markerArr.toTypedArray())

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 돌려받은 resultCode가 정상인지 체크
        if(resultCode == Activity.RESULT_OK){
            val message = data?.getStringExtra("address")
            address=message
        }
    }

//    private val getAdressResult = registerForActivityResult<Intent, ActivityResult>(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result: ActivityResult ->
//        //SetAddress로부터의 결과 값이 이곳으로 전달
//        if (result.resultCode == AppCompatActivity.RESULT_OK) {
//            if (result.data != null) {
//                val data = result.data!!.getStringExtra("address")
//                address=data
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}