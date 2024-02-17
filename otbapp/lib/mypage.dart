import 'dart:convert';  // json.decode를 사용하기 위해 추가
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:chapter07/reserveBus.dart';
import 'package:flutter/gestures.dart';
import 'package:barcode_widget/barcode_widget.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: '마이 페이지',
      home: const MyProfilePage(),
      theme: ThemeData(
        primarySwatch: Colors.lightGreen,
      ),
    );
  }
}

class MyProfilePage extends StatefulWidget {
  const MyProfilePage({Key? key}) : super(key: key);

  @override
  State<MyProfilePage> createState() => _MyPageState();
}

class _MyPageState extends State<MyProfilePage> {
  bool isEditing = false;
  bool showQrCode = false;
  String memberName = '임시'; // 초기값

  //버스 정보 받아오기
  String start = '';
  String arrive = '';
  String busId = '';
  String vehId = '';

  @override
  void initState() {
    super.initState();
    // 페이지가 생성될 때 초기 회원 정보를 불러오는 메서드 호출
    loadMemberInfo();
    //요청 전에 초기화
    start = '';
    arrive = '';
    busId = '';
    vehId = '';
    fetchInformation();//결제 정보 패치 받아오기
  }

  //QR
  Future<void> fetchInformation() async {
    try {
      //서버 URL
      var url = Uri.parse('http://bak10172.asuscomm.com:10001/reservation');

      //HTTP 헤더 설정
      var headers = {
        'Content-Type': 'application/json',
        'Authorization': 'otb eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWsxMDE3MjpST0xFX1VTRVIiLCJpc3MiOiJzc3A2OTU5NyIsImlhdCI6MTcwNDQzNzQ2NywiZXhwIjoxNzA0NDQ4MjY3fQ.31C9RQ-TaxIgPXCxgh_3RLUk7EeMXPSxpbYLDajNQ3Qmp46zYViCzKVMPYRPi2I5lLhgkkScFPlnsZPeyyhzdg',
      };

      //요청 데이터
      var requestData = {
        "depart_station": start,
        "arrive_station": arrive,
        "busnumber": busId,
        "busnumberplate": vehId,
      };

      /*// HTTP POST 요청 보내기
      var response = await http.post(url, body: json.encode(requestData), headers: headers);

      // HTTP 상태 코드 확인
      if (response.statusCode == 201) {
        // 성공적으로 데이터를 받았을 때, JSON 디코딩하여 변수에 할당 (여기서는 필요한 작업이 없을 것 같습니다)
        print('Reservation successful!');
      } else {
        print('Failed to make a reservation. Status code: ${response.statusCode}');
      }
    } catch (e) {
      print('Error making a reservation: $e');
    }
  }*/
      // HTTP POST 요청 보내기
      var response = await http.post(url, body: json.encode(requestData), headers: headers);

      // HTTP 상태 코드 확인
      if (response.statusCode == 201) {
        // 성공적으로 데이터를 받았을 때, JSON 디코딩하여 변수에 할당
        final Map<String, dynamic> responseData = json.decode(response.body);

        // Set the values for QR code data
        setState(() {
          start = responseData['depart_station'];
          arrive = responseData['arrive_station'];
          busId = responseData['busnumber'];
          vehId = responseData['busnumberplate'];
        });

        print('Reservation successful!');
      } else {
        print('Failed to make a reservation. Status code: ${response.statusCode}');
      }
    } catch (e) {
      print('Error making a reservation: $e');
    }
  }

