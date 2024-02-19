import 'package:flutter/material.dart';
import 'package:fluttertest/pay.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'Bus_Stop_Info.dart';
import 'Bus_Total__Info_Page.dart';

class SelectPage extends StatefulWidget {
  final String busNm; // routeName
  final String busId; // busrouteid
  final String busVehId; // vehId
  final String arrivalMsg1; // arrmsg1
  final String arrivalMsg2; // arrmsg2
  final String selectedStopNo;
  final String selectedStopNm;


  const SelectPage({super.key, 
    required this.busNm,
    required this.busId,
    required this.arrivalMsg1,
    required this.arrivalMsg2,
    required this.busVehId,
    required this.selectedStopNo,
    required this.selectedStopNm,


  });

  @override
  _SelectPageState createState() => _SelectPageState(
    busNm: busNm,
    busId: busId,
    busVehId: busVehId,
    selectedStopNo:selectedStopNo,
    selectedStopNm:selectedStopNm,
    selectedStop: '',
  );
}

class _SelectPageState extends State<SelectPage> {
  final String busId;
  final String busVehId;
  final String busNm;
  final String selectedStopNo;
  final String selectedStopNm;

  _SelectPageState(
      {
        required this.busNm, required this.busId,
        required this.busVehId, required this.selectedStopNo, required this.selectedStop, required this.selectedStopNm});

  List<BusStop> datas = [];
  String selectedStop = '';

  dynamic fetchBus() async {
    Map<String, String> headers = {
      "Content-Type": "application/json",
    };
    var data = {"busrouteid": busId, "stationid": selectedStopNo}; //
    var url = Uri.parse('http://bak10172.asuscomm.com:10001/bus/route-name');
    var response = await http.post(
        url, body: json.encode(data), headers: headers);

    if (response.statusCode == 200) {
      var dataObjsJson =
      jsonDecode(utf8.decode(response.bodyBytes))['data'][0] as List;
      final List<BusStop> parsedResponse =
      dataObjsJson.map((a) => BusStop.fromJson(a)).toList();
      setState(() {
        datas.clear();
        datas.addAll(parsedResponse);
      });
      print(dataObjsJson[0]);
      if (datas.isNotEmpty) {
        selectedStop = datas[0].arsId;
      }
      datas.remove(parsedResponse[0]);
    }
  }

  @override
  void initState() {
    super.initState();
    fetchBus();
  }

  List<List<BusStop>> chunkPages(List<BusStop> list, int chunkSize) {
    List<List<BusStop>> chunks = [];
    for (var i = 0; i < list.length; i += 7) {
      chunks.add(list.sublist(
          i,
          i + 7 > list.length
              ? list.length
              : i + 7));
    }
    return chunks;
  }

  @override
  Widget build(BuildContext context) {
    List<List<BusStop>> pages = chunkPages(datas, 7);

    return Scaffold(
      appBar: AppBar(
        title: const Text('도착 정류장 목록'),
        backgroundColor: Colors.orangeAccent,
      ),
      body: PageView.builder(
        itemCount: pages.length,
        itemBuilder: (context, pageIndex) {
          return SingleChildScrollView(
            child: Column(
              children: [
                const Padding(padding: EdgeInsets.only(top: 20)),
                Text(
                  "${pageIndex + 1}번째 페이지",
                  style: const TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const Text(
                  "좌우로 넘겨 정류장을 선택하세요.",
                  style: TextStyle(
                    fontSize: 10,
                    fontWeight: FontWeight.bold,
                    color: Colors.black,
                  ),
                ),
                SizedBox(
                  height: 1200,
                  child: ListView.builder(
                    itemCount: pages[pageIndex].length,
                    itemBuilder: (context, index) {
                      return Card(
                        child: ListTile(
                          onTap: () {
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => TotalPage(
                                      start: selectedStop,
                                      startNm: selectedStopNm,
                                      arrive: pages[pageIndex][index].arsId,
                                      arriveNm:pages[pageIndex][index].stationNm,
                                      vehId: busVehId,
                                      busId: busNm,
                                    ))
                            );
                          },
                          title: Text(pages[pageIndex][index].stationNm),
                          subtitle: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text("정류장번호:${pages[pageIndex][index].arsId}"),
                            ],
                          ),
                        ),
                      );
                    },
                  ),
                ),
                SizedBox(
                  width: double.infinity,
                  child: TextButton(
                    onPressed: (){
                      fetchBus();
                    },
                    child: const Text("통신상태확인용"),
                  ),
                )
              ],
            ),
          );
        },
      ),
    );
  }
}
