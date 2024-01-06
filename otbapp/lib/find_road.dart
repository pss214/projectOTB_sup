import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:location/location.dart';
import 'dart:convert';
import 'package:flutter/services.dart' show rootBundle;

class Bus_Stop{
  final String stop_nm;
  final String stop_no;
  final double xcode;
  final double ycode;

  Bus_Stop({required this.stop_nm, required this.stop_no,
            required this.xcode, required this.ycode});

}
class NavigationPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('길찾기'),
      ),
      body: MyApp(),
    );
  }
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  late GoogleMapController mapController;
  Location location = Location();
  Set<Marker> markers ={};

  @override
  void initState(){
    super.initState();
    _loadBusStops();
  }

  _onMapCreated(GoogleMapController controller) {
    setState(() {
      mapController = controller;
      _getCurrentLocation();
      _loadBusStops();
    });
  }

  _getCurrentLocation() async {
    try {
      var currentLocation = await location.getLocation();
      mapController.animateCamera(
        CameraUpdate.newCameraPosition(
          CameraPosition(
            target: LatLng(
              currentLocation.latitude ?? 0.0,
              currentLocation.longitude ?? 0.0,
            ),
            zoom: 11.0,
          ),
        ),
      );
    } catch (e) {
      print("위치값을 가져오지 못했습니다.: $e");
    }
  }

  _loadBusStops() async{
    String jsonString = await rootBundle.loadString('assets/Bus_Stop_List.json');
    List<dynamic>jsonList = json.decode(jsonString);

    List<Bus_Stop>busStops=jsonList.map((json){
      return Bus_Stop(
          stop_nm: json['stop_nm'],
          stop_no: json['stop_no'],
          xcode: json['xcode'],
          ycode: json['ycode']);
    }).toList();

    setState(() {
      markers=busStops.map((busStops){
        return Marker(
          markerId: MarkerId(busStops.stop_no),
          position: LatLng(busStops.xcode,busStops.ycode),
          infoWindow: InfoWindow(title: busStops.stop_nm),
        );
      }).toSet();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: GoogleMap(
        onMapCreated: _onMapCreated,
        initialCameraPosition: CameraPosition(
          target: LatLng(0.0, 0.0),
          zoom: 11.0,
        ),
        myLocationEnabled: true,
        compassEnabled: true,
        markers: markers,
      ),
    );
  }



}
