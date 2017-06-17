package k.httpd.c.model;

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
}
