import 'package:flutter/material.dart';
import 'signupbus.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: '회원가입',
      home: SignUp(),
      theme: ThemeData(
        primarySwatch: Colors.lightGreen,
      ),
    );
  }
}

class SignUp extends StatefulWidget {
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
    var response = await http.post(url, body: json.encode(data), headers: headers);
    if (response.statusCode == 201) {
      // 회원가입 성공 시 수행할 작업
      print("성공");
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
          title: Text("비밀번호가 일치하지 않습니다."),
          actions: <Widget>[
            TextButton(
              child: Text("확인"),
              onPressed: () {
                Navigator.of(context).pop(); // 경고창 닫기
              },
            ),
          ],
        );
      },
    );
  }

  void showSuccessDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text("회원가입이 완료되었습니다."),
          actions: <Widget>[
            TextButton(
              child: Text("확인"),
              onPressed: () {
                Navigator.of(context).popUntil((route) => route.isFirst); // 처음 페이지로 이동
              },
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('회원가입'),
        elevation: 0.0,
        backgroundColor: Colors.lightGreen,
        centerTitle: true,
        leading: IconButton(
          icon: Icon(Icons.home),
          onPressed: () {
            Navigator.of(context).popUntil((route) => route.isFirst);
          },
        ),
        actions: <Widget>[],
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Padding(padding: EdgeInsets.only(top: 50)),
            Center(
              child: Image(
                image: AssetImage('image/otb.png'),
                width: 170.0,
              ),
            ),
            Container(
              margin: EdgeInsets.symmetric(horizontal: 20.0),
              padding: EdgeInsets.all(20.0),
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(15.0),
                color: Colors.white,
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.5),
                    spreadRadius: 3,
                    blurRadius: 7,
                    offset: Offset(0, 3),
                  ),
                ],
              ),
              child: Form(
                child: Theme(
                  data: ThemeData(
                    primaryColor: Colors.grey,
                    inputDecorationTheme: InputDecorationTheme(
                      labelStyle: TextStyle(color: Colors.teal, fontSize: 15.0),
                    ),
                  ),
                  child: Column(
                    children: [
                      TextField(
                        controller: idController,
                        decoration: InputDecoration(
                          labelText: '아이디',
                          hintText: '아이디를 입력해주세요.',
                        ),
                        keyboardType: TextInputType.text,
                      ),
                      TextField(
                        controller: emailController,
                        decoration: InputDecoration(
                          labelText: 'E-Mail',
                          hintText: '이메일을 입력해주세요.',
                        ),
                        keyboardType: TextInputType.emailAddress,
                      ),
                      TextField(
                        controller: passwordController,
                        decoration: InputDecoration(
                          labelText: '비밀번호',
                          hintText: '비밀번호를 입력해주세요.',
                        ),
                        keyboardType: TextInputType.text,
                        obscureText: true,
                      ),
                      TextField(
                        decoration: InputDecoration(
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
                      SizedBox(height: 40.0),
                      SizedBox(
                        width: double.infinity,
                        child: TextButton(
                          onPressed: signUp,
                          child: Text(
                            '회원가입',
                            style: TextStyle(color: Colors.white, fontSize: 18.0),
                          ),
                          style: ButtonStyle(
                            backgroundColor:
                            MaterialStateProperty.all<Color>(Colors.orangeAccent),
                          ),
                        ),
                      ),
                      SizedBox(height: 10.0),
                      SizedBox(
                        width: double.infinity,
                        child: TextButton(
                          onPressed: () {
                            Navigator.push(
                              context,
                              MaterialPageRoute(builder: (context) => SignUpbus()),
                            );
                          },
                          child: Text(
                            '버스기사 전용 회원가입',
                            style: TextStyle(color: Colors.white, fontSize: 18.0),
                          ),
                          style: ButtonStyle(
                            backgroundColor:
                            MaterialStateProperty.all<Color>(Colors.orangeAccent),
                          ),
                        ),
                      ),
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
