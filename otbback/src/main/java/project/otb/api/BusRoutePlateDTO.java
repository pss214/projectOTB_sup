package project.otb.api;

import lombok.Getter;

import java.util.List;

@Getter
public class BusRoutePlateDTO {
    private Msdy msgBody;
}
@Getter
class Msdy{
    List<Itli> itemList;
}
@Getter
class Itli{
    String plainNo;
}
