package com.vr.active.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

public class HttpRequest 
{
	/**
	 * 日志对象。
	 */
	private static Logger logger = Logger.getLogger(HttpRequest.class);
	
	/**
	 * 默认HTTP请求客户端对象。
	 */
	private CloseableHttpClient  _httpclient;

	/**
	 * 用户自定义消息头。
	 */
	private Map<String, String> _headers;

	/**
	 * 使用默认客户端对象。
	 */
	public HttpRequest() 
	{
		// 1. 创建HttpClient对象。
		_httpclient =  HttpClients.createDefault();
	}

	/**
	 * 调用者指定客户端对象。
	 * 
	 * @param
	 */
	public HttpRequest(Map<String, String> headers) 
	{
		// 1. 创建HttpClient对象。
		_httpclient = HttpClients.createDefault();
		this._headers = headers;
		logger.info("create _httpclient ...");
	}

	/**
	 * HTTP POST请求。
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws InterruptedException
	 */
	public String post(String url, Map<String, Object> params)
	{
		// 2. 创建请求方法的实例，并指定请求URL，添加请求参数。
		HttpPost post = postParams(url, params);
		
		logger.info("create httppost : " + url);
		return invoke(post);
	}
	
	/**
	 * HTTP GET请求。
	 * 
	 * @param url
	 * @return
	 */
	public String get(String url) 
	{
		HttpGet get = new HttpGet(url);
		logger.info("create httpget : " + url);
		return invoke(get);
	}
	
	/**
	 * 发送请求，处理响应。
	 * @param request
	 * @return
	 */
	private String invoke(HttpUriRequest request)
	{
		String result = "";
		if (this._headers != null) 
		{
			//
			addHeaders(request);
			logger.info("addHeaders to http ...");
		}
		HttpResponse response = null;
		try
		{
			response = _httpclient.execute(request);
			result = EntityUtils.toString(response.getEntity());
			logger.info("execute http result... ;request:"+request.getParams()+" result = " + result);
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("execute reauest error ", e);
			return null;
		}
		finally
		{
			// 4. 无论执行方法是否成功，都必须释放连接。
			request.abort();
			logger.info("release http ...");
		}
	}
	
	private HttpPost postParams(String url, Map<String, Object> params) 
	{
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params!=null&&!params.isEmpty()) {
			// 组装参数。
			Set<String> keySet = params.keySet();
			for (String key : keySet) 
			{
				nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
			try 
			{
				logger.info("set utf-8 form entity to httppost ...");
				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return httpost;
	}
	
	/**
	 * 增加消息头。
	 * 
	 * @param httpost
	 */
	private void addHeaders(HttpUriRequest httpost)
	{
		Iterator<Entry<String, String>> it = this._headers.entrySet()
				.iterator();
		Entry<String, String> entry = null;
		String name = null;
		String value = null;

		while (it.hasNext())
		{
			entry = it.next();
			name = entry.getKey();
			value = entry.getValue();

			httpost.addHeader(name, value);
		}
	}
	
	/**
	 * 关闭HTTP客户端链接。
	 */
	public void shutdown()
	{
		_httpclient.getConnectionManager().shutdown();
		logger.info("shutdown _httpclient ...");
	}
	
	public static void main(String[] args) {
		
		String url="http://blog.sina.com.cn/s/blog_6b275753010161t3.html";
		HttpRequest httpClient = new HttpRequest();
		JSONObject json = new JSONObject();
		json.put("data", "");
		System.out.println(httpClient.post(url, json));
	}
}