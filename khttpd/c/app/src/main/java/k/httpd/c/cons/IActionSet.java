package k.httpd.c.cons;

/**
 * Created by key on 2017/6/17.
 */

public interface IActionSet {
    public interface getFileList {
        String DO = "getFileList";
        String ext = "ext";//文件扩展名大类，image是图像，video是视频
        String pageId = "pageId";//分页加载文件
    }
    public interface Download {
        String DO = "Download";
        String path = "path";//文件名
        String level = "level";//nail为缩列图，raw为原始图
    }
}
