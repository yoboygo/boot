package ml.idream.distribute.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 工具类
 * @author songjianlong
 *
 */
public class CodeUtils {

	/**
	 * 发起post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String doPost(String url,Map<String,String> params) throws ClientProtocolException, IOException{
		try(CloseableHttpClient client = HttpClientBuilder.create().build();){
			HttpPost methodPost = new HttpPost(url);
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			params.forEach((key,value) ->{
				NameValuePair nvp = new BasicNameValuePair(key,value);
				paramsList.add(nvp);
			});
			if(paramsList.size() > 0){
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsList,Charset.forName("UTF-8"));  
				methodPost.setEntity(entity);
				HttpResponse response = client.execute(methodPost);
				return EntityUtils.toString(response.getEntity());
			}
		}
		return "";
	}
}
