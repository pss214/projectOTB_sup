import 'package:flutter/material.dart';
import 'free_board.dart';

class FreeWritePage extends StatelessWidget {
  final Function(String) addFreeWrite;

  FreeWritePage(this.addFreeWrite);

  final TextEditingController titleController = TextEditingController();
  final TextEditingController contentController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('게시글 작성하기'),
        backgroundColor: Colors.orangeAccent,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              controller: titleController,
              decoration: InputDecoration(
                labelText: '제목',
              ),
            ),
            SizedBox(height: 16.0),
            TextField(
              controller: contentController,
              maxLines: 5,
              decoration: InputDecoration(
                labelText: '내용',
              ),
            ),
            SizedBox(height: 16.0),
            ElevatedButton(
              onPressed: () {
                String title = titleController.text;
                String content = contentController.text;

                //콜백 호출로 FreeBoardPage 목록에 제목 추가
                addFreeWrite(title);

                Navigator.pop(context);
              },
              child: Text('게시글 작성'),
            ),
          ],
        ),
      ),
    );
  }
}
