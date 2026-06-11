package com.genge.GengeAPIs.common.service;

import com.genge.GengeAPIs.common.entity.SMS;
import com.genge.GengeAPIs.common.repository.SMSRepository;
import com.genge.GengeAPIs.config.SystemEnv;
import com.genge.GengeAPIs.response.APIResponse;
import com.genge.GengeAPIs.user.entity.User;
import com.genge.GengeAPIs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final SMSRepository smsRepository;
    private final SystemEnv systemEnv;

    public APIResponse<Object> sendMessage(
            String message,
            List<String> phoneNumbers,
            Long userId
    ){
        if (message == null || message.trim().isEmpty()) {
            return APIResponse.fail("Message cannot be empty.");
        }

        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            return APIResponse.fail("No phone numbers provided.");
        }

        try {

            User user = userRepository
                    .findById(userId)
                    .orElse(null);

            List<Map<String, String>> recipients = new ArrayList<>();
            List<SMS> smsLogs = new ArrayList<>();

            for (String phoneNumber : phoneNumbers) {

                if (phoneNumber == null ||
                        phoneNumber.trim().isEmpty()) {
                    continue;
                }

                Map<String, String> recipient =
                        new HashMap<>();

                recipient.put(
                        "name",
                        user != null
                                ? user.getFullName()
                                : ""
                );

                recipient.put(
                        "phoneNumber",
                        phoneNumber
                );

                recipients.add(recipient);

                SMS sms = new SMS();

                sms.setRecipient(phoneNumber);
                sms.setMessage(message);

                if (user != null) {
                    sms.setUser(user);
                }

                smsLogs.add(sms);
            }

            // no valid recipients
            if (recipients.isEmpty()) {
                return APIResponse.fail(
                        "No valid phone numbers found."
                );
            }

            // payload
            Map<String, Object> payload =
                    new HashMap<>();

            payload.put("message", message);
            payload.put("recipients", recipients);

            // headers
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            headers.set(
                    "apiKey",
                    systemEnv.smsApiKey
            );

            headers.set(
                    "senderIdentity",
                    systemEnv.senderId
            );

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(payload, headers);

            // send sms
            ResponseEntity<Map> response =
                    restTemplate.exchange(
                            "https://os.cup.co.tz/api/v1/sms/dispatch",
                            HttpMethod.POST,
                            request,
                            Map.class
                    );

            Map body = response.getBody();

            if (body == null) {

                // update failed logs
                for (SMS sms : smsLogs) {
                    sms.setStatus("FAILED");
                }

                smsRepository.saveAll(smsLogs);

                return APIResponse.fail(
                        "Empty response from SMS gateway."
                );
            }

            Map data = (Map) body.get("data");

            String status =
                    data != null
                            ? String.valueOf(
                            data.getOrDefault(
                                    "status",
                                    "FAILED"
                            )
                    )
                            : "FAILED";

            String requestId =
                    data != null
                            ? String.valueOf(
                            data.getOrDefault(
                                    "requestId",
                                    "N/A"
                            )
                    )
                            : "N/A";

            String gatewayMessage =
                    String.valueOf(
                            body.getOrDefault(
                                    "message",
                                    "No response message"
                            )
                    );

            // update sms logs
            for (SMS sms : smsLogs) {
                sms.setStatus(status);
                sms.setRequestId(requestId);
                sms.setGatewayResponse(gatewayMessage);
            }

            // save all sms logs
            smsRepository.saveAll(smsLogs);

            // response payload
            Map<String, Object> result =
                    new HashMap<>();

            result.put("status", status);
            result.put("requestId", requestId);
            result.put(
                    "gatewayMessage",
                    gatewayMessage
            );

            result.put(
                    "totalRecipients",
                    recipients.size()
            );

            return APIResponse.success(
                    "SMS sent successfully.",
                    result
            );

        } catch (Exception e) {

            e.printStackTrace();

            return APIResponse.fail(
                    "SMS sending failed: "
                            + e.getMessage()
            );
        }
    }
}
