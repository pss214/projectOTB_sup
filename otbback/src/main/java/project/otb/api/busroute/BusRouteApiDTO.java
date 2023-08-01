package project.otb.api.busroute;

import lombok.Getter;

import java.util.List;

@Getter
public class BusRouteApiDTO {
    private BusRoute busRoute;
    public BusRoute getBusRoute() {
        return busRoute;
    }
    public void setBusRoute(BusRoute busRoute) {
        this.busRoute = busRoute;
    }
}
@Getter
class BusRoute{
    private int list_total_count;
    private List<Row> row;

}
@Getter
class Row{
    String ROUTE;
    String ROUTE_ID;
}
