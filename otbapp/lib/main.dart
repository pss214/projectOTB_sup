import 'package:flutter/material.dart';
import 'splash_screen.dart';  // SplashScreen 클래스가 정의된 파일을 임포트합니다.

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: SplashScreen(),
    );
  }
}