  // 회원 정보를 불러오는 메서드
  Future<void> loadMemberInfo() async {
    try {
      var response = await http.get(
        Uri.parse('http://bak10172.asuscomm.com:10001/member'),
        headers: {
          'Authorization': 'otb ',
        },
      );

      if (response.statusCode == 200) {
        // 성공적으로 응답을 받아왔을 때
        final Map<String, dynamic> responseData = json.decode(response.body);
        setState(() {
          memberName = responseData['member']; // 서버에서 받아온 회원 이름으로 업데이트
        });
      } else {
        // 응답이 실패했을 때
        print('Failed to load member info: ${response.statusCode}');
      }
    } catch (e) {
      // 예외 처리
      print('Error loading member info: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('마이 페이지'),
        elevation: 0.0,
        backgroundColor: Colors.lightGreen,
        centerTitle: true,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.of(context).popUntil((route) => route.isFirst);
          },
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              '회원 정보',
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20.0,
              ),
            ),
            const SizedBox(height: 20.0),
            Container(
              decoration: BoxDecoration(
                border: Border.all(color: Colors.grey),
                borderRadius: BorderRadius.circular(8.0),
              ),
              padding: const EdgeInsets.all(16.0),
              child: Column(  // const 키워드 제거
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        '회원 이름:',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 18.0,
                        ),
                      ),
                      Expanded(
                        child: Text(
                          memberName,
                          textAlign: TextAlign.end,
                          style: TextStyle(fontSize: 16.0),
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 8.0),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        '이메일:',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 18.0,
                        ),
                      ),
                      Expanded(
                        child: Text(
                          '임시',
                          textAlign: TextAlign.end,
                          style: TextStyle(fontSize: 16.0),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20.0),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton(
                  onPressed: () {
                    setState(() {
                      isEditing = !isEditing;
                      showQrCode = false;//QR 코드 숨기기
                    });
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orangeAccent,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(18.0),
                    ),
                  ),
                  child: const Text(
                    '수정하기',
                    style: TextStyle(fontSize: 16.0, color: Colors.white),
                  ),
                ),
                ElevatedButton(
                  onPressed: () {
                    // 회원 탈퇴 버튼 눌렀을 때 실행되는 기능 추가
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orangeAccent,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(18.0),
                    ),
                  ),
                  child: const Text(
                    '회원 탈퇴',
                    style: TextStyle(fontSize: 16.0, color: Colors.white),
                  ),
                ),
                ElevatedButton(
                  onPressed: () {
                    setState(() {
                      showQrCode = !showQrCode; // Show QR code when this button is pressed
                    });
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orangeAccent,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(18.0),
                    ),
                  ),
                  child: Text(
                    showQrCode ? 'QR 닫기' : 'QR 보기',
                    style: TextStyle(fontSize: 16.0, color: Colors.white),
                  ),
                ),//통신 성공하면 이 코드 파기 후 아래 코드 쓰면 됩니다. 주석 바꿔서 써보면서 어떤 동작하는지는 파악하세요.
                /*ElevatedButton(
                  onPressed: () {
                    setState(() {
                      if (start.isNotEmpty && arrive.isNotEmpty && busId.isNotEmpty && vehId.isNotEmpty) {
                        showQrCode = !showQrCode;
                      } else {
                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(
                            content: RichText(
                              textAlign: TextAlign.center,
                              text: TextSpan(
                                text: '결제 내역이 없습니다. 결제 후 확인해주세요.',
                                style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.white,
                                ),
                                children: <TextSpan>[
                                  TextSpan(
                                    text: '\n버스 결제하기',
                                    style: TextStyle(
                                      color: Colors.blue,
                                      decoration: TextDecoration.underline,
                                    ),
                                    recognizer: TapGestureRecognizer()
                                      ..onTap = () {
                                        Navigator.push(
                                          context,
                                          MaterialPageRoute(
                                            builder: (context) => BusReservePage(),
                                          ),
                                        );
                                      },
                                  ),
                                ],
                              ),
                            ),
                            duration: Duration(seconds: 6),//메시지를 보여줄 시간
                            backgroundColor: Colors.orangeAccent,
                          ),
                        );
                      }
                    });
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orangeAccent,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(18.0),
                    ),
                  ),
                  child: Text(
                    showQrCode ? 'QR 닫기' : 'QR 보기',
                    style: TextStyle(fontSize: 16.0, color: Colors.white),
                  ),
                ),*/
              ],
            ),
            const SizedBox(height: 20.0),
            if (isEditing)
              Column(
                children: [
                  const TextField(
                    decoration: InputDecoration(
                      hintText: '새로운 아이디',
                    ),
                  ),
                  const TextField(
                    decoration: InputDecoration(
                      hintText: '새로운 비밀번호',
                    ),
                  ),
                  const TextField(
                    decoration: InputDecoration(
                      hintText: '비밀번호 확인',
                    ),
                  ),
                  const SizedBox(height: 20.0),
                  ElevatedButton(
                    onPressed: () async {
                      // 저장 버튼 눌렀을 때 실행되는 기능 추가
                      var response = await http.post(
                        Uri.parse('http://bak10172.asuscomm.com/member'),
                        headers: {
                          'Authorization': 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzpST0xFX1VTRVIiLCJpc3MiOiJzc3A2OTU5NyIsImlhdCI6MTcwNzgwNjczNiwiZXhwIjoxNzA3ODE3NTM2fQ.GLatMK44O4wza5Wp4QsAVDGQ9B_evsVt2o5SEVRzLrYazgK1AaNVlFMdy5ySvHnBtDawnxdaUjbzflhVwRvqCA',
                        },
                      );
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.orangeAccent,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(18.0),
                      ),
                    ),
                    child: const Text(
                      '저장 하기',
                      style: TextStyle(fontSize: 16.0, color: Colors.white),
                    ),
                  ),
                ],
              ),
            if (showQrCode)
              Column(
                children: [
                  Center(
                    child: BarcodeWidget(
                      barcode: Barcode.qrCode(),
                      color: Colors.black,
                      data: 'Start: $start\nArrive: $arrive\nBus ID: $busId\nVehicle ID: $vehId',
                      width: 200.0,
                      height: 200.0,
                    ),
                  ),
                  const SizedBox(height: 20.0),
                  //제대로 가져오는지 확인하기 위해 ('$변수명' 만 사용해도 됨), 통신 성공하면 역시 파기해도 무방
                  Text('출발 정류장: ${start.isNotEmpty ? start : 'N/A'}'),
                  Text('도착 정류장: ${arrive.isNotEmpty ? arrive : 'N/A'}'),
                  Text('차량번호: ${busId.isNotEmpty ? busId : 'N/A'}'),
                  Text('차량고유번호: ${vehId.isNotEmpty ? vehId : 'N/A'}'),
                ],
              ),
          ],
        ),
      ),
    );
  }
}
