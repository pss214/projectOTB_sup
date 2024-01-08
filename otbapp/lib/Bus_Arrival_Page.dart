import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'Bus_Arrival_Info.dart';
import 'Bus_Select_Page.dart';

void main()=>runApp(ReserveApp());

class ReserveApp extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MyReserveApp(),
    );
  }
}
class MyReserveApp extends StatefulWidget{
  @override
  _ReserveWidgetState createState()=> _ReserveWidgetState();
}

class _ReserveWidgetState extends State<MyReserveApp>{

  var _text;
  List<Info> _datas = [];


  dynamic fetchPost()async{
    Map<String,String>headers={
      "Content-Type": "application/json",
      "Authorization": "otb eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWsxMDE3MjpST0xFX1VTRVIiLCJpc3MiOiJjb2xhYmVhcjc1NCIsImlhdCI6MTcwNDM0NTcxMiwiZXhwIjoxNzA0MzU2NTEyfQ.ljXX8mrx9X6YH1KnpLylAYl69Hg-UOFGGgB-N3RZp8xmu1qow0nZBkpO3Gzi5s-YDnhwYvtoAeo-byxBsLAKXw",
    };
    var data = {"stationid": "22288"};
    var url = Uri.parse('http://bak10172.asuscomm.com:10001/bus/information');
    var response = await http.post(
        url,
        body: json.encode(data),headers: headers);
    var dataObjsJson = jsonDecode(utf8.decode(response.bodyBytes))['data'][0] as List;
    final List<Info> parsedResponse = dataObjsJson.map((a)=>Info.fromJson(a)).toList();
    _datas.clear();
    _datas.addAll(parsedResponse);
    print(parsedResponse[4]);
    setState(() {
      _datas.clear();
      _datas.addAll(parsedResponse);
    });
  }
  @override
  void initState(){
    super.initState();
    fetchPost();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            Padding(padding: EdgeInsets.only(top: 20)),
            Text(
              "도착 버스 목록",
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(
              height: 1000,
              child: ListView.builder(
                itemCount: _datas.length,
                itemBuilder: (context, index) {
                  return Card(
                    child: ListTile(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => SecondPage(
                              routeName: _datas[index].rtNm,
                              busId: _datas[index].vehId1,
                              arrivalMsg1: _datas[index].arrmsg1,
                              arrivalMsg2: _datas[index].arrmsg2,
                            ),
                          ),
                        );
                      },
                      title: Text("버스 번호: ${_datas[index].rtNm}"),
                      subtitle: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text("BusID: ${_datas[index].vehId1}"),
                          Text("도착정보1: ${_datas[index].arrmsg1}"),
                          Text("도착정보2: ${_datas[index].arrmsg2}"),
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
                child: Text("API 호출"),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
