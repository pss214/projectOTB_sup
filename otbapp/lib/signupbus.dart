import 'package:flutter/material.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: '회원가입',
      home: const SignUpbus(),
      theme: ThemeData(
        primarySwatch: Colors.lightGreen,
      ),
    );
  }
}

class SignUpbus extends StatefulWidget {
  const SignUpbus({super.key});

  @override
  State<StatefulWidget> createState() => _SignInState();
}

class _SignInState extends State<SignUpbus> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('회원가입'),
        elevation: 0.0,
        backgroundColor: Colors.lightGreen, // 여기에서 앱 바 색상 변경
        centerTitle: true,
        leading: IconButton(
          icon: const Icon(Icons.home), // 홈 아이콘
          onPressed: () {
            Navigator.of(context).popUntil((route) => route.isFirst);
          },
        ),
        actions: const <Widget>[],
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            const Padding(padding: EdgeInsets.only(top: 50)),
            const Center(
              child: Image(
                image: AssetImage('image/otb.png'), // 이미지 경로
                width: 170.0,
              ),
            ),
            Container(
              margin: const EdgeInsets.symmetric(horizontal: 20.0),
              padding: const EdgeInsets.all(20.0),
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(15.0),
                color: Colors.white,
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.5),
                    spreadRadius: 3,
                    blurRadius: 7,
                    offset: const Offset(0, 3),
                  ),
                ],
              ),
              child: Form(
                child: Theme(
                  data: ThemeData(
                    primaryColor: Colors.grey,
                    inputDecorationTheme: const InputDecorationTheme(
                      labelStyle: TextStyle(color: Colors.teal, fontSize: 15.0),
                    ),
                  ),
                  child: Column(
                    children: [
                      const TextField(
                        decoration: InputDecoration(labelText: '차량 번호',
                        hintText: '차량 번호를 입력해주세요.'),
                        keyboardType: TextInputType.text,
                      ),
                      const TextField(
                        decoration: InputDecoration(labelText: '버스 번호',
                        hintText: '버스 번호를 입력해주세요.'),
                        keyboardType: TextInputType.text,
                      ),
                      const TextField(
                        decoration: InputDecoration(labelText: '탑승정원',
                        hintText: '탑승정원을 입력해주세요.'),
                        keyboardType: TextInputType.number,
                      ),
                      const TextField(
                        decoration: InputDecoration(labelText: '비밀번호',
                        hintText: '비밀번호를 입력해주세요.'),
                        keyboardType: TextInputType.text,
                        obscureText: true,
                      ),
                      const TextField(
                        decoration: InputDecoration(labelText: '비밀번호 확인',
                        hintText: '비밀번호를 입력해주세요.'),
                        keyboardType: TextInputType.text,
                        obscureText: true,
                      ),
                      const SizedBox(height: 40.0),
                      SizedBox(
                        width: double.infinity,
                        child: TextButton(
                          onPressed: () {
                            // 회원가입 버튼 눌렀을 때 수행할 작업 추가
                          },
                          style: ButtonStyle(
                            backgroundColor: MaterialStateProperty.all<Color>(Colors.orangeAccent),
                          ),
                          child: const Text(
                            '회원가입',
                            style: TextStyle(color: Colors.white, fontSize: 18.0),
                          ),
                        ),
                      ),
                      const SizedBox(height: 10.0), // 로그인과 회원가입 사이의 공백
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
