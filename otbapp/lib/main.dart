import 'package:flutter/material.dart';
import 'app_menu.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: SplashScreen(),
    );
  }
}

class SplashScreen extends StatefulWidget {
  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation<double> _animation;
  late Animation<double> _secondImageAnimation;

  @override
  void initState() {
    super.initState();

    _controller = AnimationController(
      vsync: this,
      duration: Duration(seconds: 2),
    );

    _animation = Tween<double>(begin: -1.0, end: 0.0).animate(
      CurvedAnimation(
        parent: _controller,
        curve: Curves.fastOutSlowIn,
      ),
    );

    _secondImageAnimation = Tween<double>(begin: 0.0, end: 1.0).animate(
      CurvedAnimation(
        parent: _controller,
        curve: Interval(0.5, 1.0, curve: Curves.fastOutSlowIn),
      ),
    );

    _secondImageAnimation.addStatusListener((status) {
      if (status == AnimationStatus.completed) {
        print("Second image animation completed");
      }
    });

    _controller.forward();

    Future.delayed(Duration(seconds: 3), () {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => AppMenu()),
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.lightGreen,
      body: AnimatedBuilder(
        animation: _controller,
        builder: (context, child) {
          Widget firstImage = Transform(
            transform: Matrix4.translationValues(
              _animation.value * MediaQuery.of(context).size.width,
              0.0,
              0.0,
            ),
            child: Center(
              child: Transform.scale(
                scale: 0.5,
                child: Image.asset('assets/6556198.png'),
              ),
            ),
          );

          Widget secondImage = Opacity(
            opacity: _secondImageAnimation.value,
            child: Center(
              child: Transform.scale(
                scale: 0.5,
                child: Image.asset('assets/otb.png'),
              ),
            ),
          );

          return Stack(
            children: [firstImage, secondImage],
          );
        },
      ),
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }
}
