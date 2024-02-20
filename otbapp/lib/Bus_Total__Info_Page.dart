import 'package:flutter/material.dart';
import 'package:fluttertest/pay.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:barcode_widget/barcode_widget.dart';

class TotalPage extends StatefulWidget {
  final String start;
  final String startNm;
  final String arrive;
  final String arriveNm;
  final String vehId;
  final String busId;

  const TotalPage({super.key, 
    required this.start,
    required this.startNm,
    required this.arrive,
    required this.arriveNm,
    required this.vehId,
    required this.busId,
  });

  @override
  _TotalPageState createState() => _TotalPageState(
    start: start,
    startNm:startNm,
    arrive: arrive,
    arriveNm:arriveNm,
    vehId: vehId,
    busId: busId,
  );
}

class _TotalPageState extends State<TotalPage> {
  final String start;
  final String startNm;
  final String arrive;
  final String arriveNm;
  final String vehId;
  final String busId;

  _TotalPageState({
    required this.start,
    required this.arrive,
    required this.vehId,
    required this.busId,
    required this.startNm,
    required this.arriveNm,
  });

  // fetch Reserve가 예약 함수
  fetchReserve() async {
    Map<String, String> headers = {
      "Content-Type": "application/json",
      "Authorization":
      "otb eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWsxMDE3MjpST0xFX1VTRVIiLCJpc3MiOiJzc3A2OTU5NyIsImlhdCI6MTcwODMzODMwOCwiZXhwIjoxNzA4MzQ5MTA4fQ.jGY_XUbCwfLHZaKkpGV9VPTWHnuf8DP0kM_ceJLAPhEaiEZJEV2ihtmj4IdhcDoebOsEULyfrbw3AdzebcuN0A"
    };
    var data = {
      "depart_station": start,
      "arrive_station": arrive,
      "busnumber": busId,
      "busnumberplate": vehId
    };
    var url = Uri.parse('http://bak10172.asuscomm.com:10001/reservation');
    var response = await http.post(url, body: json.encode(data), headers: headers);
    print(response.statusCode); // 201출력되면 통신 성공
  }

  @override
  void initState() {
    super.initState();
    fetchReserve();
  }

  @override
  Widget build(BuildContext context) {
    //Concatenate or format your information into a single string
    //String qrData = "$start\n$arrive\n$busId\n$vehId";

    return Scaffold(
      appBar: AppBar(
        title: const Text('버스 탑승 정보.'),
        backgroundColor: Colors.orangeAccent,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // BarcodeWidget(
            //   barcode: Barcode.qrCode(),
            //   color: Colors.black,
            //   data: qrData,
            //   width: 200.0,
            //   height: 200.0,
            // ),
            //Text('출발 정류장: $start'),
            Text('출발 정류장: $startNm'),
            //Text('도착 정류장: $arrive'),
            Text('도착 정류장: $arriveNm'),
            Text('차량번호: $busId'),
            //Text('차량고유번호: $vehId'),

            SizedBox(
              width: double.infinity,
              child: TextButton(
                onPressed: () {
                  fetchReserve();
                  Navigator.push(context, MaterialPageRoute(builder: (context)=>PayMenu()));
                },
                child: const Text("결제 하기"),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
