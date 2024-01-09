import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:location/location.dart';

class BusReservePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('버스 탑승하기'),
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
  LatLng _center = LatLng(0.0, 0.0);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: GoogleMap(
          onMapCreated: _onMapCreated,
          initialCameraPosition: CameraPosition(
            target: _center,
            zoom: 11.0,
          ),
          myLocationEnabled: true,
          compassEnabled: true,
        ),
      ),
    );
  }

  void _onMapCreated(GoogleMapController controller) {
    setState(() {
      mapController = controller;
      _getCurrentLocation();
    });
  }

  void _getCurrentLocation() async {
    try {
      var currentLocation = await location.getLocation();
      setState(() {
        _center = LatLng(
          currentLocation.latitude ?? 0.0,
          currentLocation.longitude ?? 0.0,
        );
      });

      mapController.animateCamera(
        CameraUpdate.newCameraPosition(
          CameraPosition(
            target: _center,
            zoom: 11.0,
          ),
        ),
      );
    } catch (e) {
      print("내 위치값을 가져오지 못했습니다.: $e");
    }
  }
}
