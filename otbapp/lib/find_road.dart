import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:flutter_polyline_points/flutter_polyline_points.dart';

class NavigationPage extends StatelessWidget {
  const NavigationPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('길찾기'),
      ),
      body: const MyApp(),
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
  final LatLng _center = const LatLng(37.4739388, 127.03374); // 종열 어린이 공원

  final Set<Polyline> _polylines = {};
  List<LatLng> polylineCoordinates = [];
  late PolylinePoints polylinePoints;

  final TextEditingController _startLocationController = TextEditingController();
  final TextEditingController _destinationLocationController = TextEditingController();

  String errorMessage = ''; //에러 메시지를 저장할 변수

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
  }
  void showError(String message) {
    setState(() {
      errorMessage = message;
    });
    //에러 메시지를 일정 시간 후에 지우기 위해 타이머 추가
    Timer(const Duration(seconds: 3), () {
      setState(() {
        errorMessage = '';
      });
    });
  }

  Future<void> getPolylines() async {
    try {
      String apiKey = 'APIKEY';

      LatLng sourceLocation = await getLocationCoordinates(_startLocationController.text);
      LatLng destinationLocation = await getLocationCoordinates(_destinationLocationController.text);

      String drivingApiUrl =
          'https://maps.googleapis.com/maps/api/directions/json?origin=${sourceLocation.latitude},${sourceLocation.longitude}&destination=${destinationLocation.latitude},${destinationLocation.longitude}&mode=driving&key=$apiKey';

      final drivingResponse = await http.get(Uri.parse(drivingApiUrl));
      if (drivingResponse.statusCode == 200) {
        Map<String, dynamic> drivingData = jsonDecode(drivingResponse.body);
        List<dynamic> drivingRoutes = drivingData['routes'];
        if (drivingRoutes.isNotEmpty) {
          _polylines.clear();

          Map<String, dynamic> drivingRoute = drivingRoutes[0];
          Map<String, dynamic> drivingPolyline = drivingRoute['overview_polyline'];
          String drivingPoints = drivingPolyline['points'];

          polylineCoordinates = polylinePoints.decodePolyline(drivingPoints).cast<LatLng>();
          List<LatLng> convertedDrivingCoordinates = [];

          for (LatLng coordinate in polylineCoordinates) {
            convertedDrivingCoordinates.add(LatLng(coordinate.latitude, coordinate.longitude));
          }

          setState(() {
            Polyline drivingPolyline = Polyline(
              polylineId: const PolylineId('drivingPoly'),
              color: Colors.blue,
              points: convertedDrivingCoordinates,
              width: 3,
            );

            _polylines.add(drivingPolyline);
          });
        }
      }

      String transitApiUrl =
          'https://maps.googleapis.com/maps/api/directions/json?origin=${sourceLocation.latitude},${sourceLocation.longitude}&destination=${destinationLocation.latitude},${destinationLocation.longitude}&mode=transit&key=$apiKey';

      final transitResponse = await http.get(Uri.parse(transitApiUrl));
      if (transitResponse.statusCode == 200) {
        Map<String, dynamic> transitData = jsonDecode(transitResponse.body);
        List<dynamic> transitRoutes = transitData['routes'];
        if (transitRoutes.isNotEmpty) {
          Map<String, dynamic> transitRoute = transitRoutes[0];
          Map<String, dynamic> transitPolyline = transitRoute['overview_polyline'];
          String transitPoints = transitPolyline['points'];

          polylineCoordinates = polylinePoints.decodePolyline(transitPoints).cast<LatLng>();
          List<LatLng> convertedTransitCoordinates = [];

          for (LatLng coordinate in polylineCoordinates) {
            convertedTransitCoordinates.add(LatLng(coordinate.latitude, coordinate.longitude));
          }

          setState(() {
            Polyline transitPolyline = Polyline(
              polylineId: const PolylineId('transitPoly'),
              color: Colors.green,
              points: convertedTransitCoordinates,
              width: 3,
            );

            _polylines.add(transitPolyline);
          });
        }
      }
    } catch (e) {
      // Handle errors
      showError('정확한 장소를 입력해주세요.');
    }
  }


  Future<LatLng> getLocationCoordinates(String address) async {
    try {
      const apiKey = 'APIKEY';
      final encodedAddress = Uri.encodeComponent(address);

      final apiUrl = 'https://maps.googleapis.com/maps/api/geocode/json?address=$encodedAddress&key=$apiKey';
      final response = await http.get(Uri.parse(apiUrl));

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        print('Geocoding response: $data'); //디버깅용 출력

        final results = data['results'];

        if (results.isNotEmpty) {
          final location = results[0]['geometry']['location'];
          final double latitude = location['lat'];
          final double longitude = location['lng'];

          return LatLng(latitude, longitude);
        } else {
          throw Exception('No results found for the provided address');
        }
      } else {
        throw Exception('Failed to fetch location coordinates. Status code: ${response.statusCode}');
      }
    } catch (e) {
      print('Error in getLocationCoordinates: $e'); //디버깅용 출력
      rethrow; //에러를 상위로 전달
    }
  }

  @override
  void initState() {
    super.initState();
    polylinePoints = PolylinePoints();
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Stack(
          children: [
            GoogleMap(
              onMapCreated: _onMapCreated,
              initialCameraPosition: CameraPosition(
                target: _center,
                zoom: 11.0,
              ),
              polylines: _polylines,
            ),
            //에러 메시지 표시
            Positioned(
              top: 16,
              left: 16,
              right: 16,
              child: errorMessage.isNotEmpty
                  ? Container(
                padding: const EdgeInsets.all(8),
                color: Colors.red,
                child: Text(
                  errorMessage,
                  style: const TextStyle(color: Colors.white),
                ),
              )
                  : Container(),
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                children: [
                  TextField(
                    controller: _startLocationController,
                    decoration: const InputDecoration(labelText: '출발지'),
                  ),
                  TextField(
                    controller: _destinationLocationController,
                    decoration: const InputDecoration(labelText: '도착지'),
                  ),
                  ElevatedButton(
                    onPressed: getPolylines,
                    child: const Text('길찾기'),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
