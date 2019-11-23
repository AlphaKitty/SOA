package com.proshine.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 进行httpClient的请求
 */
@Log4j2
public class HttpClientUtil {

    private static final String AUTHENKEY = "Authorization";

    private static final String BASICKEY = "Basic ";

    /**
     * POST
     *
     * @param url     要提交的目标url
     * @param map     参数集合
     * @param charset 编码
     * @return String
     */
    public static String doPost(String url, Map<String, String> map, String charset, Map<String, String> header) {
        // 定义一个可关闭的httpClient的对象
        CloseableHttpClient httpClient = null;

        //List<Cookie> cookies = ((AbstractHttpClient)httpClient).getCookieStore().getCookies();
        // 定义httpPost对象
        HttpPost post;
        try {
            // 1.创建httpClient的默认实例
            httpClient = HttpClients.createDefault();

            // 2.提交post
            post = new HttpPost(url);
            //自定义请求头
            if (header != null) {
                for (Entry<String, String> element : header.entrySet()) {
                    post.setHeader(element.getKey(), element.getValue());
                }
            }
            // 3.设置参数
            List<NameValuePair> list = new ArrayList<>();
            // 4.迭代参数
            for (Entry<String, String> element : map.entrySet()) {
                // 获得参数
                // 通过BasicNameValuePair(key,vlaue)填充参数，并放到集合
                list.add(new BasicNameValuePair(element.getKey(), element.getValue()));
            }
            // 5.编码
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                post.setEntity(entity);
            }
            // 执行
            return getResponse(httpClient, post);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                //关闭资源
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * GET
     *
     * @param url     要提交的目标url
     * @param map     参数集合
     * @param charset 编码
     * @return String
     */
    public static String doGet(String url, Map<String, String> map, String charset, boolean needAuth) {
        // 定义一个可关闭的httpClient的对象
        CloseableHttpClient httpClient = null;
        // 定义httpPost对象
        HttpGet get;
        String str = "";
        try {
            // 1.创建httpClient的默认实例
            httpClient = HttpClients.createDefault();
            // 3.设置参数
            List<NameValuePair> list = new ArrayList<>();
            // 4.迭代参数
            if (map != null) {
                for (Entry<String, String> element : map.entrySet()) {
                    // 获得参数
                    // 通过BasicNameValuePair(key,vlaue)填充参数，并放到集合
                    list.add(new BasicNameValuePair(element.getKey(), element.getValue()));
                }
                // 5.编码
                if (list.size() > 0) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                    str = EntityUtils.toString(entity);
                }
            }
            // 拼接get的参数字符串
            if (!"".equals(str)) {
                get = new HttpGet(url + "?" + str);
            } else {
                get = new HttpGet(url);
            }
            if (needAuth) {
                String authenticationEncoding = Base64.getEncoder().encodeToString("admin:public".getBytes(StandardCharsets.UTF_8));
                get.setHeader(AUTHENKEY, BASICKEY + authenticationEncoding);
            }

            return getResponse(httpClient, get);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭资源
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * GET
     *
     * @param url     要提交的目标url
     * @param map     参数集合
     * @param charset 编码
     * @return String
     */
    public static String doGetForTems(String url, Map<String, String> map, String charset, boolean needAuth, Map<String, String> header) {
        // 定义一个可关闭的httpClient的对象
        CloseableHttpClient httpClient = null;
        // 定义httpPost对象
        HttpGet get;
        String str = "";
        try {
            // 1.创建httpClient的默认实例
            httpClient = HttpClients.createDefault();
            // 3.设置参数
            List<NameValuePair> list = new ArrayList<>();


            // 4.迭代参数
            if (map != null) {
                for (Entry<String, String> element : map.entrySet()) {
                    // 获得参数
                    // 通过BasicNameValuePair(key,vlaue)填充参数，并放到集合
                    list.add(new BasicNameValuePair(element.getKey(), element.getValue()));
                }
                // 5.编码
                if (list.size() > 0) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                    str = EntityUtils.toString(entity);
                }
            }
            // 拼接get的参数字符串
            if (!"".equals(str)) {
                get = new HttpGet(url + "?" + str);
            } else {
                get = new HttpGet(url);
            }
            if (header != null) {
                for (Entry<String, String> element : header.entrySet()) {
                    get.setHeader(element.getKey(), element.getValue());
                }
            }

            if (needAuth) {
                String authenticationEncoding = Base64.getEncoder().encodeToString("admin:public".getBytes(StandardCharsets.UTF_8));
                get.setHeader(AUTHENKEY, BASICKEY + authenticationEncoding);
            }

            return getResponse(httpClient, get);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭资源
                assert httpClient != null;
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * post请求
     *
     * @param url  要提交的目标url
     * @param json json
     * @return JSONObject
     */
    public static JSONObject doPostForJson(String url, JSONObject json, Map<String, String> header, String contentType) {
        JSONObject returnValue = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);


            //设置请求头
            if (header != null) {
                for (Entry<String, String> element : header.entrySet()) {
                    httpPost.setHeader(element.getKey(), element.getValue());
                }
            }
            //第三步：给httpPost设置JSON格式的参数
            if (json != null) {
                StringEntity requestEntity = new StringEntity(json.toJSONString(), "utf-8");
                requestEntity.setContentType(contentType);
                httpPost.setEntity(requestEntity);
            }

            //第四步：发送HttpPost请求，获取返回值
            //调接口获取返回值时，必须用此方法
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            if (execute.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = execute.getEntity();
                // 返回json格式：
                String result = EntityUtils.toString(entity);
                returnValue = JSONObject.parseObject(result);
            }
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
        //第五步：处理返回值
        return returnValue;
    }


    public static String doPostForString(String url, String string, Map<String, String> header, String contentType) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);


            //设置请求头
            if (header != null) {
                for (Entry<String, String> element : header.entrySet()) {
                    httpPost.setHeader(element.getKey(), element.getValue());
                }
            }
            //第三步：给httpPost设置JSON格式的参数
            if (string != null) {
                StringEntity requestEntity = new StringEntity(string, "utf-8");
                requestEntity.setContentType(contentType);
                httpPost.setEntity(requestEntity);
            }

            //第四步：发送HttpPost请求，获取返回值
            //调接口获取返回值时，必须用此方法
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            if (execute.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = execute.getEntity();
                // 返回json格式：
                result = EntityUtils.toString(entity);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
        //第五步：处理返回值
        return result;
    }


    /**
     * 获取返回的数据
     *
     * @param httpClient httpClient
     * @param request    request
     * @return String
     * @throws IOException IOException
     */
    public static String getResponse(CloseableHttpClient httpClient, HttpRequestBase request) throws IOException {
        // 执行
        CloseableHttpResponse response = httpClient.execute(request);

        String result = "";
        try {
            if (response != null) {
                HttpEntity httpEntity = response.getEntity();
                // 如果返回的内容不为空
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        } finally {
            //关闭response
            if (response != null) {
                response.close();
            }
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
