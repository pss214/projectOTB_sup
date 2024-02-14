import 'dart:convert';  // json.decode를 사용하기 위해 추가
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: '마이 페이지',
      home: const MyPage(),
      theme: ThemeData(
        primarySwatch: Colors.lightGreen,
      ),
    );
  }
}

class MyPage extends StatefulWidget {
  const MyPage({Key? key}) : super(key: key);

  @override
  State<MyPage> createState() => _MyPageState();
}

class _MyPageState extends State<MyPage> {
  bool isEditing = false;
  String memberName = '임시'; // 초기값

  @override
  void initState() {
    super.initState();
    // 페이지가 생성될 때 초기 회원 정보를 불러오는 메서드 호출
    loadMemberInfo();
  }

  // 회원 정보를 불러오는 메서드
  Future<void> loadMemberInfo() async {
    try {
      var response = await http.get(
        Uri.parse('http://bak10172.asuscomm.com:10001/member'),
        headers: {
          'Authorization': 'otb eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzpST0xFX1VTRVIiLCJpc3MiOiJzc3A2OTU5NyIsImlhdCI6MTcwNzg4ODIyMCwiZXhwIjoxNzA3ODk5MDIwfQ.Sr4vSVjOxsBFgDdHIygIRqnLPzezCiCxGMfPFuT24D05SKG6MNZn_DVpgDnUCYVWj2xpxbNGdk-y2-JV5qN5Tw',
        },
      );

      if (response.statusCode == 200) {
        // 성공적으로 응답을 받아왔을 때
        final Map<String, dynamic> responseData = json.decode(response.body);
        setState(() {
          memberName = responseData['member']; // 서버에서 받아온 회원 이름으로 업데이트
        });
      } else {
        // 응답이 실패했을 때
        print('Failed to load member info: ${response.statusCode}');
      }
    } catch (e) {
      // 예외 처리
      print('Error loading member info: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('마이 페이지'),
        elevation: 0.0,
        backgroundColor: Colors.lightGreen,
        centerTitle: true,
        leading: IconButton(
          icon: const Icon(Icons.home),
          onPressed: () {
            Navigator.of(context).popUntil((route) => route.isFirst);
          },
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '회원 정보',
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20.0,
              ),
            ),
            const SizedBox(height: 20.0),
            Container(
              decoration: BoxDecoration(
                border: Border.all(color: Colors.grey),
                borderRadius: BorderRadius.circular(8.0),
              ),
              padding: const EdgeInsets.all(16.0),
              child: Column(  // const 키워드 제거
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        '회원 이름:',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 18.0,
                        ),
                      ),
                      Expanded(
                        child: Text(
                          memberName,
                          textAlign: TextAlign.end,
                          style: TextStyle(fontSize: 16.0),
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 8.0),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        '이메일:',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 18.0,
                        ),
                      ),
                      Expanded(
                        child: Text(
                          '임시',
                          textAlign: TextAlign.end,
                          style: TextStyle(fontSize: 16.0),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20.0),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton(
                  onPressed: () {
                    setState(() {
                      isEditing = !isEditing;
                    });
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orangeAccent,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(18.0),
                    ),
                  ),
                  child: const Text(
                    '수정하기',
                    style: TextStyle(fontSize: 16.0, color: Colors.white),
                  ),
                ),
                ElevatedButton(
                  onPressed: () {
                    // 회원 탈퇴 버튼 눌렀을 때 실행되는 기능 추가
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orangeAccent,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(18.0),
                    ),
                  ),
                  child: const Text(
                    '회원 탈퇴',
                    style: TextStyle(fontSize: 16.0, color: Colors.white),
                  ),
                ),
                ElevatedButton(
                  onPressed: () {
                    // QR 보기 버튼 눌렀을 때 실행되는 기능 추가
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orangeAccent,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(18.0),
                    ),
                  ),
                  child: const Text(
                    'QR 보기',
                    style: TextStyle(fontSize: 16.0, color: Colors.white),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 20.0),
            if (isEditing)
              Column(
                children: [
                  const TextField(
                    decoration: InputDecoration(
                      hintText: '새로운 아이디',
                    ),
                  ),
                  const TextField(
                    decoration: InputDecoration(
                      hintText: '새로운 비밀번호',
                    ),
                  ),
                  const TextField(
                    decoration: InputDecoration(
                      hintText: '비밀번호 확인',
                    ),
                  ),
                  const SizedBox(height: 20.0),
                  ElevatedButton(
                    onPressed: () async {
                      // 저장 버튼 눌렀을 때 실행되는 기능 추가
                      var response = await http.post(
                        Uri.parse('http://bak10172.asuscomm.com/member'),
                        headers: {
                          'Authorization': 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzpST0xFX1VTRVIiLCJpc3MiOiJzc3A2OTU5NyIsImlhdCI6MTcwNzgwNjczNiwiZXhwIjoxNzA3ODE3NTM2fQ.GLatMK44O4wza5Wp4QsAVDGQ9B_evsVt2o5SEVRzLrYazgK1AaNVlFMdy5ySvHnBtDawnxdaUjbzflhVwRvqCA',
                        },
                      );
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.orangeAccent,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(18.0),
                      ),
                    ),
                    child: const Text(
                      '저장 하기',
                      style: TextStyle(fontSize: 16.0, color: Colors.white),
                    ),
                  ),
                ],
              ),
          ],
        ),
      ),
    );
  }
}
