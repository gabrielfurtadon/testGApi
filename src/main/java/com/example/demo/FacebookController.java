package com.example.demo;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.Campaign;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://gabrielfurtadon.github.io")
@RestController
@RequestMapping("/api")
public class FacebookController {

	 @PostMapping("/saveToken")
	    public String saveToken(@RequestBody String requestBody) {
	        JSONObject jsonObject = new JSONObject(requestBody);
	        String accessToken = jsonObject.getString("token");
	        String adAccountId = jsonObject.getString("adAccountId");

	        APIContext context = new APIContext(accessToken).enableDebug(true);

	        try {
	            AdAccount account = new AdAccount(adAccountId, context);
	            List<Campaign> campaigns = account.getCampaigns()
	                .requestField("name")
	                .execute();

	            if (!campaigns.isEmpty()) {
	                String campaignName = campaigns.get(0).getFieldName();
	                return "{\"campaignName\": \"" + campaignName + "\"}";
	            } else {
	                return "{\"message\": \"Nenhuma campanha encontrada\"}";
	            }

	        } catch (APIException e) {
	            e.printStackTrace();
	            return "{\"error\": \"Erro ao acessar a API\"}";
	        }
	    }
}
