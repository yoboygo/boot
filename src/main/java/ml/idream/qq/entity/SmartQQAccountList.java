package ml.idream.qq.entity;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description 待登陆的账号列表
 * @Author Aimy
 * @Date 2018/12/12 15:33
 **/
public enum SmartQQAccountList {

    INSTANCE;

    private LinkedBlockingDeque<SmartQQAccount> smartQQAccountList = new LinkedBlockingDeque<SmartQQAccount>();

    /**将账号新信息加入循环列表*/
    public static void addSmartQQAccount(SmartQQAccount smartQQAccount){
        SmartQQAccountList.INSTANCE.getSmartQQAccountList().push(smartQQAccount);
    }

    public LinkedBlockingDeque<SmartQQAccount> getSmartQQAccountList() {
        return smartQQAccountList;
    }

    public void setSmartQQAccountList(LinkedBlockingDeque<SmartQQAccount> smartQQAccountList) {
        this.smartQQAccountList = smartQQAccountList;
    }
}
