class Info {
  final String rtNm;
  final String busRouteId;
  final String arrmsg1;
  final String arrmsg2;
  final String vehId1;
  Info(this.rtNm, this.busRouteId, this.arrmsg1, this.arrmsg2, this.vehId1);

  factory Info.fromJson(dynamic json){
    return Info(json['rtNm'] as String, json['busRouteId'] as String,
        json['arrmsg1'] as String, json['arrmsg2'] as String, json['vehId1'] as String);
  }

  @override
  String toString() {
    return '{ $rtNm, $busRouteId, $arrmsg1, $arrmsg2, $vehId1';
  }
}
