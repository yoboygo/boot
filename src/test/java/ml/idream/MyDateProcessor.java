package ml.idream;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description JSON解析日期格式
 * @Author Aimy
 * @Date 2018/10/10 15:51
 **/
public class MyDateProcessor implements JsonValueProcessor {

    private ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    @Override
    public Object processArrayValue(Object o, JsonConfig jsonConfig) {
        return sdf.get().format((Date)o);
    }

    @Override
    public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
        if(o instanceof Date){
            return sdf.get().format((Date)o);
        }else if(o instanceof Long){
            return sdf.get().format(new Date((Long)o));
        }
        return null;
    }
}
