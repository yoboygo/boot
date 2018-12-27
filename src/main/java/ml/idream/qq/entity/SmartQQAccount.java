package ml.idream.qq.entity;


import ml.idream.qq.common.QRCodeStatus;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

import java.io.Serializable;

/**
 * @Description 账号基本信息
 * @Author Aimy
 * @Date 2018/12/12 15:34
 **/
public class SmartQQAccount implements Serializable {

    /** 已经重试的次数*/
    private int tryTimes = 0;

    /** QQ号码*/
    private String account;

    /** QQ用户昵称*/
    private String nickName;

    /** 加密后的hash,固定值*/
    private String hash;

    /** vfwebqq*/
    private String vfwebqq;

    private String psessionid;

    /** 请求上下文*/
    private HttpClientContext httpClientContext = HttpClientContext.create();
    {
        httpClientContext.setCookieStore(new BasicCookieStore());
    }

    /** 当前账号是否已经登录*/
    private QRCodeStatus status = QRCodeStatus.NONE;

    /** 重试次数+1*/
    public void tryTimeInc(){
        ++tryTimes;
    }


    public int getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(int tryTimes) {
        this.tryTimes = tryTimes;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public HttpClientContext getHttpClientContext() {
        return httpClientContext;
    }

    public void setHttpClientContext(HttpClientContext httpClientContext) {
        this.httpClientContext = httpClientContext;
    }

    public QRCodeStatus getStatus() {
        return status;
    }

    public void setStatus(QRCodeStatus status) {
        this.status = status;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getVfwebqq() {
        return vfwebqq;
    }

    public void setVfwebqq(String vfwebqq) {
        this.vfwebqq = vfwebqq;
    }

    public String getPsessionid() {
        return psessionid;
    }

    public void setPsessionid(String psessionid) {
        this.psessionid = psessionid;
    }

    @Override
    public String toString() {

        return "SmartQQAccount{" +
                "tryTimes=" + tryTimes +
                ", account='" + account + '\'' +
                ", nickName='" + nickName + '\'' +
                ", hash='" + hash + '\'' +
                ", vfwebqq='" + vfwebqq + '\'' +
                ", psessionid='" + psessionid + '\'' +
                ", status=" + status.getValue() + "-" + status.getLabel() +
                '}';
    }
}
