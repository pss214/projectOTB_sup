import 'dart:convert';
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fluttertest/Bus_Arrival_Page.dart';
import 'package:fluttertest/Bus_Stop_Model.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:location/location.dart';


void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: BusReservePage(),
    );
  }
}

class BusReservePage extends StatefulWidget {
  @override
  _MyMapState createState() => _MyMapState();
}

class _MyMapState extends State<BusReservePage> {
  late GoogleMapController mapController;
  Location location = Location();
  Set<Marker> markers = <Marker>{};

  Future<List<BusStops>> loadBusStopsFromJson() async {
    String jsonData = await rootBundle.loadString('assets/json/BusStopList.json');

    Map<String, dynamic> jsonDataMap = json.decode(jsonData);
    List<dynamic> dataList = jsonDataMap['DATA'];
    List<BusStops> busStops = dataList.map((json) => BusStops.fromJson(json)).toList();

    return busStops;
  }

  @override
  void initState(){
    super.initState();
    _loadMarkers();
  }
  //내 범위내 마커값 가져오기.  범위 수정할려면 저기 500 수정하면 됩니다.
  void _loadMarkers() async {
    List<BusStops>busStops=await loadBusStopsFromJson();
    var currentLocation=await location.getLocation();

    List<Marker> JsonMarkers= busStops.where((busStop)=>
    calculateDistance(
        currentLocation.latitude ?? 0.0,
        currentLocation.longitude ?? 0.0,
        double.parse(busStop.ycode),
        double.parse(busStop.xcode)) <= 500).map((busStop){
          return Marker(
            markerId: MarkerId(busStop.stopNo),
            position: LatLng(double.parse(busStop.ycode), double.parse(busStop.xcode)),
            infoWindow: InfoWindow(title: busStop.stopNm),
            onTap: (){
              Navigator.push(
                context,MaterialPageRoute(builder:(context)=>
                  MyReserveApp(selectedStopNo: busStop.stopNo, selectedStopName:busStop.stopNm), // 마커 클릭시 마커값 넘겨주기
              ),
              );
            },
          );

    }).toList();

    //print('내 범위내 정류장 마커: ${nMarkers.length}');

    setState(() {
      markers=Set.from(JsonMarkers);
      //print("정류장 번호:${busStops[0].stopNm}");
    });


  }
  double radians(double degree)
  {
    return degree*(pi/180);
  }

  double calculateDistance(double startLat, double startLng, double endLat, double endLng){
    // 지구 반지름 아직도 왜 필요한지는 모르겠는데 정확한 위도경도값을 위해 필요하다고 하는데 이미 정확한값을줘도 필요하다네요 오차범위 줄일려면
    const int eRadius=6371000;

    double dLat=radians(endLat-startLat);
    double dLng=radians(endLng - startLng);

    //허슬법칙(Haversine) 사용, 구면 삼각법. 위도와 경도를 사용하여 두 지점간 거리 계산
    //위도경도간 차이를 계산, 중심각도의 반 구함
    double haversineA =
        sin(dLat/2)*sin(dLat/2)+cos(radians(startLat))*cos(radians(endLat))*sin(dLng/2)*sin(dLng/2);

    //중심각도 값을 이용 해 두지점간 직전거리 계산
    double haversineC =
        2*atan2(sqrt(haversineA ),sqrt(1-haversineA));

    return eRadius*haversineC ;
  }

  //지도 생성시에 할 작업, 내위치로 이동하는기능까지 해버리면 지도생성시 이벤트랑 꼬여서 마커 출력 안됨.
  void _onMapCreated(GoogleMapController controller) {
    setState(() {
      mapController = controller;
      _getCurrentLocation();
      _loadMarkers();
    });
  }
  //내위치 가져오기
  void _getCurrentLocation() async {
    try {
      var currentLocation = await location.getLocation();
      print('현재 내위치: $currentLocation'); // 현재 위치 출력
      mapController.animateCamera(
        CameraUpdate.newCameraPosition(
          CameraPosition(
            target: LatLng(
              currentLocation.latitude ?? 0.0,
              currentLocation.longitude ?? 0.0,
            ),
            zoom: 15.0,
          ),
        ),
      );
    } catch (e) {
      print("내 위치값을 가져오지 못했습니다.: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Stop Select'),
      ),
      body: GoogleMap(
        onMapCreated: _onMapCreated,
        initialCameraPosition: CameraPosition(
          target: LatLng(37.7749, -122.4194),
          zoom: 15.0,
        ),
        myLocationEnabled: true,
        compassEnabled: true,
        markers: markers,
      ),
    );
  }
}