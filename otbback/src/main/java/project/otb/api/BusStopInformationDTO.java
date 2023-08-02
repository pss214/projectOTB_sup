package project.otb.api;

import lombok.Getter;

import java.util.List;

@Getter
public class BusStopInformationDTO {
    private MsgBody msgBody;
}
@Getter
class MsgBody{
    List<ItemList> itemList;
}
@Getter
class ItemList{
    String busRouteId; //노선ID
    String busRouteNm; //노선명
    String busRouteAbrv; //노선 약칭
    String length; //노선 길이
    String busRouteType; // 노선 유형 1(공항),2(마을),3(간선),4(지선),5(순환),6(광역),7(인천),8(경기),9(폐지),0(공용)
    String stBegin; //기점
    String stEnd; //종점
    String term; //배차 간격
    String nextBus; //다음 도착 버스 시간
    String firstBusTm; // 금일 첫차 시간
    String lastBusTm; // 금일 막차 시간
    String firstBusTmLow; // 금일 저상 첫차 시간
    String lastBusTmLow; // 금일 저상 막차 시간
}
