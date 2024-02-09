import 'dart:convert';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'notice.dart';
import 'free_board.dart';
import 'package:http/http.dart' as http;
import 'package:url_launcher/url_launcher.dart';

final dummyItems = [
  'https://cdn.pixabay.com/photo/2018/11/12/18/44/thanksgiving-3811492_1280.jpg',
  'https://cdn.pixabay.com/photo/2019/10/30/15/33/tajikistan-4589831_1280.jpg',
  'https://cdn.pixabay.com/photo/2019/11/25/16/15/safari-4652364_1280.jpg',
];

class mainPageBody extends StatelessWidget {
  const mainPageBody({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Main Page'),
      ),
      body: ListView(
        children: <Widget>[
          _buildNews(),
          _buildNoticeOfficial(context),
          _buildNoticeFree(context),
        ],
      ),
    );
  }

class _MainPageBodyState extends State<mainPageBody> {
  final List<Map<String, String>> categories = [
    {'name': 'news', 'text': '뉴스 보기'},//'뉴스' 버튼
  ];

  bool showInfoWindow = false;//'닫기' 버튼 사용하기 위함

  String selectedCategory = 'all';
  List<dynamic> categoryData = [];

  @override
  Widget build(BuildContext context) {
    return ListView(
      children: <Widget>[
        _buildNews(),
        _buildNoticeOfficial(context),
        _buildNoticeFree(context),
        _buildCategories(context),
        _buildCategoryData(),
      ],
    );
  }

  Widget _buildNews() {
    return CarouselSlider(
      options: CarouselOptions(
        height: 150,
        autoPlay: true,
      ),
      items: dummyItems.map((url) {
        return Builder(
          builder: (BuildContext context) {
            return Container(
              width: MediaQuery.of(context).size.width,
              margin: const EdgeInsets.symmetric(horizontal: 5.0),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(8.0),
                child: Image.network(
                  url,
                  fit: BoxFit.cover,
                ),
              ),
            );
          },
        );
      }).toList(),
    );
  }

  Widget _buildNoticeOfficial(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => const NoticePage()), // Replace NoticePage with your actual notice page class
        );
      },
      child: _buildNoticeListTile('[공지 사항] n번 버스 운행 중 고장으로 x정류장에서 차고지 행'),
    );
  }

  Widget _buildNoticeFree(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => const FreeBoardPage()), // Replace FreeBoardPage with your actual free board page class
        );
      },
      child: _buildFreeBoardTile('[자유 게시판] x대로 사고 발행 1차선 통제 중'),
    );
  }

  Widget _buildNoticeListTile(String title) {
    return ListTile(
      leading: const Icon(Icons.notifications_none),
      title: Text(title),
    );
  }
  
  Widget _buildFreeBoardTile(String title) {
    return ListTile(
      leading: Icon(Icons.edit),
      title: Text(title),
    );
  }

  Widget _buildCategories(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: categories.map((category) {
        final categoryName = category['name']!;
        final categoryText = category['text']!;
        final isActive = selectedCategory == categoryName;

        return Row(
          children: [
            GestureDetector(
              onTap: () {
                if (categoryName == 'news') {
                  //정보 검색 창 보기 전환
                  setState(() {
                    showInfoWindow = !showInfoWindow;
                  });

                  if (showInfoWindow) {
                    _fetchNewsData();
                  }
                }

                setState(() {
                  selectedCategory = categoryName;
                });
              },
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text(
                  categoryText,
                  style: TextStyle(
                    fontSize: 16.0,
                    fontWeight: isActive ? FontWeight.bold : FontWeight.normal,
                  ),
                ),
              ),
            ),
            if (categoryName == 'news')//'뉴스'에 대한 새로 고침 버튼
              Row(
                children: [
                  IconButton(
                    icon: Icon(Icons.refresh),
                    onPressed: () {
                      _fetchNewsData();
                      //새로 고침 버튼을 눌렀을 때 창 표시
                      setState(() {
                        showInfoWindow = true;
                      });
                    },
                  ),
                  if (showInfoWindow)//뉴스 창이 보일 때만 '닫기' 버튼 표시
                    IconButton(
                      icon: Icon(Icons.close),
                      onPressed: () {
                        //닫기'를 누르면 뉴스 닫기
                        setState(() {
                          showInfoWindow = false;
                        });
                      },
                    ),
                ],
              ),
          ],
        );
      }).toList(),
    );
  }

  Widget _buildCategoryData() {
    return showInfoWindow
        ? Column(
      children: [
        //카테고리를 표시하기 위한 기존 코드Data
        Column(
          children: categoryData.map((data) {
            try {
              //데이터 처리
              final title =
                  utf8.decode(data['title'].toString().codeUnits) ?? '제목 없음';
              final description = utf8.decode(
                  data['description'].toString().codeUnits) ??
                  '설명 없음';
              final url = utf8.decode(data['url'].toString().codeUnits) ?? '';

              return Card(
                child: ListTile(
                  title: Text(title),
                  subtitle: Text(description),
                  onTap: () async {
                    //여기에 해당 항목을 클릭했을 때의 동작을 추가
                    print('Selected URL: $url');

                    //url_launcher 패키지를 사용하여 URL 시작
                    if (await canLaunch(url)) {
                      await launch(url);
                    } else {
                      print('Could not launch $url');
                    }
                  },
                ),
              );
            } catch (e) {
              print('데이터 디코딩 오류: $e');
              print('문제가 되는 데이터: $data');
              return Container();
            }
          }).toList(),
        ),
      ],
    )
        : Container();
  }

  void _fetchNewsData() async {
    final url = 'https://back.otb.kr/news';

    print('뉴스 데이터 가져오기: $url');

    try {
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        final Map<String, dynamic> responseData = jsonDecode(response.body);
        print(response.body);//Server data check

        try {
          final articles = responseData['data'][0]['articles'] as List<dynamic>;
          for (final article in articles) {
            utf8.decode(article['title'].codeUnits);
            utf8.decode(article['description'].codeUnits);
            utf8.decode(article['url'].codeUnits);
          }
        } catch (e) {
          print('데이터 디코딩 오류 확인: $e');
          print('문제가 되는 데이터: ${responseData['data'][0]['articles']}');
        }

        setState(() {
          categoryData = responseData['data'][0]['articles'];
        });
      } else {
        print('데이터 가져오기 실패: ${response.statusCode}');
      }
    } catch (error) {
      print('데이터를 가져오는데 오류가 발생했습니다.: $error');
    }
  }
}
