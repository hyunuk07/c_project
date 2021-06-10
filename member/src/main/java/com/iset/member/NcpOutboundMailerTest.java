package com.iset.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbp.ncp.nes.ApiClient;
import com.nbp.ncp.nes.ApiRequest;
import com.nbp.ncp.nes.ApiResponse;
import com.nbp.ncp.nes.api.V1Api;
import com.nbp.ncp.nes.auth.PropertiesFileCredentialsProvider;
import com.nbp.ncp.nes.exception.ApiException;
import com.nbp.ncp.nes.marshaller.FormMarshaller;
import com.nbp.ncp.nes.marshaller.JsonMarshaller;
import com.nbp.ncp.nes.marshaller.XmlMarshaller;
import com.nbp.ncp.nes.model.EmailSendRequest;
import com.nbp.ncp.nes.model.EmailSendRequestRecipients;
import com.nbp.ncp.nes.model.EmailSendResponse;

@RestController
public class NcpOutboundMailerTest {
	
	private V1Api api;
	private ApiClient apiClient;
	
    private final String accessKey = "XRQWus30CZn8ivgiay3K";  // access key id (from portal or sub account)
    private final String secretKey = "sMXVdBFsALuRCz1cyy1EmhiqA4qKsniXG5lbDAQS";  // secret key (from portal or sub account)
	
	private void mailSetup() {
		
		if(apiClient == null)	apiClient = new ApiClient.ApiClientBuilder()
					.addMarshaller(JsonMarshaller.getInstance())
					.addMarshaller(XmlMarshaller.getInstance())
					.addMarshaller(FormMarshaller.getInstance())
					.setCredentials(new PropertiesFileCredentialsProvider("naverMailCredentials.properties").getCredentials())
					.setLogging(true)
					.build();

		if(api == null) api = new V1Api(apiClient);
	}

	@GetMapping("/member/sendJoinMail")
	public void sendJoinMail() throws Exception {
		mailSetup();
		EmailSendRequest req = new EmailSendRequest();
		req.setSenderAddress("itpeople1@hotmail.com");
		req.setSenderName("JV시스템");
		req.setTitle("유비온 본인인증 이메일 확인");
		req.setBody("유비온 네이버클라우드 메일발송 테스트 내용 <br/> <html><head></head><body><a href='http://www.ubion.co.kr'><div>본인 메일 인증하기</div></a></body></html>");
		
		List<EmailSendRequestRecipients> list = new ArrayList<EmailSendRequestRecipients>();
		
		EmailSendRequestRecipients r1 = new EmailSendRequestRecipients();
//		EmailSendRequestRecipients r2 = new EmailSendRequestRecipients();
		
		r1.setAddress("iset@iiiset.com");
		r1.setName("김현욱");
//		r2.setAddress("no2nd@ubion.co.kr");
//		r2.setName("문학도");
		
		list.add(r1);
//		list.add(r2);
		req.setRecipients(list);
		
		ApiResponse<EmailSendResponse> apiResponse =  mailsPost(req);
		System.out.println(apiResponse.toString());
	}
	
	@GetMapping("/member/verifyJoinMail")
	public void verifyJoinMail() {
		
	}
	
	
	/**
	 * 
	 * Email 발송 요청
	 * @param requestBody 메일발송 데이터 (required)
	 * //@param X_NCP_LANG 언어 (ko-KR, en-US, zh-CN), default:en-US (optional)
	 * @return EmailSendResponse
	 * @throws ApiException if fails to make API call
	 * @throws Exception if fails to make API call
	 */
	public ApiResponse<EmailSendResponse> mailsPost(EmailSendRequest requestBody) throws Exception {
		
		// path
		String path = "/mails";
		// query params
		Map<String, Object> queryParams = new HashMap<String, Object>();
		// form params
		Map<String, Object> formParams = new HashMap<String, Object>();
		// headers
		Map<String, Object> httpHeaders = new HashMap<String, Object>();
		
		httpHeaders.put("X-NCP-LANG", "ko-KR");
		httpHeaders.put("x-ncp-apigw-timestamp", Long.valueOf(System.currentTimeMillis()).toString());
		httpHeaders.put("x-ncp-iam-access-key", accessKey);
		httpHeaders.put("x-ncp-apigw-signature-v2", makeSignature());

		// accept
		final String[] accepts = {
			"application/json"
		};
		
		String accept = apiClient.selectHeaderAccept(accepts);
		httpHeaders.put("accept", accept);

		// content-type
		final String[] contentTypes = {
			"application/json"
		};
		String contentType = apiClient.selectHeaderContentType(contentTypes);
		httpHeaders.put("content-type", contentType);
		

		ApiRequest apiRequest = new ApiRequest("POST", path, queryParams, formParams, httpHeaders, requestBody, false, false);
		return apiClient.call(apiRequest, EmailSendResponse.class);
	}

	//x-ncp-apigw-signature-v2 값으로 사용
	public String makeSignature() throws Exception {
	    String space = " ";  // 공백
	    String newLine = "\n";  // 줄바꿈
	    String method = "POST";  // HTTP 메소드
	    String url = "/api/v1/mails";  // 도메인을 제외한 "/" 아래 전체 url (쿼리스트링 포함)
	    String timestamp = Long.valueOf(System.currentTimeMillis()).toString();  // 현재 타임스탬프 (epoch, millisecond)

	    String message = new StringBuilder()
	        .append(method)
	        .append(space)
	        .append(url)
	        .append(newLine)
	        .append(timestamp)
	        .append(newLine)
	        .append(accessKey)
	        .toString();

	    SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
	    Mac mac = Mac.getInstance("HmacSHA256");
	    mac.init(signingKey);

	    byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
	    String encodeBase64String = Base64.encodeBase64String(rawHmac);

	  return encodeBase64String;

	}
	
	public static void main(String[] args) throws Exception {
		NcpOutboundMailerTest mail = new NcpOutboundMailerTest();
		mail.sendJoinMail();
	}
}
