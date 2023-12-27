import 'package:flutter/material.dart';
import 'my_home_page.dart';
import 'find_road.dart';
import 'reserveBus.dart';
import 'notice.dart';
import 'free_board.dart';

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
          title: InkWell(
            onTap: () {
              setState(() {
                currentPage = 'home';
              });
            },
            child: Text('OTB'),
          ),
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
                    setState(() {
                      currentPage = 'bus';
                    });
                  print('버스 이용하기 tapped');
                },
              ),
              ExpansionTile(
                title: Text('게시판'),
                children: [
                  ListTile(
                    title: Text('공지사항'),
                    onTap: () {
                      setState(() {
                        currentPage = 'notice';
                      });
                      print('공지사항 tapped');
                    },
                  ),
                  ListTile(
                    title: Text('자유게시판'),
                    onTap: () {
                      setState(() {
                        currentPage = 'freeBoard';
                      });
                      print('자유게시판 tapped');
                    },
                  ),
                ],
              ),
            ],
          ),
        ),
        body: _getPage(),
      ),
    );
  }
  Widget _getPage() {
    switch (currentPage) {
      case 'navigation':
        return NavigationPage();
      case 'bus':
        return BusReservePage();
      case 'notice':
        return NoticePage();
      case 'freeBoard':
        return FreeBoardPage();
      default:
        return MyHomePage();
    }
  }
}
