package ml.idream.qq.entity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description 待登陆的账号列表
 * @Author Aimy
 * @Date 2018/12/12 15:33
 **/
public class SmartQQAccountList {

//    private static ConcurrentHashMap<String, SmartQQAccount> smartQQAccountList;
    private static LinkedBlockingDeque<SmartQQAccount> smartQQAccountList;

    private SmartQQAccountList() {
    }

    public static LinkedBlockingDeque<SmartQQAccount> getInstance(){
        if(smartQQAccountList == null){
            synchronized (SmartQQAccountList.class){
                if(smartQQAccountList == null){
                    smartQQAccountList = new LinkedBlockingDeque<>();
                }
            }
        }
        return smartQQAccountList;
    }

    /**将账号新信息加入循环列表*/
    public static void setSmartQQAccount(SmartQQAccount smartQQAccount){
        SmartQQAccountList.getInstance().add(smartQQAccount);
    }

}
