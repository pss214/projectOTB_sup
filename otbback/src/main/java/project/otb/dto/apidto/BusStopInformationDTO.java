package project.otb.dto.apidto;

import lombok.Getter;

import java.util.List;

@Getter
public class BusStopInformationDTO {
    private MsgBody msgBody;

    @Getter
    public class MsgBody {
        List<ItemList> itemList;
    }

    @Getter
    public class ItemList {
        String rtNm;
        String busRouteId;
        String arrmsg1;
        String arrmsg2;
        String vehId1;
    }
}
