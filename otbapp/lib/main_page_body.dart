import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';

final dummyItems = [
  'https://cdn.pixabay.com/photo/2018/11/12/18/44/thanksgiving-3811492_1280.jpg',
  'https://cdn.pixabay.com/photo/2019/10/30/15/33/tajikistan-4589831_1280.jpg',
  'https://cdn.pixabay.com/photo/2019/11/25/16/15/safari-4652364_1280.jpg',
];

class mainPageBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListView(
      children: <Widget>[
        _buildNews(),
        _buildNoticeOfficial(),
        _buildNoticeFree(),
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
              margin: EdgeInsets.symmetric(horizontal: 5.0),
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

  Widget _buildNoticeOfficial() {
    final items = List.generate(1, (i) {//갯수 조절
      return ListTile(
        leading: Icon(Icons.notifications_none),
        title: Text('[공지 사항] n번 버스 운행 중 고장으로 x정류장에서 차고지 행'),//예시입니다 여기에 게시판 연동해서 실시간으로 띄워야 하는 곳
      );
    });

    return ListView(
      physics: NeverScrollableScrollPhysics(),
      shrinkWrap: true,
      children: items,
    );
  }

  Widget _buildNoticeFree() {
    final items = List.generate(1, (i) {
      return ListTile(
        leading: Icon(Icons.notifications_none),
        title: Text('[자유 게시판] x대로 사고 발행 1차선 통제 중'),
      );
    });

    return ListView(
      physics: NeverScrollableScrollPhysics(),
      shrinkWrap: true,
      children: items,
    );
  }
}
