package key.android.demo.databindingdemo.utils;

/**
 *@desc
 *@ref:
 *@author : key.guan @ 2017/2/4 21:06
 */
public class MyStringUtils {

    // 首字母大写
    public static String capitalize(final String word) {
        if (word.length() > 1) {
            return String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1);
        }
        return word;
    }
}
