# 样例

````
        jsonObject.put("bodySign", bodySign);

        log.info("{}: 请求的参数：{}", methodReq, jsonObject.toString());
        String url = greateSoftCardUrl + "/do";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(jsonObject, headers);
        JSONObject rs = restTemplate.postForObject(url, entity, JSONObject.class);
        log.info(rs.toJSONString());
        return rs;
        
        
        JSONObject result = restTemplate.postForEntity(carUrl + "/do", erhcCardNoRequestMap, JSONObject.class).getBody();
        return restTemplate.postForEntity(baseUrl + "/main/interfacepregetcode", linkedMap, Map.class).getBody();



        
````

