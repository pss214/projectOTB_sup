import 'package:flutter/material.dart';
import 'free_write.dart';
import 'free_post_detail.dart';

class FreeBoardPage extends StatefulWidget {
  @override
  _FreeBoardPageState createState() => _FreeBoardPageState();
}

class _FreeBoardPageState extends State<FreeBoardPage> {
  List<String> notices = [
    '글 1',
    '글 2',
    '글 3',
    //백에서 글 가져올 공간
  ];

  TextEditingController _newFreeController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('자유 게시판'),
        backgroundColor: Colors.orangeAccent,
        actions: [
          IconButton(
            icon: Icon(Icons.add),
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
                  title: Text(notices[index]),
                  onTap: () {
                    //게시물 클릭되면 세부 정보로 이동
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => PostDetailsPage(notices[index]),
                      ),
                    );
                  },
                );
              },
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [
              ],
            ),
          ),
        ],
      ),
    );
  }

  void addFreeWrite(String title) {
    setState(() {
      notices.add(title);
    });
  }
}
