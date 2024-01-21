import 'package:flutter/material.dart';

class FreeBoardPage extends StatelessWidget {
  const FreeBoardPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('자유게시판'),
      ),
      body: const Center(
        child: Text('자유게시판 입니다'),
      ),
    );
  }
}
