import 'dart:convert';
import 'package:barcode_widget/barcode_widget.dart';
import 'package:flutter/material.dart';
import 'package:fluttertest/data/model/UserResponse.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

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
  User? user;

  String start = '';
  String arrive = '';
  String busId = '';
  String vehId = '';

  final newPasswordTextFieldController = TextEditingController();
  final rePasswordTextFieldController = TextEditingController();

  @override
  void initState() {
    super.initState();
    loadMemberInfo();
    start = '';
    arrive = '';
    busId = '';
    vehId = '';
    fetchInformation();
  }

  @override
  void dispose() {
    newPasswordTextFieldController.dispose();
    rePasswordTextFieldController.dispose();
    super.dispose();
  }

  Future<void> fetchInformation() async {
    try {
      var url = Uri.parse('http://bak10172.asuscomm.com:10001/reservation');
      var headers = {
        'Content-Type': 'application/json',
        'Authorization':
        'otb eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWsxMDE3MjpST0xFX1VTRVIiLCJpc3MiOiJzc3A2OTU5NyIsImlhdCI6MTcwNDQzNzQ2NywiZXhwIjoxNzA0NDQ4MjY3fQ.31C9RQ-TaxIgPXCxgh_3RLUk7EeMXPSxpbYLDajNQ3Qmp46zYViCzKVMPYRPi2I5lLhgkkScFPlnsZPeyyhzdg',
      };

      var requestData = {
        "depart_station": start,
        "arrive_station": arrive,
        "busnumber": busId,
        "busnumberplate": vehId,
      };

      var response = await http.post(
        url,
        body: json.encode(requestData),
        headers: headers,
      );

      if (response.statusCode == 201) {
        final Map<String, dynamic> responseData = json.decode(response.body);
        setState(() {
          start = responseData['depart_station'];
          arrive = responseData['arrive_station'];
          busId = responseData['busnumber'];
          vehId = responseData['busnumberplate'];
        });
        print('Reservation successful!');
      } else {
        print(
            'Failed to make a reservation. Status code: ${response.statusCode}');
      }
    } catch (e) {
      print('Error making a reservation: $e');
    }
  }

  Future<String?> getToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString('jwtToken');
  }

  Future<void> loadMemberInfo() async {
    final token = await getToken();
    if (token?.isNotEmpty != true) return;

    try {
      var response = await http.get(
        Uri.parse('http://bak10172.asuscomm.com:10001/member'),
        headers: {
          'Authorization': 'otb $token',
        },
      );

      if (response.statusCode == 200) {
        final userResponse =
        UserResponse.fromJson(json.decode(response.body));
        debugPrint(userResponse.message);

        WidgetsBinding.instance?.addPostFrameCallback((_) {
          setState(() {
            user = userResponse.data.firstOrNull;
          });
        });
      } else {
        print('Failed to load member info: ${response.statusCode}');
      }
    } catch (e, s) {
      print('Error loading member info: $e');
      debugPrintStack(stackTrace: s);
    }
  }

  Future<void> updatePassword() async {
    final token = await getToken();
    if (token?.isNotEmpty != true) return;

    final user = this.user;
    if (user == null) return;

    final newPassword = newPasswordTextFieldController.text.trim();
    final rePassword = rePasswordTextFieldController.text.trim();

    if (newPassword.isEmpty || rePassword.isEmpty) return;
    if (newPassword != rePassword) {
      Fluttertoast.showToast(
        msg: '비밀번호가 다릅니다. 다시 입력해 주세요.',
      );
      return;
    }

    final json = {
      'password': newPassword,
    };

    Map<String, String> headers = {
      "Content-Type": "application/json",
      'Authorization': 'otb $token',
    };

    var url = Uri.parse('http://bak10172.asuscomm.com:10001/member');
    var response =
    await http.post(url, body: jsonEncode(json), headers: headers);

    final userResponse = UserResponse.fromJson(jsonDecode(response.body));
    Fluttertoast.showToast(
      msg: userResponse.message,
    );

    if (userResponse.status == 201) {
      setState(() {
        isEditing = false;
        newPasswordTextFieldController.clear();
        rePasswordTextFieldController.clear();
      });
    }
  }

  Future<void> deleteAccount() async {
    final token = await getToken();
    if (token?.isNotEmpty != true) return;

    try {
      var response = await http.delete(
        Uri.parse('http://bak10172.asuscomm.com:10001/member'),
        headers: {
          'Authorization': 'otb $token',
        },
      );

      if (response.statusCode == 200) {
        final userResponse =
        UserResponse.fromJson(json.decode(response.body));
        Fluttertoast.showToast(
          msg: userResponse.message,
        );

        if (userResponse.status == 200) {
          // 회원 탈퇴 성공
          // 로그인 페이지로 이동하거나 다른 작업을 수행할 수 있습니다.
        }
      } else {
        print('Failed to delete account: ${response.statusCode}');
      }
    } catch (e, s) {
      print('Error deleting account: $e');
      debugPrintStack(stackTrace: s);
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
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      const Text(
                        '회원 이름:',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 18.0,
                        ),
                      ),
                      Expanded(
                        child: Text(
                          user?.name ?? '',
                          textAlign: TextAlign.end,
                          style: const TextStyle(fontSize: 16.0),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 8.0),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      const Text(
                        '이메일:',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 18.0,
                        ),
                      ),
                      Expanded(
                        child: Text(
                          user?.email ?? '',
                          textAlign: TextAlign.end,
                          style: const TextStyle(fontSize: 16.0),
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
                      showQrCode = false;
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
                    deleteAccount(); // 회원 탈퇴 버튼 눌렀을 때 호출
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
                      showQrCode = !showQrCode;
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
                    style: const TextStyle(fontSize: 16.0, color: Colors.white),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 20.0),
            if (isEditing)
              Column(
                children: [
                  TextField(
                    controller: newPasswordTextFieldController,
                    decoration: const InputDecoration(
                      hintText: '새로운 비밀번호',
                    ),
                  ),
                  TextField(
                    controller: rePasswordTextFieldController,
                    decoration: const InputDecoration(
                      hintText: '비밀번호 확인',
                    ),
                  ),
                  const SizedBox(height: 20.0),
                  ElevatedButton(
                    onPressed: () async {
                      await updatePassword();
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
                      data:
                      'Start: $start\nArrive: $arrive\nBus ID: $busId\nVehicle ID: $vehId',
                      width: 200.0,
                      height: 200.0,
                    ),
                  ),
                  const SizedBox(height: 20.0),
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
