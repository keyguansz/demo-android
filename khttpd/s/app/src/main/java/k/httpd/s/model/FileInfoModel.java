package k.httpd.s.model;

import java.io.File;
import java.util.Comparator;

/**
 * Created by key on 2017/6/17.
 */

public final class FileInfoModel {
    public String path;
    public long len;
    public long mtime;//修改时间
    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder(30);
        sb.append("{\"path\":");
        sb.append(path);
        sb.append(",\"len\":\"");
        sb.append(len);
        sb.append(",\"mtime\":\"");
        sb.append(mtime);
        sb.append("\"}");
        return sb.toString();
    }
    public static class FileComparator implements Comparator<File> {
        public int compare(File bean1, File bean2) {
            long dx = bean2.lastModified() - bean1.lastModified();
            if ( dx > 0 )return 1;
            if (dx == 0) return 0;
            return -1;
        }
    }
}
