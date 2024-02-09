import 'package:flutter/material.dart';

class PostDetailsPage extends StatelessWidget {
  final String postTitle;

  PostDetailsPage(this.postTitle);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(postTitle),
        backgroundColor: Colors.orangeAccent,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '내용',
              //free_write.dart에서 작성한 글 내용을 DB로 보내고 이 곳으로 다시 보내줘야 합니다.
            ),
          ],
        ),
      ),
    );
  }
}
