class BusStops {
  String stopNm;
  String ycode;
  String stopNo;
  String xcode;
  String stopType;
  String nodeId;


  BusStops({
    required this.ycode,
    required this.xcode,
    required this.stopNm,
    required this.stopNo,
    required this.stopType,
    required this.nodeId});

  factory BusStops.fromJson(Map<String,dynamic>json){
    return BusStops(
      stopNm: json['stop_nm'] as String,
      ycode: json['ycode'] as String,
      stopNo: json['stop_no'] as String,
      xcode: json['xcode'] as String,
      stopType: json['stop_type'] as String,
      nodeId: json['node_id'] as String,
    );
  }

}