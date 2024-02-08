package project.otb.dto.kakao;

import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

@Data
public class PayApproveResDTO {
    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
    private Amount amount;
    private Card_info card_info;
    private String item_name;
    private String item_code;
    private int quantity;
    private String created_at;
    private String approved_at;
    private String payload;
}
