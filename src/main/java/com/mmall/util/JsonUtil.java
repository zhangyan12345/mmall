package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        //统一一下所有的日期格式
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //反序列化
        //忽略在json中存在，但是在java对象中不存在对应属性的情况，防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static <T> String obj2String(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj:objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error",e);
            return null;
        }
    }

    //格式化过的json字符串
    public static <T> String obj2StringPretty(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj:objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error",e);
            return null;
        }
    }


    public static <T> T string2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class)? (T)str:objectMapper.readValue(str,clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str:objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClass,Class<?> ...elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }




    public static void main(String[] args) {
//
//        User u1 = new User();
//        u1.setId(1);
//        u1.setPhone("121212");
//        u1.setUsername("zhhanghan");
//
//        String str1 = JsonUtil.obj2String(u1);
//        String str2 = JsonUtil.obj2StringPretty(u1);
//        log.info(str1);
//        log.info(str2);
//
//        User user = JsonUtil.string2Obj(str1,User.class);

//        Short s1 = 1;
//        s1 += 1;

//        Aninal an = new Cat();
//        an.eat();
//        na = new dog();
//        an.eat();

    }













}
