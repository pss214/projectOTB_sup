import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:barcode_widget/barcode_widget.dart';

class TotalPage extends StatefulWidget {
  final String start;
  final String arrive;
  final String vehId;
  final String busId;

  TotalPage({
    required this.start,
    required this.arrive,
    required this.vehId,
    required this.busId,
  });

  @override
  _TotalPageState createState() => _TotalPageState(
    start: start,
    arrive: arrive,
    vehId: vehId,
    busId: busId,
  );
}

class _TotalPageState extends State<TotalPage> {
  final String start;
  final String arrive;
  final String vehId;
  final String busId;

  _TotalPageState({
    required this.start,
    required this.arrive,
    required this.vehId,
    required this.busId,
  });

  // fetch Reserve가 예약 함수
  fetchReserve() async {
    Map<String, String> headers = {
      "Content-Type": "application/json",
      "Authorization":
      "otb eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWsxMDE3MjpST0xFX1VTRVIiLCJpc3MiOiJzc3A2OTU5NyIsImlhdCI6MTcwNDQzNzQ2NywiZXhwIjoxNzA0NDQ4MjY3fQ.31C9RQ-TaxIgPXCxgh_3RLUk7EeMXPSxpbYLDajNQ3Qmp46zYViCzKVMPYRPi2I5lLhgkkScFPlnsZPeyyhzdg"
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
    // Concatenate or format your information into a single string
    String qrData = "$start\n$arrive\n$busId\n$vehId";

    return Scaffold(
      appBar: AppBar(
        title: Text('버스 탑승 선결제 정보입니다.'),
        backgroundColor: Colors.orangeAccent,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            BarcodeWidget(
              barcode: Barcode.qrCode(),
              color: Colors.black,
              data: qrData,
              width: 200.0,
              height: 200.0,
            ),

            Text('출발 정류장: $start'),
            Text('도착 정류장: $arrive'),
            Text('차량번호: $busId'),
            Text('차량고유번호: $vehId'),

            SizedBox(
              width: double.infinity,
              child: TextButton(
                onPressed: () {
                  fetchReserve();
                },
                child: Text("통신상태확인용"),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
