class BusStop {
  final String arsId;//정류장id
  final String stationNm;//정류장이름

  BusStop( this.arsId, this.stationNm);

  factory BusStop.fromJson(dynamic json){
    return BusStop(
        json['arsId'] as String, json['stationNm'] as String);
  }

  @override
  String toString() {
    return 'BusStop{arsId: $arsId, stationNm: $stationNm}';
  }
}