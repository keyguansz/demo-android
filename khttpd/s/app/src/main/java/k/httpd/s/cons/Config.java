package k.httpd.s.cons;


public final class Config {
	public final static int sharedAdd = 10;//	
	public final static int smsActiveTime = 60;//短信存活时间，秒
	public static final String fileAcKey = "K4234Tdk87C902";	//文件访问（上传或者下载的密钥）
	public static String upload;
	public static String video;
	public static String image;
	public final static long max_size_image=10*1024*1024;
	public final static long Day =1000*60*60*24;
	//局域网
	public static final int port = 8081;
	private static boolean isAp = true;
	private static final String IP = isAp ? "192.168.1.3":"127.0.0.1";
	public static final String SERVER_IP = "http://"+IP+":"+port+"/";
	public final static String[] FileDir={"/"};
	public final static int pageSize = 100;

}
