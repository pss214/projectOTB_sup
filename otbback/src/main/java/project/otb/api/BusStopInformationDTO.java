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
    String rtNm;
    String busRouteId;
    String arrmsg1;
    String arrmsg2;
}
