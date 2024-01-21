import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'notice.dart';
import 'free_board.dart';

final dummyItems = [
  'https://cdn.pixabay.com/photo/2018/11/12/18/44/thanksgiving-3811492_1280.jpg',
  'https://cdn.pixabay.com/photo/2019/10/30/15/33/tajikistan-4589831_1280.jpg',
  'https://cdn.pixabay.com/photo/2019/11/25/16/15/safari-4652364_1280.jpg',
];

class mainPageBody extends StatelessWidget {
  const mainPageBody({super.key});

  @override
  Widget build(BuildContext context) {
    return ListView(
      children: <Widget>[
        _buildNews(),
        _buildNoticeOfficial(context),
        _buildNoticeFree(context),
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
      child: _buildNoticeListTile('[자유 게시판] x대로 사고 발행 1차선 통제 중'),
    );
  }

  Widget _buildNoticeListTile(String title) {
    return ListTile(
      leading: const Icon(Icons.notifications_none),
      title: Text(title),
    );
  }
}
