package ml.idream.qq.entity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description 待登陆的账号列表
 * @Author Aimy
 * @Date 2018/12/12 15:33
 **/
public enum SmartQQAccountList {

    INSTANCE;

//    private LinkedBlockingDeque<SmartQQAccount> smartQQAccountList = new LinkedBlockingDeque<SmartQQAccount>();

    private static ConcurrentHashMap<String,LinkedBlockingDeque<SmartQQAccount>> smartQQAccount = new ConcurrentHashMap<>();

    /**将账号新信息加入循环列表*/
    public static void addSmartQQAccount(String loginKey, SmartQQAccount smartQQAccount){
        SmartQQAccountList.INSTANCE.getSmartQQAccount(loginKey).push(smartQQAccount);
    }

    public static LinkedBlockingDeque<SmartQQAccount> getSmartQQAccount(String loginKey) {
        return smartQQAccount.get(loginKey);
    }
}
