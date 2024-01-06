import 'package:flutter/material.dart';

class SecondPage extends StatelessWidget {
  final String routeName;
  final String busId;
  final String arrivalMsg1;
  final String arrivalMsg2;

  SecondPage({
    required this.routeName,
    required this.busId,
    required this.arrivalMsg1,
    required this.arrivalMsg2,
  });


  List<String> findMatchingBusIds(String routeName) {
    // 더미데이터로 테스트 로직 추가해야함
    if (routeName == 'SampleRouteName') {
      return ['BusId1', 'BusId2', 'BusId3'];
    } else {
      return [];
    }
  }

  @override
  Widget build(BuildContext context) {

    List<String> matchingBusIds = findMatchingBusIds(routeName);

    return Scaffold(
      appBar: AppBar(
        title: Text('상세 정보'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('버스번호: $routeName', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            Text('BusID: $busId', style: TextStyle(fontSize: 16)),
            Text('도착정보 1: $arrivalMsg1', style: TextStyle(fontSize: 16)),
            Text('도착정보 2: $arrivalMsg2', style: TextStyle(fontSize: 16)),
            SizedBox(height: 16),
            Text('도착정류장:', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: matchingBusIds.map((matchingBusId) {
                return Text(matchingBusId, style: TextStyle(fontSize: 16));
              }).toList(),
            ),
          ],
        ),
      ),
    );
  }
}
