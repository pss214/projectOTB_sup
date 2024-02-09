import 'dart:convert';
import 'dart:developer' as dev;
import 'package:flutter/material.dart';
import 'package:flutter_inappwebview/flutter_inappwebview.dart';
import 'package:http/http.dart' as http;

class PayMenu extends StatelessWidget {

  Future<String> kakaopay() async {

    try {
      Map<String, String> headers = {
        "Content-Type": "application/json",
        "Authorization": "otb eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWsxMDE3MjpST0xFX1VTRVIiLCJpc3MiOiJzc3A2OTU5NyIsImlhdCI6MTcwNzQxNzg5NCwiZXhwIjoxNzA3NDI4Njk0fQ.5ZBzqUCQFWg9GJNwxAv8PDvBD-EFm0oNdmTq1-38uIynH_ru347lw81OSpZ0zwmu0SCgHoxpKEoCPrg5d8uq-Q",
      };
      // 보내는 부분
      var url = Uri.parse("http://bak10172.asuscomm.com:10001/pay");
      var response = await http.get(url,headers: headers);
      print(utf8.decode(response.bodyBytes));
      if (response.statusCode == 200) {
        //Map<String,dynamic> data=json.decode(response.body);
        var nextRedirectAppUrl = jsonDecode(
            utf8.decode(response.bodyBytes))['data'][0]['next_redirect_mobile_url'];

        //String nextRedirectAppUrl=data['next_redirect_app_url'];

        print("AppUrl : ${nextRedirectAppUrl}");
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
          title: Text('KakaoPay Test'),
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
                  if(url.isNotEmpty){
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context)=>KakaoPayWebView(url: url))
                    );
                  }
                },
                child: Text('KakaoPay 테스트'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class KakaoPayWebView extends StatelessWidget{
  String url;
  KakaoPayWebView({required this.url});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('KakaoPay Test'),
      ),
      body: InAppWebView(
        initialUrlRequest: URLRequest(url: Uri.parse(url)),
      ),
    );
  }


}

