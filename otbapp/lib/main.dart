import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() {
  runApp(const MyApp());
}
class Data{
  List<Articles> articles;

  Data(this.articles);
  factory Data.fromJson(dynamic json){
    return Data(json['articles'] as List<Articles>);
  }

  @override
  String toString(){
    return '{$articles}';
  }

}
class Articles{
  String title;
  String description;

  Articles(this.title,this.description);
  factory Articles.fromJson(dynamic json){
    return Articles(json['title'] as String, json['description'] as String);
  }
  @override
  String toString(){
    return '{$title,$description';
  }
}
class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget {
  const MyHomePage({Key? key}) : super(key: key);
  void _callAPI() async {
    var text = "Http Example";
    List<Data> data = [];
    var url = Uri.parse(
      'https://back.otb.kr/news/&category=sports',
    );
    var response = await http.get(url);
    text = utf8.decode(response.bodyBytes);
    var dataObjsJson = jsonDecode(text)['data'] as List;
    print('Response body: $dataObjsJson');

    // url = Uri.parse('http://bak10172.asuscomm.com:10001');
    // response = await http.post(url, body: {
    //   'key': 'value',
    // });
    // print('Response status: ${response.statusCode}');
    // print('Response body: ${response.body}');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('http Example'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: _callAPI,
          child: const Text('Call API'),
        ),
      ),
    );
  }
}