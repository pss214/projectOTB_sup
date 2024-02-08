package project.otb.dto.kakao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayApproveDTO {
    private String cid;
    private String cid_secret;
    private String tid;
    private String partner_order_id;
    private String partner_user_id;
    private String pg_token;
}
