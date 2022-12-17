package com.rwin.wxsend.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 序列化工具类
 * @author chenli
 * @date 2016-2-3
 * @version 1.0
 */
public class SerializerUtil {
//	public static ObjectMapper mapper = new ObjectMapper();
	
	private final static Logger log = LoggerFactory.getLogger(SerializerUtil.class);
	
	/** 
     * 将对象序列化为JSON字符串 
     *  
     * @param object 
     * @return JSON字符串 
     */  
    public static String serialize(Object object){  
    	if(object == null){
    		return null;
    	}
    	return JSON.toJSONString(object);
    }
    
    /**
     * 将对象序列化为JSON字符串 ，添加序列化过滤器
     * @author wangzy
     * @param object
     * @param filter 
     * @return
     */
    public static String serialize(Object object,SerializeFilter filter, SerializerFeature ... serializerFeatures){
    	if(object == null){
    		return null;
    	}
    	List<SerializerFeature> list = Arrays.asList(serializerFeatures);
		boolean flag = Arrays.asList(serializerFeatures).contains(SerializerFeature.WriteNullStringAsEmpty);
		boolean hasMap = Arrays.asList(serializerFeatures).contains(SerializerFeature.WriteMapNullValue);
    	if(flag && !hasMap){
			SerializerFeature[] serializerFeaturesResult = new SerializerFeature[serializerFeatures.length + 1];
			int i = 0;
			for(SerializerFeature serializerFeature:list){
				serializerFeaturesResult[i] = list.get(i);
				i++;
			}
			serializerFeaturesResult[i] = SerializerFeature.WriteMapNullValue;
			serializerFeatures = serializerFeaturesResult;
		}
    	return JSON.toJSONString(object,filter, serializerFeatures);
    }
    
    /** 
     * 将JSON字符串反序列化为对象 
     * @param json
     * @param clazz
     * @return
     * @author chenliJSONType
     * @data 2016-4-23
     */
	public static <T> T deserializeObj(String json, Class<T> clazz){  
		if(json == null){
    		return null;
    	}
        return JSON.parseObject(json, clazz);  
    }
	
    /** 
     * 将JSON字符串反序列化为JSON对象 
     * @param json
     * @return
     */
	public static JSONObject deserializeJson(String json){  
		if(json == null){
    		return null;
    	}
        return JSON.parseObject(json);  
    }
	
	
    /** 
     * 将JSON字符串反序列化为对象数组
     * @param json
     * @param clazz
     * @return
     * @author chenli
     * @data 2016-4-23
     */
	public static <T> List<T> deserializeArray(String json, Class<T> clazz){  
		if(json == null){
    		return new ArrayList<T>();
    	}
        return JSON.parseArray(json, clazz);  
    }
}
