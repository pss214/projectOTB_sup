import 'package:flutter/material.dart';
import 'my_home_page.dart';
import 'find_road.dart';
import 'reserveBus.dart';
import 'notice.dart';
import 'free_board.dart';
import 'login.dart';
import 'signin.dart';
//TestBus
import 'Bus_Arrival_Page.dart';

class AppMenu extends StatefulWidget {
  @override
  _AppMenuState createState() => _AppMenuState();
}

class _AppMenuState extends State<AppMenu> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  String currentPage = 'home';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Menu Example2',
      theme: ThemeData(
        primarySwatch: Colors.lightGreen,
      ),
      home: Builder(
        builder: (BuildContext context) {
          return Scaffold(
            key: _scaffoldKey,
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
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => LogIn()),
                    );
                  },
                ),
                IconButton(
                  icon: Icon(Icons.more_vert),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => SignIn()),
                    );
                  },
                )
              ],
            ),
            
            /*
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
            ),*/
            //위 코드는 로그인 하지 않은 유저에게 보여지는 코드
            
            drawer: Drawer(
              child: ListView(
                padding: EdgeInsets.zero,
                children: [
                  Container(
                    color: Colors.lightGreen[400],
                    padding: EdgeInsets.only(top: 40, bottom: 20, left: 16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          '서비스 메뉴',
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 24,
                            /*fontSize: 20,
                            fontWeight: FontWeight.bold,
                            color: Colors.black,*/
                          ),
                        ),
                        UserAccountsDrawerHeader(
                          currentAccountPicture: CircleAvatar(
                            backgroundImage: AssetImage('images/images1.png'),
                            backgroundColor: Colors.white,
                          ),
                          accountName: Text("USERNAME"), //로그인 유저 이름
                          accountEmail: Text("USER@Email.com"), //로그인 유저 이메일
                          decoration: BoxDecoration(
                            color: Colors.lightGreen[400],
                          ),
                        ),
                      ],
                    ),
                  ),

                  //카테고리 선택 메뉴들
                  ListTile(
                    title: Text('길찾기'),
                    onTap: () {
                      _navigateToPage('navigation', context);
                    },
                  ),
                  ListTile(
                    title: Text('버스 이용하기'),
                    onTap: () {
                      _navigateToPage('bus', context);
                      print('버스 이용하기 tapped');
                    },
                  ),
                  ExpansionTile(
                    title: Text('게시판'),
                    children: [
                      ListTile(
                        title: Text('공지사항'),
                        onTap: () {
                          _navigateToPage('notice', context);
                          print('공지사항 tapped');
                        },
                      ),
                      ListTile(
                        title: Text('자유게시판'),
                        onTap: () {
                          _navigateToPage('freeBoard', context);
                          print('자유게시판 tapped');
                        },
                      ),
                    ],
                  ),
                  //TestBus
                  ListTile(
                    title: Text('(테스트) 버스 이용하기'),
                    onTap: () {
                      _navigateToPage('TestBus', context);
                      print('(테스트) 버스 이용하기 tapped');
                    },
                  ),
                ],
              ),
            ),
            body: _getPage(),
          );
        },
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
        //TestBus
      case 'TestBus':
        return ReserveApp();
      default:
        return MyHomePage();
    }
  }

  void _navigateToPage(String page, BuildContext context) {
    setState(() {
      currentPage = page;
    });
    Navigator.of(context).pop(); // Close the drawer
  }
}
