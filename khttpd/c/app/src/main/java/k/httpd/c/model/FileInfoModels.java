package k.httpd.c.model;

import java.util.List;

/**
 * Created by key on 2017/6/17.
 */

public final class FileInfoModels {
    public long mtime;//修改时间，单位为天，t,y
    public String mtimeStr;//title
    public List<FileInfoModel> datas;

    @Override
    public String toString() {
        int size = datas != null?0:datas.size();
        StringBuilder sb= new StringBuilder(30);
        sb.append(",\"mtime\":\"");
        sb.append(mtime);
        sb.append(",\"mtimeStr\":\"");
        sb.append(mtimeStr);
        sb.append(",\"size\":\"");
        sb.append(size);
        sb.append("\"}");
        return sb.toString();
    }
}
