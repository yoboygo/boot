package ml.idream;

import ml.idream.config.DreamClassLoaser;
import ml.idream.manage.sys.user.SysUser;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.coyote.InputBuffer;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

import java.io.*;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestDream {

    @Test
    public void testClassLoaser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String path = "ml.idream.manage.sys.user.SysUser";
        DreamClassLoaser dLoader = new DreamClassLoaser();
        Class<?> clazz = dLoader.loadClass(path);
        SysUser sysUser = (SysUser) clazz.newInstance();
        sysUser.setName("abc");
        sysUser.setPassword("qer");
        System.out.println(sysUser.getName() + "-->" + sysUser.getPassword());
        Class<?> clazz2 = dLoader.loadClass(path);
        System.out.println(clazz.equals(clazz2));

    }

    @Test
    public void testAntMatcher(){
        AntPathMatcher managermatcher = new AntPathMatcher();
        String path = "/css/manage/index.css";
        System.out.println(managermatcher.match("/manage/**",path));
    }

    /**
     * @Description 测试登陆
     * @Param []
     * @return void
     * @Author Aimy
     * @Date  
     **/
    @Test
    public void testLogin() throws IOException {
        String baseHost = "http://back.chtfundtest.com";
        String urlLogin = "/user/login/login.action";
        String utlMenu = "/main/menu.action";
        String urlCheckCode = "/common/validateImage.action";

        String paramsStr = "{\"hmac\":\"\",\"params\":{\"userCode\":\"HT123456\",\"password\":\"ht123456\",\"checkCode\":\"{{checkCode}}\"}}";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost();

        //下载验证码
        downLoadCheckCode(client,baseHost + urlCheckCode);

        System.out.print("请输入验证码：");
        BufferedReader inputCode = new BufferedReader(new InputStreamReader(System.in));
        String checkCode = inputCode.readLine();



        post.setURI(URI.create(baseHost + urlLogin));
        HttpEntity params = new StringEntity(paramsStr.replace("{{checkCode}}",checkCode),ContentType.APPLICATION_JSON);
        post.setEntity(params);
        CloseableHttpResponse response = client.execute(post);
        String retDatas = EntityUtils.toString(response.getEntity());
        System.out.println(retDatas);

    }


    private void downLoadCheckCode(CloseableHttpClient client, String url) throws IOException {

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


    @Test
    public void testDownLoadCheckCode() throws IOException {
        String baseHost = "http://back.chtfundtest.com";
        String urlCheckCode = "/common/validateImage.action";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost();

        for(int i = 0; i < 10 ; ++i){
            downLoadCheckCode(client,baseHost + urlCheckCode);
        }
    }

    /**
     * @Description 日期转换
     * @Param []
     * @return void
     * @Author Aimy
     * @Date
     **/
    @Test
    public void testJson() throws IOException{

        Map<String,Object> datas = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        datas.put("source",calendar.getTimeInMillis());
        datas.put("date",calendar.getTime());
        datas.put("long",calendar.getTimeInMillis());

        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class,new MyDateProcessor());
        config.registerJsonValueProcessor("long",new MyDateProcessor());
        JSONObject json = JSONObject.fromObject(datas,config);

        System.out.println(json.toString());


    }
}
