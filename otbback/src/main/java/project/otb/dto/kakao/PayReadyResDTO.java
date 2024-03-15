package project.otb.dto.kakao;

import lombok.Data;

import java.util.Date;

@Data
public class PayReadyResDTO {
    private String tid, next_redirect_pc_url, next_redirect_mobile_url,next_redirect_app_url;
    private String username;
    private Date created_at;
}
