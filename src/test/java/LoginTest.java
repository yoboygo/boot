import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.util.Calendar;

/**
 * @Description TODO
 * @Author Aimy
 * @Date 2018/9/14 17:32
 **/
public class LoginTest {

    public static void main(String[] args) throws IOException {
        String baseHost = "http://back.chtfundtest.com";
        String urlLogin = "/user/login/login.action";
        String urlMenu = "/main/menu.action";
        String urlCheckCode = "/common/validateImage.action";

        String paramsStr = "{\"hmac\":\"\",\"params\":{\"userCode\":\"HT123456\",\"password\":\"ht123456\",\"checkCode\":\"{{checkCode}}\"}}";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost();


        post.setURI(URI.create(baseHost + urlMenu));
        CloseableHttpResponse response = client.execute(post);
        System.out.println("登陆前请求菜单：");
        System.out.println(EntityUtils.toString(response.getEntity()));

        //下载验证码
        downLoadCheckCode(client,baseHost + urlCheckCode);

        System.out.print("请输入验证码：");
        BufferedReader inputCode = new BufferedReader(new InputStreamReader(System.in));
        String checkCode = inputCode.readLine();

        post.setURI(URI.create(baseHost + urlLogin));
        HttpEntity params = new StringEntity(paramsStr.replace("{{checkCode}}",checkCode), ContentType.APPLICATION_JSON);
        post.setEntity(params);
        response = client.execute(post);
        JSONObject retDatas = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
        String retCode = retDatas.getString("code");
        if("CS0000".equals(retCode)){//登陆成功
            System.out.println("登陆成功！");
            System.out.println("获取菜单:");
            post.setURI(URI.create(baseHost + urlMenu));
            response = client.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }

    private static void downLoadCheckCode(CloseableHttpClient client, String url) throws IOException {

        //下载验证码，写入本地目录
        HttpGet get = new HttpGet();
        get.setURI(URI.create(url));
        CloseableHttpResponse response = client.execute(get);
        try(InputStream input = response.getEntity().getContent();){
            File code = new File("D:\\code\\" + Calendar.getInstance().getTimeInMillis() + ".jpg");
            try(FileOutputStream checkCodeOut = new FileOutputStream(code);){
                byte[] bytes = new byte[1024];
                int l = 0;
                while((l = input.read(bytes)) != -1){
                    checkCodeOut.write(bytes,0,l);
                }
            }
        }
    }

}
