import 'dart:convert';

class UserResponse {
  final int status;       // 서버 응답 상태
  final String message;   // 서버 응답 메시지
  final List<User> data;  // 멤버 정보 목록

  UserResponse(this.status, this.message, this.data); //생성자ㅏㅏㅏㅏㅏㅏㅏㅏ

  //json 맵을 UserResponse 객체로 변환하는 공장 메서드
  factory UserResponse.fromJson(Map<String, dynamic> json) {
    final status = json['status'] is int ? json['status'] : -1;
    final message = json['message'] is String
        ? utf8.decode((json['message'] as String).codeUnits)
        : '';
    List<User> data = json['data'] is List
        ? (json['data'] as List).cast<Map>().map((e) {
            final json = e.cast<String, dynamic>();
            return User.fromJson(json);
          }).toList()
        : [];

    return UserResponse(status, message, data);
  }
}

// 사용자 정보 클라스
class User {
  final String name; //이름
  final String? password; //비번
  final String email; //이멜
  final String? token; //토큰
  final String? type; // ?!
  final String? cd; // 만든 날자
  final String? md; // 수정 날짜

  User(this.name, this.password, this.email, this.token, this.type, this.cd,
      this.md);

//제이슨 맵을 유저 객체로 변환하는 공장 메서드
  factory User.fromJson(Map<String, dynamic> json) {
    print(json);

    final name = json['username'] is String ? json['username'] as String : '';
    final password =
        json['password'] is String ? json['password'] as String : null;
    final email = json['email'] is String ? json['email'] as String : '';
    final token = json['token'] is String ? json['token'] as String : null;
    final type = json['type'] is String ? json['type'] as String : null;
    final cd = json['cd'] is String ? json['cd'] as String : null;
    final md = json['md'] is String ? json['md'] as String : null;

    return User(name, password, email, token, type, cd, md);
  }
 // 유저 객체를 제이슨으로
  Map<String, dynamic> toJson() => {
        'username': name,
        'password': password,
        'email': email,
        'token': token,
        'type': type,
        'cd': cd,
        'md': md,
      };
}
