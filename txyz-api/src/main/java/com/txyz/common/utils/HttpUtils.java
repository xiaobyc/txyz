package com.txyz.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class HttpUtils {
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static int socketTimeout =30000;
	private static int socketConnectTimeout =60000;

	public static JSONObject doGet(String url) throws IOException {
		return JSON.parseObject(doGetString(url));
    }
	
	public static String doGetString(String url) throws IOException {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().
				setSocketTimeout(socketTimeout).setConnectTimeout(socketConnectTimeout).build();
		httpGet.setConfig(requestConfig);

		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());
			if (response.getStatusLine().getStatusCode() != 200) {
				return responseStatusNotOk(response, url).toJSONString();
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "utf-8");
			}
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("IOException",e);
				}
			}
		}
		return unknownExceptionResult().toJSONString();
	}
	
	/**
	 * 调用带有header参数的get请求
	 * @param url
	 * @param headerMap
	 */
	public static JSONObject doGetWithHeader(String url,Map<String,String> headerMap) throws IOException {
		return JSON.parseObject(doGetString(url,headerMap));
    }
	
	public static String doGetString(String url,Map<String,String>headerMap) throws IOException {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().
				setSocketTimeout(socketTimeout).setConnectTimeout(socketConnectTimeout).build();
		httpGet.setConfig(requestConfig);
		if(headerMap!=null&&!headerMap.isEmpty()){
			Set<String> keySet=headerMap.keySet();
			Iterator<String> iter=keySet.iterator();
			while(iter.hasNext()){
				String key=iter.next();
				String value=headerMap.get(key);
				httpGet.setHeader(key, value);				
			}
		}

		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());
			if (response.getStatusLine().getStatusCode() != 200) {
				return responseStatusNotOk(response, url).toJSONString();
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "utf-8");
			}
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("IOException",e);
				}
			}
		}
		return unknownExceptionResult().toJSONString();
	}
	
	public static void main(String[] args) throws Exception {
		String url="http://116.211.87.109:22009/ruif/apps/0/-1/-1/-1/-1/-1/888888?start=1&end=3";
		Map<String,String> headerMap=new HashMap<String,String>();
		headerMap.put("platformCode", "888888");
		System.out.println(doGetString(url,headerMap));		
	}


	public static JSONObject doPost(String url, Object data) throws IOException {
		JSONObject jsonStr = JSON.parseObject(doPostString(url, data));
		return jsonStr;
    }
	public static JSONObject doPostToJSON(String url, Object data) throws IOException {
		JSONObject jsonStr = JSON.parseObject(doPostString(url, data));
		return jsonStr;
    }	
	public static String doPostString(String url, Object data) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().
				setSocketTimeout(socketTimeout).setConnectTimeout(socketConnectTimeout).build();
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/json");
		try {
			StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
			httpPost.setEntity(requestEntity);
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {
				return responseStatusNotOk(response, url).toJSONString();
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {				
				return EntityUtils.toString(entity, "utf-8");
			}
		} finally {
			if (response != null) try {
				response.close();
			} catch (IOException e) {
				logger.error("IOException",e);
			}
		}
		return unknownExceptionResult().toJSONString();
	}

	public static String doPostMessage(String url, Map<String,String> map) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;

		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().
				setSocketTimeout(socketTimeout).setConnectTimeout(socketConnectTimeout).build();
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8 ");
		try {
			List<NameValuePair> pairs = new ArrayList<>();
			for(Map.Entry<String,String> entry : map.entrySet()){
				pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {
				return responseStatusNotOk(response, url).toJSONString();
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "utf-8");
			}
		} finally {
			if (response != null) try {
				response.close();
			} catch (IOException e) {
				logger.error("IOException",e);
			}
		}
		return unknownExceptionResult().toJSONString();
	}
	/**
	 * 模拟form表单提交
	 */
	public static String doFormString(String url, JSONObject data) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().
				setSocketTimeout(socketTimeout).setConnectTimeout(socketConnectTimeout).build();
		httpPost.setConfig(requestConfig);
		List<NameValuePair> formParams = new ArrayList<>();
		for(String key: data.keySet()){
			formParams.add(new BasicNameValuePair(key, data.getString(key)));
		}
		HttpEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
		httpPost.setEntity(requestEntity);
		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());
			
			if (response.getStatusLine().getStatusCode() != 200) {
				return responseStatusNotOk(response, url).toJSONString();
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "utf-8");
			}
		} finally {
			if (response != null) try {
				response.close();
			} catch (IOException e) {
				logger.error("IOException",e);
			}
		}
		return unknownExceptionResult().toJSONString();
	}

	public static JSONObject uploadMedia(String url, File file) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(socketConnectTimeout).build();
        httpPost.setConfig(requestConfig);

        HttpEntity requestEntity = MultipartEntityBuilder.create().addPart("media",
        		new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
        httpPost.setEntity(requestEntity);

        try {
            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {
                return responseStatusNotOk(response, url);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                return JSON.parseObject(resultStr);
            }
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
            	logger.error("IOException",e);
            }
        }
        return unknownExceptionResult();
    }
	
	public static JSONObject downloadMedia(String url, String fileDir) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(socketConnectTimeout).build();
        httpGet.setConfig(requestConfig);

        try {
            HttpContext localContext = new BasicHttpContext();

            response = httpClient.execute(httpGet, localContext);

            RedirectLocations locations = (RedirectLocations) localContext.getAttribute(HttpClientContext.REDIRECT_LOCATIONS);
            if (locations != null) {
                URI downloadUrl = locations.getAll().get(0);
                String filename = downloadUrl.toURL().getFile();
                File downloadFile = new File(fileDir + File.separator + filename);
                FileUtils.writeByteArrayToFile(downloadFile, EntityUtils.toByteArray(response.getEntity()));
                JSONObject obj = new JSONObject();
                obj.put("downloadFilePath", downloadFile.getAbsolutePath());
                obj.put("httpcode", response.getStatusLine().getStatusCode());
                return obj;
            } else {
                if (response.getStatusLine().getStatusCode() != 200) {
                    return responseStatusNotOk(response, url);
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String resultStr = EntityUtils.toString(entity, "utf-8");
                    return JSON.parseObject(resultStr);
                }
            }
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
            	logger.error("IOException",e);
            }
        }
        return unknownExceptionResult();
    }
	
	public static JSONObject uploadFile(String url, Map<String,String> params, byte[] fileContent, String fileField, String fileName){
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(socketConnectTimeout).build();
		httpPost.setConfig(requestConfig);

		Iterator<String> keys = params.keySet().iterator();

		MultipartEntityBuilder meb = MultipartEntityBuilder.create();
		meb.addBinaryBody(fileField, fileContent, ContentType.APPLICATION_OCTET_STREAM, fileName);
		while(keys.hasNext()){
			String key = keys.next();
			meb.addTextBody(key, params.get(key));
		}
		httpPost.setEntity(meb.build());
		try {
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != 200) {
				return responseStatusNotOk(response, url);
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				return JSON.parseObject(resultStr);
			}
		}catch(Exception e){
			logger.error("IOException",e);
		}finally {
			if (response != null) try {
				response.close();
			} catch (IOException e) {
				logger.error("IOException",e);
			}
		}
		return unknownExceptionResult();
	}

	private static JSONObject responseStatusNotOk(CloseableHttpResponse response, String url){
		JSONObject result = new JSONObject();
		result.put("errcode", "999999");
		result.put("errmsg", "request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
		return result;
	}

	private static JSONObject unknownExceptionResult(){
		JSONObject result = new JSONObject();
		result.put("errcode", "888888");
		result.put("errmsg", "未知异常导致无返回结果");
		return result;
	}
}
