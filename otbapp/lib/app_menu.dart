import 'package:flutter/material.dart';
import 'my_home_page.dart';
import 'find_road.dart';
import 'reserveBus.dart';
import 'notice.dart';
import 'free_board.dart';
import 'login.dart';
import 'signup.dart' as SignUpPage;
//TestBus
import 'Bus_Arrival_Page.dart';
//TestPay
import 'pay.dart';
import 'mypage.dart';

class AppMenu extends StatefulWidget {
  const AppMenu({super.key});

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
              title: GestureDetector(
                onTap: () {
                  setState(() {
                    currentPage = 'home';
                  });
                },
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,//가로 정렬을 중앙으로 설정
                   crossAxisAlignment: CrossAxisAlignment.center,//세로 정렬을 중앙으로 설정
                  children: [
                    Image.asset(
                      'assets/otb.png',
                      width: 72,//이미지의 가로 크기
                      height: 72,//이미지의 세로 크기
                    ),
                    /*SizedBox(width: 8),
                    Text('OTB'),*/
                  ],
                ),
              ),
              centerTitle: true,
              elevation: 0.0,
              actions: <Widget>[
                IconButton(
                  //icon: const Icon(Icons.menu), //실행해보고 택 1
                  icon: Icon(Icons.login),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => const LogIn()),
                    );
                  },
                ),
                IconButton(
                  //icon: const Icon(Icons.more_vert), //실행해보고 택 1
                  icon: Icon(Icons.assignment_ind),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => const SignUpPage.SignUp()),
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
                    padding: const EdgeInsets.only(top: 40, bottom: 20, left: 16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text(
                          '서비스 메뉴',
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 24,
                            /*fontSize: 20,
                            fontWeight: FontWeight.bold,
                            color: Colors.black,*/
                          ),
                        ),
                      SizedBox(height: 20),
                      GestureDetector(
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(builder: (context) =>  MyProfilePage()),
                          );
                        },
                        child:UserAccountsDrawerHeader(
                            currentAccountPicture: CircleAvatar(
                              backgroundColor: Colors.white,
                            ),
                            accountName: Text("USERNAME"), //로그인 유저 이름
                            accountEmail: Text("USER@Email.com"), //로그인 유저 이메일
                            decoration: BoxDecoration(
                              color: Colors.lightGreen[400],
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),

                  //카테고리 선택 메뉴들
                  ListTile(
                    leading: Icon(Icons.navigation),
                    title: Text('길찾기'),
                    onTap: () {
                      _navigateToPage('navigation', context);
                    },
                  ),
                  ListTile(
                    leading: Icon(Icons.bus_alert),
                    title: Text('버스 이용하기'),
                    onTap: () {
                      _navigateToPage('bus', context);
                      print('버스 이용하기 tapped');
                    },
                  ),
                  ExpansionTile(
                    title: const Text('게시판'),
                    children: [
                      ListTile(
                        leading: Icon(Icons.notifications_none),
                        title: Text('공지사항'),
                        onTap: () {
                          _navigateToPage('notice', context);
                          print('공지사항 tapped');
                        },
                      ),
                      ListTile(
                        leading: Icon(Icons.edit),
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
                    title: const Text('(테스트) 버스 이용하기'),
                    onTap: () {
                      _navigateToPage('TestBus', context);
                      print('(테스트) 버스 이용하기 tapped');
                    },
                  ),
                  ListTile(
                    title: Text('(테스트) 결제창'),
                    onTap: () {
                      _navigateToPage('TestPay', context);
                      print('(테스트) 결제창 tapped');
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
        return  NavigationPage();
      case 'bus':
        return  BusReservePage();
      case 'notice':
        return  NoticePage();
      case 'freeBoard':
        return  FreeBoardPage();
        //TestBus
      case 'TestBus':
        return ReserveApp();
        //TestPay
      case 'TestPay':
        return PayMenu();
      default:
        return  MyHomePage();
    }
  }

  void _navigateToPage(String page, BuildContext context) {
    setState(() {
      currentPage = page;
    });
    Navigator.of(context).pop();
  }
}
