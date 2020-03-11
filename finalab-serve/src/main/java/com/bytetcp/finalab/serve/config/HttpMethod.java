package com.bytetcp.finalab.serve.config;

import com.alibaba.fastjson.JSONObject;
import com.bytetcp.finalab.common.base.HttpResult;
import com.bytetcp.finalab.serve.course.service.impl.CourseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

@Component
public class HttpMethod {
    Logger logger = LoggerFactory.getLogger(HttpMethod.class);
    @Autowired
    private RestTemplate restTemplate;

    public <T extends Serializable> HttpResult send(T t, String url) {
        HttpResult result = new HttpResult();
        try {
            ResponseEntity<HttpResult> responseEntity =
                    restTemplate.postForEntity(url, JSONObject.toJSONString(t), HttpResult.class);
            result = responseEntity.getBody();
            logger.info("HTTP RESULT:{}", result);
            if (result != null) {
                result.setStatus(responseEntity.getStatusCodeValue());
                return result;
            }
        } catch (RestClientException e) {
            result.setStatus(500);
            result.setCode(1);
            result.setMsg("操作失败");
            logger.info("请求失败：{}", e.getStackTrace());
        }
        return result;
    }

}
