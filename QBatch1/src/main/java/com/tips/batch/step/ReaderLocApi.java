package com.tips.batch.step;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tips.batch.entity.FineDust;

import lombok.Data;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ReaderLocApi implements ItemReader<List<FineDust>>
{
    private String[] messages = { "Welcome to Spring Batch Example", "We use H2 Database for this example" };

    private int count = 0;

    RestTemplate restTemplate = new RestTemplate();
    
    private static final Logger logger = LoggerFactory.getLogger(ReaderLocApi.class);
    List<FineDust> fineDust = new ArrayList<FineDust>();
    
    @Override
    public List<FineDust> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException
    {
//        if (count < messages.length)
//        {
//            return "[ItemReader] " + messages[count++];
//        }
//        else
//        {
//            count = 0;
//        }

        StringBuffer result = new StringBuffer();
        
        try
        {
            // 미세먼지 공공 REST API
            /**
            String serviceId     = "ArpltnInforInqireSvc"    ;  // 대기오염정보 조회 서비스
            String operationName = "getCtprvnRltmMesureDnsty";  // 시도별 실시간 측정정보 조회
            String serviceKey    = "bg9choiwFZX5JYcIIF76jFiVYe0VwiWdxdpCUldbALWxzJLNZA4Ipq2Z1SVqkZyWSW88og%2Bt8EiOCX9J%2BB3ZUw%3D%3D";
            String numOfRows     = "1"   ;
            String pageNo        = "1"   ;
            String sidoName      = "서울" ;
            String version       = "1.3" ;
            String returnType    = "json";
            
            String strUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/"
                          + serviceId
                          + "/"
                          + operationName
                          + "?" + "serviceKey="   + serviceKey
                          + "&" + "numOfRows="    + numOfRows
                          + "&" + "pageNo="       + pageNo
                          + "&" + "sidoName="     + sidoName
                          + "&" + "ver="          + version
                          + "&" +  "_returnType=" + returnType;
            **/
            
            // 로컬 REST API
            String strUrl = "http://localhost:8081/finedustlist";
            
            // 호로록이 버전
            /**
            URL url = new URL(strUrl);
            
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            urlconnection.setRequestMethod("GET");
                
            BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            
            String returnLine;
            
            result.append("<xmp>");
            
            while((returnLine = br.readLine()) != null)
            {
                result.append(returnLine + "\n");
            }
                
            urlconnection.disconnect();
            **/
            
            //
            JsonNode item = null;
            URI uri = new URI(strUrl);
            URL url = new URL(strUrl);
            
            logger.error("1111111111111111111");
            
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            
            //HttpURLConnection urlconnection = (HttpURLConnection) uri.openConnection();
            
            //urlconnection.setRequestMethod("GET");
            
            logger.error("22222222222222222222");
            
            //BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            
            logger.error("3333333333333333333333333");
            
            logger.error("-------- response : " + response.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            
            logger.error("-------- root : " + root.toString());
            
            //fineDust = new ObjectMapper().readerFor(new TypeReference<ArrayList<FineDust>>() {}).readValue(item);
            
//            if (root.path("response").path("header").get("resultCode").asText().equals("99"))
//            {
//                logger.error(root.path("response").path("header").get("resultMsg").asText(), ReaderLocApi.class);
//                
//                return null;    //nullpointer exception...
//            }
            
//            if (root.path("response").path("body").path("items").has("item")) 
//            {
//                item = root.path("response").path("body").path("items").get("item");
//                
//                // Array일 경우와 하나일 경우를 분리해야 함, 한개를 List로 가져올 시 에러
//                if(item.isArray())
//                {
//                    fineDust = new ObjectMapper().readerFor(new TypeReference<ArrayList<FineDust>>() {}).readValue(item);
//                }
//                else
//                {
//                    fineDust.add(new ObjectMapper().readerFor(new TypeReference<FineDust>() {}).readValue(item));
//                }
//            }
//            else
//            {
//                logger.error("아파트 거래 데이터 없음, 더미 데이터로 처리111111.");
//                
//                //return getDummyAptTrade();
//                return null;
//            }
            
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
            //return getDummyAptTrade();
            return null;
        }
            
        //logger.debug("SiGu : , fineDust.size(): " + fineDust.size());
        //logger.debug("item.toString() : " + item.toString());

    }
    
    public List<FineDust> getDummyAptTrade()
    {
        List<FineDust> arr = new ArrayList<FineDust>();
        FineDust fineDust = new FineDust();
        
        fineDust.setReturnType("0"); //": "json",
        fineDust.setCoGrade    ("0"); //": "1",
        fineDust.setCoValue    ("0"); //": "0.8",
        fineDust.setDataTerm   ("0"); //": "",
        fineDust.setDataTime   ("0"); //": "2020-01-15 10:00",
        fineDust.setKhaiGrade  ("0"); //": "2",
        fineDust.setKhaiValue  ("0"); //": "82",
        fineDust.setMangName   ("0"); //": "도시대기",
        fineDust.setNo2Grade   ("0"); //": "2",
        fineDust.setNo2Value   ("0"); //": "0.036",
        fineDust.setNumOfRows  ("0"); //": "10",
        fineDust.setO3Grade    ("0"); //": "1",
        fineDust.setO3Value    ("0"); //": "0.007",
        fineDust.setPageNo     ("0"); //": "1",
        fineDust.setPm10Grade  ("0"); //": "2",
        fineDust.setPm10Grade1h("0"); //": "2",
        fineDust.setPm10Value  ("0"); //": "53",
        fineDust.setPm10Value24("0"); //": "42",
        fineDust.setPm25Grade  ("0"); //": "2",
        fineDust.setPm25Grade1h("0"); //": "3",
        fineDust.setPm25Value  ("0"); //": "37",
        fineDust.setPm25Value24("0"); //": "28",
        fineDust.setResultCode ("0"); //": "",
        fineDust.setResultMsg  ("0"); //": "",
        fineDust.setRnum       ("0"); //": 0,
        fineDust.setServiceKey ("0"); //": "",
        fineDust.setSidoName   ("0"); //": "",
        fineDust.setSo2Grade   ("0"); //": "1",
        fineDust.setSo2Value   ("0"); //": "0.005",
        fineDust.setStationCode("0"); //": "",
        fineDust.setStationName("0"); //": "중구",
        fineDust.setTotalCount ("0"); //": "",
        fineDust.setVer        ("0"); //": ""

        arr.add(fineDust);

        return arr;
    }
}
