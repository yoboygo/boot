package ml.idream.qq.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 待登陆的账号列表
 * @Author SongJianlong
 * @Date 2018/12/12 15:33
 **/
public class SmartQQAccountList {

    private static ConcurrentHashMap<String,SmartQQAccount> smartQQAccountList;

    private SmartQQAccountList() {
    }

    public static ConcurrentHashMap<String,SmartQQAccount> getInstance(){
        if(smartQQAccountList == null){
            synchronized (smartQQAccountList){
                if(smartQQAccountList == null){
                    smartQQAccountList = new ConcurrentHashMap();
                }
            }
        }
        return smartQQAccountList;
    }

    /**将账号新信息加入循环列表*/
    public void setSmartQQAccount(SmartQQAccount smartQQAccount){
        SmartQQAccountList.getInstance().put(smartQQAccount.getAccount(),smartQQAccount);
    }

    /**获取循环列表中的指定账号*/
    public SmartQQAccount getSmartQQAccount(String account){
        return SmartQQAccountList.getInstance().get(account);
    }
}
