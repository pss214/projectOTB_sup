import 'dart:convert';
import 'dart:developer' as dev;
import 'package:flutter/material.dart';
import 'package:fluttertest/app_menu.dart';
import 'package:fluttertest/mypage.dart';
import 'package:http/http.dart' as http;
import 'package:kakao_flutter_sdk/kakao_flutter_sdk.dart';
import 'package:webview_flutter/webview_flutter.dart';

class PayMenu extends StatelessWidget {
  const PayMenu({super.key});

  Future<String> kakaopay() async {
    try {
      Map<String, String> headers = {
        "Content-Type": "application/json",
        "Authorization":
        "otb eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWsxMDE3MjpST0xFX1VTRVIiLCJpc3MiOiJzc3A2OTU5NyIsImlhdCI6MTcwODMzODMwOCwiZXhwIjoxNzA4MzQ5MTA4fQ.jGY_XUbCwfLHZaKkpGV9VPTWHnuf8DP0kM_ceJLAPhEaiEZJEV2ihtmj4IdhcDoebOsEULyfrbw3AdzebcuN0A",
      };
// 보내는 부분
      var url = Uri.parse("http://bak10172.asuscomm.com:10001/pay");

      var response = await http.get(url, headers: headers);
      print(utf8.decode(response.bodyBytes));
      if (response.statusCode == 200) {
//Map<String,dynamic> data=json.decode(response.body);

//next_redirect_mobile_url, next_redirect_app_url
        var nextRedirectAppUrl =
        jsonDecode(utf8.decode(response.bodyBytes))['data'][0]['next_redirect_app_url'];

//String nextRedirectAppUrl=data['next_redirect_app_url'];
        print("AppUrl : $nextRedirectAppUrl");
        return nextRedirectAppUrl.toString();
      } else {
        dev.log("HTTP request failed with status: ${response.statusCode}");
        return "";
      }
    } catch (e) {
      dev.log("Exception: $e");
      return "";
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'KakaoPay Test',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('KakaoPay Test'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ElevatedButton(
                onPressed: () async {
// 테스트용 버튼을 눌렀을 때 kakaopay 메소드 호출
                  String url = await kakaopay();
                  dev.log('KakaoPay Result: $url');
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => KakaoPayWebView(url: url),
                      ));
                },
                child: const Text('KakaoPay 테스트'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class KakaoPayWebView extends StatefulWidget {
  final String url;

  const KakaoPayWebView({super.key, required this.url});

  @override
  State<KakaoPayWebView> createState() => _KakaoPayWebView(url: url);
}

class _KakaoPayWebView extends State<KakaoPayWebView> {
  late final WebViewController controller;
  String url;

  _KakaoPayWebView({required this.url});

  @override
  void initState() {
    super.initState();
    controller = WebViewController() //웹뷰 동작 제어
      ..setJavaScriptMode(JavaScriptMode.unrestricted) //자바스크립트 실행 모드 설정
      ..loadRequest(Uri.parse(url))
      ..setNavigationDelegate(
        NavigationDelegate(
            onNavigationRequest: (NavigationRequest request) {
              if (request.url.startsWith('intent://kakaopay/pg?url=')) {
                launchBrowserTab(Uri.parse(url));
                return NavigationDecision.prevent;
              }
              return NavigationDecision.navigate;
            },

            onUrlChange: (UrlChange url){
              if(url.url.toString() == "http://bak10172.asuscomm.com:10001/pay/fail")
              {
                showDialog(
                    context: context,
                    barrierDismissible: false, //Alert창 말고 다른곳 클릭해도 반응 없게 만들기
                    barrierColor: Colors.white, // 뒷배경 색 지정
                    builder: (BuildContext context)=>AlertDialog(
                      title: Text("결제 정보"),
                      content: Text("결제가 실패했습니다."),
                      actions: [
                        ElevatedButton(
                            onPressed: ()=>Navigator.push(
                                context,
                                MaterialPageRoute(builder: (context) => AppMenu())),
                            child: Text("확인"))
                      ],
                    ));
              }

              else if(url.url.toString()=="http://bak10172.asuscomm.com:10001/pay/cancel"){
                showDialog(
                    context: context,
                    barrierDismissible: false, //Alert창 말고 다른곳 클릭해도 반응 없게 만들기
                    barrierColor: Colors.white, // 뒷배경 색 지정
                    builder: (BuildContext context)=>AlertDialog(
                      title: Text("결제 정보"),
                      content: Text("결제가 취소 되었습니다."),
                      actions: [
                        ElevatedButton(
                            onPressed: ()=>Navigator.push(
                                context,
                                MaterialPageRoute(builder: (context) => AppMenu())),
                            child: Text("확인"))
                      ],
                    ));
              }

              else if(url.url!.startsWith("http://bak10172.asuscomm.com:10001/pay/success")){
                showDialog(
                    context: context,
                    barrierDismissible: false, //Alert창 말고 다른곳 클릭해도 반응 없게 만들기
                    barrierColor: Colors.white, // 뒷배경 색 지정
                    builder: (BuildContext context)=>AlertDialog(
                      title: Text("결제 정보"),
                      content: Text("결제가 성공 되었습니다."),
                      actions: [
                        ElevatedButton(
                          autofocus: true,
                            onPressed:(){
                              Navigator.push(
                                  context,
                                  MaterialPageRoute(builder: (context)=>MyPage()));
                            },
                            child: Text("확인"))
                      ],
                    )
                );
              }
            }),
      );
  }

  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;
    return SizedBox(
      width: size.width,
      height: size.height,
      child: WebViewWidget(
        controller: controller,
      ),
    );
  }
}
