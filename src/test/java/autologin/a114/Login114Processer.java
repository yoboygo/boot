package autologin.a114;

import autologin.base.AbstractLoginProcesser;
import autologin.base.Hospital;
import autologin.base.ILoginConfig;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description 114处理
 * @Author Aimy
 * @Date 2018/9/26 15:52
 **/
public class Login114Processer extends AbstractLoginProcesser {

    private static final Logger logger = LoggerFactory.getLogger(Login114Processer.class);

    private List<Hospital> hostpitalCodes = new ArrayList<>();

    public Login114Processer(ILoginConfig loginConfig) {
        super(loginConfig);
    }

    public boolean login() throws Exception {
        if(super.login(loginConfig)){
            return isLogin();
        }
        return false;
    }
    /**
     * @Description 检查是否登陆
     * @Param []
     * @return boolean
     * @Author Aimy
     * @Date
     **/
    public boolean isLogin() throws Exception {
        HttpPost post = new HttpPost();
        post.setURI(new URI(loginConfig.getIsLoginUrl()));
        CloseableHttpResponse response = this.getHttpClient().execute(post);
        int responseCode = response.getStatusLine().getStatusCode();
        if(responseCode >= 200 && responseCode < 300){
            String resDatas = EntityUtils.toString(response.getEntity());
            JSONObject datas = JSONObject.fromObject(resDatas);
            logger.info("检查是否登陆返回数据：" + resDatas);
            if(datas.containsKey("msg"))
                return datas.getString("msg").equalsIgnoreCase("OK");
        }
        return false;
    }

    @Override
    public List<Hospital> getHospitalList(boolean init) {
        //医院列表为空时初始化
        if(hostpitalCodes.isEmpty()){
            initHospital();//初始化医院列表
        //
        }else if(init){
            initHospital();
        }
        return hostpitalCodes;
    }

    /**
     * @Description 初始化医院列表
     * @Param []
     * @return void
     * @Author Aimy
     * @Date
     **/
    private void initHospital() {
        // TODO
    }

    @Override
    public CloseableHttpClient getHttpClient() {
        return client;
    }

}
