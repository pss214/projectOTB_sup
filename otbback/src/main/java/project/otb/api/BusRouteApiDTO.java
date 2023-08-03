package project.otb.api;

import lombok.Getter;

import java.util.List;

@Getter
public class BusRouteApiDTO {
    private BusRoute busRoute;
    public BusRoute getBusRoute() {
        return busRoute;
    }
}
@Getter
class BusRoute{
    private int list_total_count; //노선 총 갯수
    @Getter
    private List<Row> row;

}
@Getter
class Row{
    String ROUTE; //노선명
    String ROUTE_ID; //노선 ID
}
