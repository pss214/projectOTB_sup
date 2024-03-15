package project.otb.dto.apidto;

import lombok.Getter;

import java.util.List;

@Getter
public class BusStationApiDTO {
    List<DataList> DATA;

    @Getter
    public class DataList {
        String sttn_no; //정류장 고유번호
        String sttn_nm;// 정류장 이름
        String sttn_id;// 정류장 id
    }
}
