import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'free_write.dart';
import 'free_post_detail.dart';

class FreeBoardPage extends StatefulWidget {
  @override
  _FreeBoardPageState createState() => _FreeBoardPageState();
}

class _FreeBoardPageState extends State<FreeBoardPage> {
  List<Map<String, dynamic>> notices = [];

  TextEditingController _newFreeController = TextEditingController();

  @override
  void initState() {
    super.initState();
    fetchNotices();
  }

  Future<void> fetchNotices() async {
    try {
      final response = await http.get(Uri.parse('http://bak10172.asuscomm.com:10001/report-board/list/report'));

      if (response.statusCode == 200) {
        final dynamic decodedResponse = json.decode(utf8.decode(response.bodyBytes));

        if (decodedResponse['status'] == 0) {
          final dynamic data = decodedResponse['data'];

          if (data is List && data.isNotEmpty) {
            setState(() {
              notices = data[0]
                  .map<Map<String, dynamic>>(
                    (item) => {
                  'title': item['title'] as String? ?? 'No Title',
                  'date': DateTime.tryParse(item['date'] as String) ?? DateTime.now(),
                },
              )
                  .toList();
            });
          } else {
            throw Exception('Unexpected data format');
          }
        } else {
          throw Exception('API returned an error: ${decodedResponse['message']}');
        }
      } else {
        throw Exception('Failed to load notices. Status code: ${response.statusCode}');
      }
    } catch (error) {
      print('패치 에러 알림: $error');
    }
  }

  void addFreeWrite(String title) {
    setState(() {
      notices.add({
        'title': title,
        'date': DateTime.now(),
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('자유 게시판'),
        backgroundColor: Colors.orangeAccent,
        actions: [
          IconButton(
            icon: Icon(Icons.edit),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => FreeWritePage(addFreeWrite),
                ),
              );
            },
          ),
        ],
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: notices.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text(notices[index]['title']),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => PostDetailsPage(notices[index]['title']),
                      ),
                    );
                  },
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
