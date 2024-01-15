package project.otb.dto.apidto;

import lombok.Getter;

import java.util.List;

@Getter
public class BusStationRouteDTO {
    private Body msgBody;

    @Getter
    public class Body {
        List<Item> itemList;
    }

    @Getter
    public class Item {
        String busRouteId;
        String busRouteNm;
        String busRouteAbrv;
        String seq;
        String section;
        String station;
        String arsId;
        String stationNm;
        String gpsX;
        String gpsY;
        String posX;
        String posY;
        String fullSectDist;
        String direction;
        String stationNo;
        String routeType;
        String beginTm;
        String lastTm;
        String trnstnid;
        String sectSpd;
        String transYn;
    }
}