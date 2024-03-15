import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:flutter_polyline_points/flutter_polyline_points.dart';
import 'dart:math' as math;
import 'package:location/location.dart' as location;


Set<Marker> _markers = {};

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
  final LatLng _center = const LatLng(37.4739388, 127.03374);//종열 어린이 공원

  final Set<Polyline> _polylines = {};
  List<LatLng> polylineCoordinates = [];
  late PolylinePoints polylinePoints;

  TextEditingController _startLocationController = TextEditingController();
  TextEditingController _destinationLocationController = TextEditingController();

  String errorMessage = '';//에러 메시지를 저장할 변수

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
  }

  void showError(String message) {
    setState(() {
      errorMessage = message;
    });
    //에러 메시지를 일정 시간 후에 지우기 위해 타이머 추가
    Timer(Duration(seconds: 3), () {
      setState(() {
        errorMessage = '';
      });
    });
  }

  // 내위치 가져오기
  void _getCurrentLocation() async {
    try {
      var currentLocation = await location.Location().getLocation();
      print('현재 내위치: $currentLocation');//현재 위치 출력

      if (currentLocation != null) {
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
      }
    } catch (e) {
      print("내 위치값을 가져오지 못했습니다.: $e");
    }
  }

  Future<void> getPolylines(String mode) async {
    try {
      String apiKey = 'AIzaSyBB5JHZ3NPkacXLiZXqPq3XvD_FmodCphs';

      LatLng sourceLocation = await getLocationCoordinates(_startLocationController.text);
      LatLng destinationLocation = await getLocationCoordinates(_destinationLocationController.text);

      Marker sourceMarker = Marker(
        markerId: MarkerId('sourceMarker'),
        position: sourceLocation,
        infoWindow: InfoWindow(title: '출발지'),
      );

      Marker destinationMarker = Marker(
        markerId: MarkerId('destinationMarker'),
        position: destinationLocation,
        infoWindow: InfoWindow(title: '도착지'),
      );

      setState(() {
        _markers.clear();
        _markers.add(sourceMarker);
        _markers.add(destinationMarker);
      });

      LatLngBounds bounds = calculateBounds();
      mapController.animateCamera(
        CameraUpdate.newLatLngBounds(bounds, 50.0),
      );

      String drivingApiUrl =
          'https://maps.googleapis.com/maps/api/directions/json?origin=${sourceLocation.latitude},${sourceLocation.longitude}&destination=${destinationLocation.latitude},${destinationLocation.longitude}&mode=$mode&key=$apiKey';

      final response = await http.get(Uri.parse(drivingApiUrl));
      if (response.statusCode == 200) {
        Map<String, dynamic> data = jsonDecode(response.body);
        List<dynamic> routes = data['routes'];
        if (routes.isNotEmpty) {
          Map<String, dynamic> route = routes[0];
          Map<String, dynamic> overviewPolyline = route['overview_polyline'];
          String points = overviewPolyline['points'];

          polylineCoordinates = polylinePoints.decodePolyline(points).cast<LatLng>();
          List<LatLng> convertedCoordinates = [];

          for (LatLng coordinate in polylineCoordinates) {
            convertedCoordinates.add(LatLng(coordinate.latitude, coordinate.longitude));
          }

          setState(() {
            Polyline newPolyline = Polyline(
              polylineId: PolylineId('newPoly'),
              color: mode == 'transit' ? Colors.green : Colors.blue,
              points: convertedCoordinates,
              width: 3,
            );

            _polylines.clear();
            _polylines.add(newPolyline);
          });

          if (mode == 'transit') {
            List<dynamic> legs = route['legs'];
            if (legs.isNotEmpty) {
              Map<String, dynamic> leg = legs[0];
              List<dynamic> steps = leg['steps'];
              if (steps.isNotEmpty) {
                String transitDetails = '';
                for (int i = 0; i < steps.length; i++) {
                  Map<String, dynamic> step = steps[i];
                  Map<String, dynamic>? transitDetailsMap = step['transit_details'];
                  if (transitDetailsMap != null) {
                    String lineName = transitDetailsMap['line']['name'];
                    String departureStop = transitDetailsMap['departure_stop']['name'];
                    String arrivalStop = transitDetailsMap['arrival_stop']['name'];
                    transitDetails += 'Take $lineName from $departureStop to $arrivalStop.\n';
                  }
                }
                showError(transitDetails);
              }
            }
          }
        }
      }
    } catch (e) {
      showError('길찾기 오류가 발생했거나 정확한 장소를 입력해주세요.');
    }
  }


  Future<LatLng> getLocationCoordinates(String address) async {
    try {
      final apiKey = 'AIzaSyBB5JHZ3NPkacXLiZXqPq3XvD_FmodCphs';
      final encodedAddress = Uri.encodeComponent(address);

      final apiUrl = 'https://maps.googleapis.com/maps/api/geocode/json?address=$encodedAddress&key=$apiKey';
      final response = await http.get(Uri.parse(apiUrl));

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        print('Geocoding response: $data');//디버깅용 출력

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
      print('Error in getLocationCoordinates: $e');//디버깅용 출력
      throw e;//에러를 상위로 전달
    }
  }

  LatLngBounds calculateBounds() {
    double minLat = double.infinity;
    double minLng = double.infinity;
    double maxLat = double.negativeInfinity;
    double maxLng = double.negativeInfinity;

    for (LatLng point in polylineCoordinates) {
      minLat = math.min(minLat, point.latitude);
      minLng = math.min(minLng, point.longitude);
      maxLat = math.max(maxLat, point.latitude);
      maxLng = math.max(maxLng, point.longitude);
    }

    return LatLngBounds(
      southwest: LatLng(minLat, minLng),
      northeast: LatLng(maxLat, maxLng),
    );
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
              markers: _markers,
            ),
            // 에러 메시지 표시
            Positioned(
              top: 16,
              left: 16,
              right: 16,
              child: errorMessage.isNotEmpty
                  ? Container(
                padding: EdgeInsets.all(8),
                color: Colors.red,
                child: Text(
                  errorMessage,
                  style: TextStyle(color: Colors.white),
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
                    decoration: InputDecoration(labelText: '출발지'),
                  ),
                  TextField(
                    controller: _destinationLocationController,
                    decoration: InputDecoration(labelText: '도착지'),
                  ),
                  ElevatedButton(
                    onPressed: () => getPolylines('driving'),
                    child: Text('자동차 길찾기'),
                  ),
                  ElevatedButton(
                    onPressed: () => getPolylines('transit'),
                    child: Text('대중교통 길찾기'),
                  ),
                  ElevatedButton(
                    onPressed: _getCurrentLocation,
                    child: Text('내 위치로 이동하기'),
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
