import 'package:flutter/material.dart';
import 'NavigationPage.dart';

class AppMenu extends StatefulWidget {
  @override
  _AppMenuState createState() => _AppMenuState();
}

class _AppMenuState extends State<AppMenu> {
  String currentPage = 'home';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Menu Example2',
      theme: ThemeData(
        primarySwatch: Colors.lightGreen,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text('OTB'),
          centerTitle: true,
          elevation: 0.0,
          actions: <Widget>[
            IconButton(
              icon: Icon(Icons.menu),
              onPressed: () {},
            ),
            IconButton(
              icon: Icon(Icons.more_vert),
              onPressed: () {},
            )
          ],
        ),
        drawer: Drawer(
          child: ListView(
            padding: EdgeInsets.zero,
            children: <Widget>[
              Container(
                height: 120,
                child: DrawerHeader(
                  decoration: BoxDecoration(
                    color: Colors.lightGreen,
                  ),
                  child: Text(
                    '서비스 메뉴',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 24,
                    ),
                  ),
                ),
              ),
              ListTile(
                title: Text('길찾기'),
                onTap: () {
                  setState(() {
                    currentPage = 'navigation';
                  });
                },
              ),
              ListTile(
                title: Text('버스 이용하기'),
                onTap: () {
                  print('버스 이용하기 tapped');
                },
              ),
              ExpansionTile(
                title: Text('게시판'),
                children: [
                  ListTile(
                    title: Text('공지사항'),
                    onTap: () {
                      print('공지사항 tapped');
                    },
                  ),
                  ListTile(
                    title: Text('자유게시판'),
                    onTap: () {
                      print('자유게시판 tapped');
                    },
                  ),
                ],
              ),
            ],
          ),
        ),
        body: currentPage == 'navigation' ? NavigationPage() : MyHomePage(),
      ),
    );
  }
}
