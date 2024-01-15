package project.otb.dto.apidto;

import lombok.Getter;

import java.util.List;

@Getter
public class BusRoutePlateDTO {
    private Msdy msgBody;
    @Getter
    public class Msdy{
        List<Itli> itemList;
    }
    @Getter
    public class Itli{
        String plainNo;
    }
}
