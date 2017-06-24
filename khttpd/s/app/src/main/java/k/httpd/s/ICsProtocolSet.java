package k.httpd.s;

/**
 * Created by key on 2017/6/17.
 */

public interface ICsProtocolSet {
    interface getFileList {
        String DO = "getFileList";
        String ext = "ext";//文件扩展名大类，image是图像，video是视频
        String order ="order";//排序，date为时间排序，size按照文件大小排序
        String pageId = "pageId";//分页加载文件
    }
    interface Download {
        String DO = "Download";
        String path = "path";//文件名
        String level = "level";//nail为缩列图，raw为原始图
    }
    interface ExtType {
        String all = "";//""或者null均可
        String image = "image";
        String video = "video";
        String both = "both";//默认为这个
    }
    interface LevelType {
        String nail = "nail";//缩略图/视频预览图
        String raw = "raw";//原始文件
    }
    interface OrderType {
        String no = "no";//不分组
        String date = "date";//时间排序
        String size = "size";//原始文件
    }
}
