import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'Bus_Arrival_Info.dart';
import 'Bus_Select_Arrival_Page.dart';

void main() => runApp(const ReserveApp());

class ReserveApp extends StatelessWidget {
  const ReserveApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: MyReserveApp(selectedStopNo: '', selectedStopName: ''),
    );
  }
}
class MyReserveApp extends StatefulWidget {
  final String selectedStopNo;
  final String selectedStopName;

  const MyReserveApp({super.key, required this.selectedStopNo,required this.selectedStopName});

  @override
  _ReserveWidgetState createState() => _ReserveWidgetState(

  );
}

class _ReserveWidgetState extends State<MyReserveApp> {
  final List<Info> _datas = [];

  dynamic fetchPost() async {
    Map<String, String> headers = {
      "Content-Type": "application/json",
      //"Authorization": "otb eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWsxMDE3MjpST0xFX1VTRVIiLCJpc3MiOiJjb2xhYmVhcjc1NCIsImlhdCI6MTcwNDQyNDE3MSwiZXhwIjoxNzA0NDM0OTcxfQ.UuXXamzlmHLP07whzkpGra3du0tZh24uTmRwesMXQefimbiVasqohFJmzE_vDzMbazt5l_ce6dd6T_BeNqHt7g",
    };
    var data = {"stationid": widget.selectedStopNo};
    var url = Uri.parse('http://bak10172.asuscomm.com:10001/bus/information');
    var response = await http.post(
        url, body: json.encode(data), headers: headers);
    if (response.statusCode == 200) {
      var dataObjsJson = jsonDecode(
          utf8.decode(response.bodyBytes))['data'][0] as List;
      final List<Info> parsedResponse =
      dataObjsJson.map((a) => Info.fromJson(a)).toList();
      //print(dataObjsJson);
      //print(parsedResponse[2]);
      setState(() {
        _datas.clear();
        _datas.addAll(parsedResponse);
      });
    }
  }

  @override
  void initState() {
    super.initState();
    fetchPost();
  }
  List<List<Info>> chunkPages(List<Info> list, int chunkSize) {
    List<List<Info>> chunks = [];
    for (var i = 0; i < list.length; i += 5) {//개수 조절 여기서 하면 됩니다
      chunks.add(list.sublist(
          i,
          i + 5 > list.length
              ? list.length
              : i + 5));
    }
    return chunks;
  }

  @override
  Widget build(BuildContext context) {
    List<List<Info>> pages = chunkPages(_datas, 5);

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.selectedStopName,style: const TextStyle(fontSize: 20)),
      ),
      body: PageView.builder(
        itemCount: pages.length,
        itemBuilder: (context, pageIndex) {
          return SingleChildScrollView(
            child: Column(
              children: [
                Padding(padding: EdgeInsets.only(top: 20)),
                Text(
                  "도착 버스 목록 - 페이지 ${pageIndex + 1}",
                  style: const TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const Text(
                  "좌우로 넘겨 버스를 검색하세요.",
                  style: TextStyle(
                    fontSize: 10,
                    fontWeight: FontWeight.bold,
                    color: Colors.black,
                  ),
                ),
                SizedBox(
                  height: 1000,
                  child: ListView.builder(
                    itemCount: pages[pageIndex].length,
                    itemBuilder: (context, index) {
                      return Card(
                        child: ListTile(
                          onTap: () {
                            Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (context) => SelectPage(
                                  busNm: pages[pageIndex][index].rtNm,
                                  busId: pages[pageIndex][index].busRouteId,
                                  busVehId: pages[pageIndex][index].vehId1,
                                  arrivalMsg1: pages[pageIndex][index].arrmsg1,
                                  arrivalMsg2: pages[pageIndex][index].arrmsg2,
                                  selectedStopNo: widget.selectedStopNo,
                                  selectedStopNm: widget.selectedStopName,
                                ),
                              ),
                            );
                          },
                          title: Text("버스 번호: ${pages[pageIndex][index].rtNm}"),
                          subtitle: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text("고유번호: ${pages[pageIndex][index].busRouteId}"),
                              Text("차량번호: ${pages[pageIndex][index].vehId1}"),
                              Text("도착정보1: ${pages[pageIndex][index].arrmsg1}"),
                              Text("도착정보2: ${pages[pageIndex][index].arrmsg2}"),
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
                    onPressed: () {
                      fetchPost();
                    },
                    child: const Text("통신상태확인용"),
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }
}
