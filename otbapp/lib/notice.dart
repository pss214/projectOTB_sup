import 'package:flutter/material.dart';

class NoticePage extends StatefulWidget {
  @override
  _NoticePageState createState() => _NoticePageState();
}

class _NoticePageState extends State<NoticePage> {
  List<String> notices = [
    '첫 번째 공지사항',
    '두 번째 공지사항',
    '세 번째 공지사항',
    //백에서 공지사항 글 가져올 공간
  ];

  TextEditingController _newNoticeController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('공지사항'),
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: notices.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text(notices[index]),
                );
              },
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: _newNoticeController,
                    decoration: InputDecoration(labelText: '새로운 공지사항 추가하기'),
                  ),
                ),//백과 연결되면 삭제할 코드
                IconButton(
                  icon: Icon(Icons.add),
                  onPressed: () {
                    addNotice();
                  },
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  void addNotice() {
    setState(() {
      String newNotice = _newNoticeController.text;
      if (newNotice.isNotEmpty) {
        notices.add(newNotice);
        _newNoticeController.clear();
      }
    });
  }
}
