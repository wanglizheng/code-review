package com.inspur.ihealth.utils.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {
	public static String sortAsciiJson(String json) {
		String result = "";
		try {
			JSONObject jsonObject = JSON.parseObject(json);
			ArrayList<String> nameList = new ArrayList<>();
			Set<String> keys = jsonObject.keySet();
			for(String key : keys){
				nameList.add(key);
			}
			Collections.sort(nameList);
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			for (int i = 0; i < nameList.size(); i++) {
				String name = nameList.get(i);
				String value = jsonObject.getString(name);
				if (i != 0)
					sb.append(",");
				if (value.startsWith("{") || value.startsWith("[")) {
					sb.append(String.format("\"%s\":%s", name, value));
				} else {
					sb.append(String.format("\"%s\":\"%s\"", name, value));
				}
			}
			sb.append("}");
			result = sb.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
		
		return result;
	}
}
