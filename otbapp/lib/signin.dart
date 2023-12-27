import 'package:flutter/material.dart';
import 'signinbus.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: '회원가입',
      home: SignIn(),
      theme: ThemeData(
        primarySwatch: Colors.lightGreen, // 앱의 색상
      ),
    );
  }
}

class SignIn extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _SignInState();
}

class _SignInState extends State<SignIn> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('회원가입'),
        elevation: 0.0,
        backgroundColor: Colors.lightGreen, // 여기에서 앱 바 색상 변경
        centerTitle: true,
        leading: IconButton(
          icon: Icon(Icons.home), // 홈으로 이동하는 아이콘으로 변경
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
              image: AssetImage('image/otb.png'), // 이미지 경로
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
                      decoration: InputDecoration(labelText: '아이디',
                      hintText: "아이디를 입력해주세요."),
                      keyboardType: TextInputType.text,
                    ),
                    TextField(
                      decoration: InputDecoration(labelText: 'E-Mail',
                      hintText: "이메일을 입력해주세요."),
                      keyboardType: TextInputType.emailAddress,
                    ),
                    TextField(
                      decoration: InputDecoration(labelText: '비밀번호',
                      hintText: "비밀번호를 입력해주세요."),
                      keyboardType: TextInputType.text,
                      obscureText: true,
                    ),
                    TextField(
                      decoration: InputDecoration(labelText: '비밀번호 확인',
                      hintText: "비밀번호를 입력해주세요."),
                      keyboardType: TextInputType.text,
                      obscureText: true,
                    ),
                    SizedBox(height: 40.0),
                    SizedBox(
                      width: double.infinity,
                      child: TextButton(
                        onPressed: () {
                          // 회원가입 버튼 눌렀을 때 수행할 작업
                        },
                        child: Text(
                          '회원가입',
                          style: TextStyle(color: Colors.white, fontSize: 18.0),
                        ),
                        style: ButtonStyle(
                          backgroundColor: MaterialStateProperty.all<Color>(Colors.orangeAccent),
                        ),
                      ),
                    ),
                    SizedBox(height: 10.0), // 로그인과 회원가입 사이의 공백
                    SizedBox(
                      width: double.infinity,
                      child: TextButton(
                        onPressed: () {
                          // 버스기사 전용 회원가입 버튼 눌렀을 때 SignIn2 페이지로 이동
                          Navigator.push(
                            context,
                            MaterialPageRoute(builder: (context) => SignInbus()),
                          );
                        },
                        child: Text(
                          '버스기사 전용 회원가입',
                          style: TextStyle(color: Colors.white, fontSize: 18.0),
                        ),
                        style: ButtonStyle(
                          backgroundColor: MaterialStateProperty.all<Color>(Colors.orangeAccent),
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
