package k.httpd.s.model;

import java.io.Serializable;

/**
 * @desc:
 * @ref:
 * @author: key.guan @ 2017/6/17
 */
public final class RetModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5289501445232901209L;
	private int ec = 0;
	private String msg = "操作成功";
	public RetModel() {
	}
	public RetModel(int ec, String msg) {
		this.ec = ec;
		this.msg = msg;
	}
	/**错误码
	 * 0 无错
	 * >=1 有错
	 * @return the ec
	 */
	public void set(int ec, String msg){
		this.ec = ec;
		this.msg = msg;
	}
	public int getEc() {
		return ec;
	}
	/**
	 * @param ec the ec to set
	 */
	public void setEc(int ec) {
		this.ec = ec;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/* eg:{"rt":2,"msg":"手机号码已经注册"}
	 * @see java.lang.Object#toString() 作用类似于Gson,但是在msg字段还含有子json段时候，会报错
	 */
	@Deprecated
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder(30);
		sb.append("{\"rt\":");
		sb.append(ec);
		sb.append(",\"msg\":\"");
		sb.append(msg);
		sb.append("\"}");
		return sb.toString();
	}
}
