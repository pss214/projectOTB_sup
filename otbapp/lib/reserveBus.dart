import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:location/location.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Test'),
      ),
      body: GoogleMap(
        onMapCreated: _onMapCreated,
        initialCameraPosition: CameraPosition(
          target: LatLng(0.0, 0.0),
          zoom: 11.0,
        ),
        myLocationEnabled: true,
        compassEnabled: true,
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
      print("내위치값을 가져오지 못했습니다.: $e");
    }
  }
}