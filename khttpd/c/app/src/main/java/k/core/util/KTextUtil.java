/**
 * @file_name    : KTextUtil.java
 * @package_name : com.util
 * @Data         : 2013-10-25 下午4:44:16
 * @author       : zhangliutao
 * @Description  : TODO
 *
 * Copyright (c) 2013, DJI All Rights Reserved.
 */

package k.core.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static k.httpd.c.cons.ICsProtocolSet.OrderType.date;


public class KTextUtil
{
    private static final DateFormat mMdFormat = new SimpleDateFormat("MM.dd");
    private static final DateFormat mYmdFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static final long DAY = 60*60*24;//一天有多少s
    private static final long YAER = 365 * 60*60*24;//一年有多少s
    private static final long S_MS = 1000;//一s有多少ms
    public static String formatADate(long longMs) {
        Date date = new Date();
        long ds = (date.getTime() - longMs)/ S_MS;
        long dd = ds / DAY ;
        if (dd == 0){
            return "today";
        }
        if ( dd  == 1){
            return "yestoday";
        }
        long dy = ds / YAER ;
        date.setTime(longMs);
        if (dy == 0){
            return mMdFormat.format(date);
        }
        return mYmdFormat.format(date);
    }
}
