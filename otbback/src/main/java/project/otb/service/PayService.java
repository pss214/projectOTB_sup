package project.otb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import project.otb.dto.kakao.PayApproveDTO;
import project.otb.dto.kakao.PayApproveResDTO;
import project.otb.dto.kakao.PayReadyDTO;
import project.otb.dto.kakao.PayReadyResDTO;

@Service
@RequiredArgsConstructor
public class PayService {
    private PayReadyResDTO kakaoPaydto;
    @Value("${kakao.secrit}")
    private String key;

    public PayReadyResDTO PayReady(String username){
        WebClient webClient = WebClient.builder().build();

        PayReadyDTO body = PayReadyDTO.builder()
                .cid("TC0ONETIME")
                .partner_order_id("otb")
                .partner_user_id(username)
                .item_name("탑승권")
                .quantity(1)
                .total_amount(1450)
                .tax_free_amount(200)
                .approval_url("http://localhost:8080/pay/success")
                .cancel_url("http://localhost:8080/pay/cancel")
                .fail_url("http://localhost:8080/pay/fail")
                .build();
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";

        kakaoPaydto = webClient.post()
                .uri(url)
                .header("Authorization", "SECRET_KEY "+key)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(PayReadyResDTO.class)
                .block();
        kakaoPaydto.setUsername(username);
        return kakaoPaydto;
    }

    public PayApproveResDTO PayApprove(String pgToken){
        WebClient webClient = WebClient.builder().build();

        PayApproveDTO body = PayApproveDTO.builder()
                .cid("TC0ONETIME")
                .tid(kakaoPaydto.getTid())
                .partner_order_id("otb")
                .partner_user_id(kakaoPaydto.getUsername())
                .pg_token(pgToken)
                .build();
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
        PayApproveResDTO approveResDTO = webClient.post()
                .uri(url)
                .header("Authorization", "SECRET_KEY DEV153482743D993E71527FB019E047BD361DE14")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                    throw new RuntimeException(String.valueOf(clientResponse.bodyToMono(String.class)));
                })
                .bodyToMono(PayApproveResDTO.class)
                .block();
        // PaymentRepository.save(approveResDTO);
        return approveResDTO;
    }

}
