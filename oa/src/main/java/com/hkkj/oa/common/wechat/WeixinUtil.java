package com.hkkj.oa.common.wechat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hkkj.oa.common.utils.MD5;

/**
 * 公众平台通用接口工具类
 */

public class WeixinUtil {

	/**
	 * 
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 * 
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSON.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
			//LogUtils.error(WeixinUtil.class, "Weixin server connection timed out.");
		} catch (Exception e) {
			e.printStackTrace();
			//LogUtils.error(WeixinUtil.class, "https request error:" + e.getMessage());
		}
		//LogUtils.info(WeixinUtil.class, "返回结果:" + jsonObject);
		return jsonObject;

	}

	/**
	 * 获取访问令牌
	 * 
	 * @param appId
	 * @param appSecret
	 * @return {"access_token":"ACCESS_TOKEN","expires_in":7200}
	 *         {"errcode":40013,"errmsg":"invalid appid"}
	 */
	public static JSONObject getAccess_Token(String appId, String appSecret, String code) {
		String cacheName = MD5.MD("access_token_" + appId + appSecret + code);
		// 如果从内存中取到直接返回内存中的数据
		Object obj = WeixinDataCache.getInstance().getData(cacheName);
		if (null != obj) {
			return (JSONObject) obj;
		} else {
			//https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId
					+ "&secret=" + appSecret+"&code=" + code + "&grant_type=authorization_code";
			JSONObject jo = httpRequest(url, "GET", null);
			// 获取数据成功以后放入内存
			if (null == jo.get("errcode")) {
				WeixinDataCache.getInstance().setData(cacheName, jo, 7150);
			}
			return jo;
		}
	}

	/**
	 * 提交菜单
	 * 
	 * @param queryString
	 * @return {"errcode":0,"errmsg":"ok"} {"errcode":40018,"errmsg":
	 *         "invalid button name size"}
	 */
	public static JSONObject submitMenu(String access_token, String queryString) {
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
		JSONObject jo = httpRequest(url + access_token, "POST", queryString);
		//LogUtils.info(WeixinUtil.class, jo.toString());
		return jo;
	}

	public static JSONObject deleteMenu(String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";
		JSONObject jo = httpRequest(url + access_token, "POST", null);
		return jo;
	}

	// 给用户发送客服信息
	public static JSONObject sendMessage(String access_token, String queryString) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
		JSONObject jo = httpRequest(url + access_token, "POST", queryString);
		return jo;
	}

	// 组装文本类型的客服信息
	public static JSONObject getMsg(String openid, String content) throws JSONException {
		JSONObject ob = new JSONObject();
		ob.put("content", content);
		JSONObject jo = new JSONObject();
		jo.put("touser", openid);
		jo.put("msgtype", "text");
		jo.put("text", ob);
		return jo;
	}

	/**
	 * 签名
	 * 
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		//LogUtils.info(WeixinUtil.class, string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取调用微信JS接口的临时票据jsapi_ticket
	 * 
	 * @param access_token
	 * @return {"errcode":0,"errmsg":"ok","ticket":"...","expires_in":7200}
	 */
	public static JSONObject getJsapiTicket(String access_token) {
		String cacheName = MD5.MD("jsapi_ticket_" + access_token);
		// 如果从内存中取到直接返回内存中的数据
		Object obj = WeixinDataCache.getInstance().getData(cacheName);
		if (null != obj) {
			return (JSONObject) obj;
		} else {
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
			JSONObject jo = httpRequest(String.format(url, access_token), "GET", null);
			// 获取数据成功以后放入内存
			if (jo.getInteger("errcode").intValue() == 0) {
				WeixinDataCache.getInstance().setData(cacheName, jo, 7150);
			}
			return jo;
		}
	}
}