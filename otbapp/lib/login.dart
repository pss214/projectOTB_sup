import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:fluttertest/app_menu.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: '로그인',
      home: FutureBuilder(
        future: checkTokenExistence(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            // 토큰이 있으면 메인 화면으로 이동
            return snapshot.data == true ? const AppMenu() : const LogIn();
          } else {
            // 아직 토큰 검사가 완료되지 않았으면 로딩 화면 등을 표시
            return const CircularProgressIndicator();
          }
        },
      ),
      theme: ThemeData(
        primarySwatch: Colors.lightGreen,
      ),
    );
  }
}

class LogIn extends StatefulWidget {
  const LogIn({super.key});

  @override
  State<LogIn> createState() => _LogInState();
}

class Userjwt {
  final String token;

  Userjwt(this.token);

  factory Userjwt.fromJson(dynamic json) {
    try {
      final data = (json['data'] as List).cast<Map>();
      final token = data.first['token'];

      if (token is String) {
        return Userjwt(token);
      } else {
        throw Exception('');
      }
    } catch (_) {
      return Userjwt('');
    }
  }

  @override
  String toString() {
    return 'Userjwt{token: $token}';
  }
}

class _LogInState extends State<LogIn> {
  final TextEditingController idController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  void signIn() async {
    String id = idController.text;
    String password = passwordController.text;

    var data = {
      "username": id,
      "password": password,
    };

    try {
      var response = await http.post(
        Uri.parse('http://bak10172.asuscomm.com:10001/user/signin'),
        body: json.encode(data),
        headers: {
          "Content-Type": "application/json",
        },
      );
      print("응답 코드: ${response.statusCode}");
      print("응답 본문: ${response.body}");

      if (response.statusCode == 200) {
        print("로그인 성공");

        var jsonResponse = json.decode(response.body);
        Userjwt userjwt = Userjwt.fromJson(jsonResponse);
        String jwtToken = userjwt.token;

        //로그인 성공 후 토큰 값 출력하는 코드
        print('토큰 값: $jwtToken');

        SharedPreferences prefs = await SharedPreferences.getInstance();
        await prefs.setString('jwtToken', jwtToken);

        // 로그인 성공 후 로컬 스토리지에 저장된 토큰 값 확인
        await checkTokenExistence();

        if (jwtToken.isNotEmpty) {
          SharedPreferences prefs = await SharedPreferences.getInstance();
          await prefs.setString('jwtToken', jwtToken);
        } else {
          print('토큰 값이 유효하지 않습니다.');
        }

        if (mounted) {
          Navigator.of(context).pop();
        }

        // Navigator.of(context).pushReplacement(
        //   MaterialPageRoute(
        //       builder: (context) => const AppMenu()), // MainPageBody로 이동
        // );
      } else {
        print("로그인 실패");
        showAlertDialog(context, "로그인 실패", "아이디와 비밀번호를 확인해주세요.");
      }
    } catch (e) {
      print("로그인 에러: $e");
    }
  }

  void showAlertDialog(BuildContext context, String title, String message) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(title),
          content: Text(message),
          actions: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: const Text('확인'),
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
        title: const Text('로그인'),
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
      body: Column(
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
                    const SizedBox(height: 40.0),
                    SizedBox(
                      width: double.infinity,
                      child: TextButton(
                        onPressed: signIn,
                        style: ButtonStyle(
                          backgroundColor: MaterialStateProperty.all<Color>(
                              Colors.orangeAccent),
                        ),
                        child: const Text(
                          '로그인',
                          style: TextStyle(color: Colors.white, fontSize: 18.0),
                        ),
                      ),
                    ),
                    const SizedBox(height: 10.0),
                    SizedBox(
                      width: double.infinity,
                        child: const Text(
                          '회원가입',
                          style: TextStyle(color: Colors.white, fontSize: 18.0),
                        ),
                      ),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

// 추가 코드
class SignUp extends StatelessWidget {
  const SignUp({super.key});

  @override
  Widget build(BuildContext context) {
    // 회원가입 화면 구현
    return Scaffold(
      appBar: AppBar(
        title: const Text('회원가입'),
        // 다른 액션 등을 추가할 수 있습니다.
      ),
      body: const Center(
        child: Text('회원가입 화면'),
      ),
    );
  }
}

Future<bool> checkTokenExistence() async {
  SharedPreferences prefs = await SharedPreferences.getInstance();
  String? storedToken = prefs.getString('jwtToken');

  if (storedToken != null && storedToken.isNotEmpty) {
    print('로컬 스토리지에 저장된 토큰 값: $storedToken');
    // 여기에 추가적인 작업을 수행할 수도 있습니다.
  } else {
    print('로컬 스토리지에 저장된 토큰이 없거나 비어 있습니다.');
    // 여기에 추가적인 작업을 수행할 수도 있습니다.
  }

  return storedToken != null && storedToken.isNotEmpty;
}
