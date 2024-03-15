import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:html/parser.dart' as htmlParser;
import 'package:html/dom.dart' as htmlDom;
import 'dart:convert';

class PostDetailsPage extends StatefulWidget {
  final String postTitle;

  PostDetailsPage(this.postTitle);

  @override
  _PostDetailsPageState createState() => _PostDetailsPageState();
}

class _PostDetailsPageState extends State<PostDetailsPage> {
  String content = '';

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  Future<void> fetchData() async {
    final url = 'http://bak10172.asuscomm.com:10001/report-board/1';

    try {
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        print('HTML Response: ${response.body}');

        //UTF-8 decoding
        final document = htmlParser.parse(utf8.decode(response.bodyBytes));

        final nodes = document.body!.nodes.whereType<htmlDom.Element>().toList();

        //이미지
        nodes.removeWhere((node) => node.localName == 'img');

        //나머지 노드
        content = nodes.map((node) => node.text).join(' ');

        setState(() {});
      } else {
        print('Failed to load data. Status code: ${response.statusCode}');
      }
    } catch (error) {
      print('Error fetching data: $error');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.postTitle),
        backgroundColor: Colors.orangeAccent,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '내용',
            ),
            SizedBox(height: 10),
            Text(
              content,
              style: TextStyle(fontSize: 16),
            ),
          ],
        ),
      ),
    );
  }
}
