/**   
 *  文件说明
 *
 * @Title: VLC_JsonUtil.java 
 * @Package com.dji.frame.util 
 * Description : 处理json原始数据
 * @author ZhangLiutao  
 * @date 2013-5-14 下午4:03:59 
 * @version V1.0   
 */

package k.core.util;

import java.lang.reflect.Type;
import java.util.List;

import dji.thirdparty.gson.Gson;
import dji.thirdparty.gson.GsonBuilder;
import dji.thirdparty.gson.JsonElement;
import dji.thirdparty.gson.JsonParser;
import dji.thirdparty.gson.reflect.TypeToken;


/**
 * <h3>Description : 处理json原始数据的一些便捷方法。</h3>
 * 
 * @author : tony.zhang
 * @date : 2013-12-13 下午1:28:15
 * @version : V1.0
 */

public class V_JsonUtil {

    public static Gson gson;

    public synchronized static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * Description : 解析单一模式的json字符串为目标模型
     * 
     * @author : tony.zhang
     * @date : 2013-12-12 下午3:45:46
     * @param s json字符串
     * @param classOfT
     * @return T
     */
    @Deprecated
    public static <T> T getOne(String s, Class<T> classOfT) {
        return toBean(s, classOfT);
    }

    /**
     * Description : 解析单一模式的json字符串为目标模型
     * 
     * @author : tony.zhang
     * @date : 2013-12-12 下午3:45:46
     * @param s json字符串
     * @param classOfT
     * @return T
     */
    public static <T> T toBean(String s, Class<T> classOfT) {
        Gson gson = getGson();
        T obj = null;
        try {
            obj = gson.fromJson(s, classOfT);
        } catch (Exception e) {

        }
        return obj;
    }

    /**
     * Description : bean to json
     * 
     * @author : tony.zhang
     * @date : 2014-4-10 下午3:33:38
     * @param obj
     * @return
     */
    public static <T> String toJson(T obj) {
        Gson gson = getGson();
        return gson.toJson(obj);
    }

    /**
     * Description : beans to json
     * 
     * @author : tony.zhang
     * @date : 2014-8-29 下午2:46:25
     * @param objs
     * @return
     */
    public static <T> String toJson(List<T> objs) {
        Gson gson = getGson();
        return gson.toJson(objs);
    }

    /**
     * Description : 解析列表类型json字符串
     * 
     * @author : tony.zhang
     * @date : 2013-12-12 下午3:46:58
     * @param s json字符串
     * @return List<T>
     */
    public static <T> List<T> getList(String s, TypeToken<List<T>> token) {
        Type listType = token.getType();
        Gson gson = getGson();
        List<T> objs = gson.fromJson(s, listType);
        return objs;
    }

    /**
     * Description : 把混乱的json字符串整理成缩进的json字符串
     * 
     * @author : tony.zhang
     * @date : 2014-4-10 下午8:23:35
     * @param uglyJsonStr
     * @return
     */
    public static String jsonFormatter(String uglyJsonStr) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJsonStr);
        return gson.toJson(je);
    }
}
