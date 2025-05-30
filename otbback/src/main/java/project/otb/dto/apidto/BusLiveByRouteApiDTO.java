package project.otb.dto.apidto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BusLiveByRouteApiDTO {
    private Mb msgBody;

    @Getter
    public class Mb {
        private List<LiveBus> itemList;
    }

    @Getter
    public class LiveBus {
        String stNm; // 정류장 이름
        String arsId; // 정류장 번호
        String rtNm; //버스명
        String plainNo1; //버스의 차량번호
        String arrmsg1; // 도착 예정 시간(메세지)
        String stationNm1; //버스의 지금 정류장 위치
        String nstnId1; //도착 예정 버스의 지금 정류장 ID
    }
}