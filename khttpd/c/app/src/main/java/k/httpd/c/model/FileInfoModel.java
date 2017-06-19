package k.httpd.c.model;

import java.io.File;
import java.util.Comparator;

/**
 * Created by key on 2017/6/17.
 */

public final class FileInfoModel {
    public String path;
    public long len;
    public long mtime;//修改时间
    public String mtimeStr;//修改时间
    public long ctime;//创建时间
    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder(30);
        sb.append("{\"path\":");
        sb.append(path);
        sb.append(",\"len\":\"");
        sb.append(len);
        sb.append(",\"mtime\":\"");
        sb.append(mtime);
        sb.append(",\"mtimeStr\":\"");
        sb.append(mtimeStr);
        sb.append("\"}");
        return sb.toString();
    }
    /**
     *@desc    降序排序，排在第一位的是图片
     *@ref:
     *@author : key.guan @ 2017/6/19 11:42
     */
    public static class FileComparator implements Comparator<File> {
        public int compare(File bean1, File bean2) {
            long dx = bean2.lastModified() - bean1.lastModified();
            if ( dx > 0 )return 1;
            if (dx == 0) return 0;
            return -1;
        }
    }
}
