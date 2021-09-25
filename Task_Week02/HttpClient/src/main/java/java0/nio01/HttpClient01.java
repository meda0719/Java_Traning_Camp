package java0.nio01;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpClient01
{
    //不带参数的post方法

        public static void main(String[] args) throws Exception {

            doGETParam();

        }

        private static void doPost() throws IOException {
            // 创建Httpclient对象
            CloseableHttpClient httpclient = HttpClients.createDefault();

            // 创建http POST请求
            HttpPost httpPost = new HttpPost("http://localhost:8801");

            CloseableHttpResponse response = null;
            try {
                // 执行请求
                response = httpclient.execute(httpPost);
                // 判断返回状态是否为200
                if (response.getStatusLine().getStatusCode() == 200) {
                    String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                    System.out.println(content);
                }
            } finally {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            }
        }

        private static void doGETParam() throws IOException, URISyntaxException {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            // 定义请求的参数
            URI uri = new URIBuilder("http://localhost:8801/s").setParameter("name", "Meda").build();
            System.out.println(uri);
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = null;
            try {
                // 执行请求
                response = httpclient.execute(httpGet);
                // 判断返回状态是否为200
                if (response.getStatusLine().getStatusCode() == 200) {
                    String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                    System.out.println(content);
                }
            } finally {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            }

        }


}
