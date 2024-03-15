import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: '회원가입',
      home: const SignUp(),
      theme: ThemeData(
        primarySwatch: Colors.lightGreen,
      ),
    );
  }
}

class SignUp extends StatefulWidget {
  const SignUp({super.key});

  @override
  State<StatefulWidget> createState() => _SignUpState();
}

class _SignUpState extends State<SignUp> {
  TextEditingController idController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  bool isPasswordMatching = false;

  Future<void> fetchPost(Map<String, dynamic> data) async {
    Map<String, String> headers = {
      "Content-Type": "application/json",
    };
    print(data);
    var url = Uri.parse('http://bak10172.asuscomm.com:10001/user/signup');
    var response =
        await http.post(url, body: json.encode(data), headers: headers);
    if (response.statusCode == 201) {
      // 회원가입 성공 시 수행할 작업
      print("성공");
      print(response.headers);
      print(utf8.decode(response.bodyBytes));

      // 회원가입 성공 알림창 표시
      showSuccessDialog(context);
    } else {
      // 회원가입 실패 시 수행할 작업
      print("실패");
      print(utf8.decode(response.bodyBytes));
    }
  }

  void signUp() async {
    String id = idController.text;
    String password = passwordController.text;
    String email = emailController.text;

    if (!isPasswordMatching) {
      // 비밀번호가 일치하지 않을 때 경고창 표시
      showAlertDialog(context);
      return;
    }

    var data = {"username": id, "password": password, "email": email};
    await fetchPost(data);
  }

  void showAlertDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("비밀번호가 일치하지 않습니다."),
          actions: <Widget>[
            TextButton(
              child: const Text("확인"),
              onPressed: () {
                Navigator.of(context).pop(); // 경고창 닫기
              },
            ),
          ],
        );
      },
    );
  }

  Future<void> showSuccessDialog(BuildContext context) async {
    await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("회원가입이 완료되었습니다."),
          actions: <Widget>[
            TextButton(
              child: const Text("확인"),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );

    if (mounted) {
      Navigator.of(context).pop();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('회원가입'),
        elevation: 0.0,
        backgroundColor: Colors.lightGreen,
        centerTitle: true,
        leading: IconButton(
          icon: const Icon(Icons.home),
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
                image: AssetImage('assets/otb.png'),
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
                      TextField(
                        controller: idController,
                        decoration: const InputDecoration(
                          labelText: '아이디',
                          hintText: '아이디를 입력해주세요.',
                        ),
                        keyboardType: TextInputType.text,
                      ),
                      TextField(
                        controller: emailController,
                        decoration: const InputDecoration(
                          labelText: 'E-Mail',
                          hintText: '이메일을 입력해주세요.',
                        ),
                        keyboardType: TextInputType.emailAddress,
                      ),
                      TextField(
                        controller: passwordController,
                        decoration: const InputDecoration(
                          labelText: '비밀번호',
                          hintText: '비밀번호를 입력해주세요.',
                        ),
                        keyboardType: TextInputType.text,
                        obscureText: true,
                      ),
                      TextField(
                        decoration: const InputDecoration(
                          labelText: '비밀번호 확인',
                          hintText: '비밀번호를 입력해주세요.',
                        ),
                        keyboardType: TextInputType.text,
                        obscureText: true,
                        onChanged: (value) {
                          // 두 번째 비밀번호 입력 필드 값이 변경될 때마다 호출되는 함수
                          if (value == passwordController.text) {
                            setState(() {
                              isPasswordMatching = true;
                            });
                          } else {
                            setState(() {
                              isPasswordMatching = false;
                            });
                          }
                        },
                      ),
                      const SizedBox(height: 40.0),
                      SizedBox(
                        width: double.infinity,
                        child: TextButton(
                          onPressed: signUp,
                          style: ButtonStyle(
                            backgroundColor: MaterialStateProperty.all<Color>(
                                Colors.orangeAccent),
                          ),
                          child: const Text(
                            '회원가입',
                            style:
                                TextStyle(color: Colors.white, fontSize: 18.0),
                          ),
                        ),
                      ),
                      const SizedBox(height: 10.0),
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
