package ml.idream;

import com.sun.javafx.binding.StringFormatter;
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
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.AntPathMatcher;

import java.io.*;
import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Test
    public void testZkService() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("172.16.163.51:2181",20000,null);
        List<String> dubboRoot = zooKeeper.getChildren("/dubbo",false);
        for(String serviceName : dubboRoot){
            if(serviceName.indexOf("PofCombinationService") != -1){
                System.err.println("dubbo服务：" + serviceName);
            }else{
                System.out.println("dubbo服务：" + serviceName);
            }
        }

        Stat stat = zooKeeper.exists("/dubbo/com.chtwm.pof.manage.api.external.PofCombinationService",null);
        System.out.println(stat);
    }

    @Test
    public void testDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfw = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String abc = "15:28:05";
        Date date = sdf.parse(abc);
        date.setTime(1540857693000L);
        System.out.println(sdf.format(date.getTime()));
        System.out.println(sdfw.format(date.getTime()));
    }

    /**
     * @Description 10瓶水，4个空瓶换一个，2个瓶盖换一个。最后能喝多少瓶。
     * @Param []
     * @return void
     * @Author Aimy
     * @Date  
     **/
    @Test
    public void calculateBottle(){
        int total = 10;
        System.out.println("总共喝掉了：" + caculate(10,0,0) + "瓶水！");
    }

    public int caculate(int bottal, int leftpz, int leftgz){

        int pz = (bottal + leftpz);
        int gz = (bottal + leftgz);

        int leftb =  pz / 4 + gz / 2;

        leftpz = pz % 4;
        leftgz = gz % 2;

        System.out.println(String.format("本轮共喝掉%d瓶水，用%d瓶子和%d瓶盖换了%d瓶水，剩余%d瓶子，%d瓶盖！",bottal,pz,gz,leftb,leftpz,leftgz));
        if(leftb + leftpz < 4 && leftb + leftgz < 2){
            return bottal;
        }

        return bottal + caculate(leftb,leftpz,leftgz);
    }

    @Test
    public void testBigDecimal(){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
        System.out.println(decimalFormat.format(0.01));
    }

    @Test
    public void testBeanCopy(){
        Source s = new Source();
        s.setNo(100);
        s.setRadio(new BigDecimal(1.0));
        s.setStartTime(new Date());

        Target t = new Target();

        BeanUtils.copyProperties(s,t);
        System.out.println(t.getNo() + t.getRadio() + t.getStartTime());
    }
}
class Source{
    private Date startTime;
    private BigDecimal radio;
    private Integer no;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getRadio() {
        return radio;
    }

    public void setRadio(BigDecimal radio) {
        this.radio = radio;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}
class Target{
    private String startTime;
    private String radio;
    private Integer no;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}