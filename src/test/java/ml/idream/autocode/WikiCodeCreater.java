package ml.idream.autocode;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 创建响应的类
 * @Author Aimy
 * @Date 2018/10/11 19:20
 **/
public class WikiCodeCreater {

    //配置类
    private CodeConfig codeConfig;

    public WikiCodeCreater(CodeConfig codeConfig) {
        this.codeConfig = codeConfig;
    }

    /**
     * @Description 生成类文件
     * @Param [codeBaseList]
     * @return void
     * @Author Aimy
     * @Date  
     **/
    public void creater(List<CodeBase> codeBaseList){
        Map<String,List<CodeBase>> controllers = controllerSelect(codeBaseList);
        for(Map.Entry<String,List<CodeBase>> entry : controllers.entrySet()){
            //生成Controller文件
            createController(entry.getKey(),entry.getValue());
        }
    }

    /**
     * @Description 生成Controller文件
     * @Param [controllerName, methods]
     * @return void
     * @Author Aimy
     * @Date  
     **/
    private void createController(String controllerName, List<CodeBase> methods) {
        createParamClass(methods);
        createResponseClass(methods);
    }
    
    /**
     * @Description 创建返回结果类
     * @Param [methods]
     * @return void
     * @Author Aimy
     * @Date  
     **/
    private void createResponseClass(List<CodeBase> methods) {
    }

    /**
     * @Description 创建参数类
     * @Param [methods]
     * @return void
     * @Author Aimy
     * @Date  
     **/
    private void createParamClass(List<CodeBase> methods) {
        StringBuilder field = new StringBuilder();
        StringBuilder seterGeter = new StringBuilder();
        for(CodeBase method : methods){
            String source = method.getParam();
            //将参数处理成JSON
            JSONObject paramsObject = JSONObject.fromObject(source.replaceAll("//\\S+",""));
            for(Object key : paramsObject.keySet()){
                String keyStr = key.toString();
                if(codeConfig.getParamStayKey().contains(keyStr)) continue;
                field.append("    parivate String ").append(keyStr).append(";\n");
                seterGeter.append(assembelSeter(keyStr)).append("\n").append(assembelGeter(keyStr)).append("\n");
            }
        }
    }

    /**
     * @Description 拼装getter
     * @Param [field]
     * @return java.lang.String
     * @Author Aimy
     * @Date  
     **/
    private String assembelGeter(String field) {
        String getter = "    public String get${field}(){\n    return ${fieldEntry};  \n}";
        return getter.replace("${fieldEntry}",field).replace("${field}",CodeBaseUtils.upperCaseFirstChatar(field));
    }

    /**
     * @Description 拼装Seter
     * @Param [field]
     * @return java.lang.String
     * @Author Aimy
     * @Date  
     **/
    private String assembelSeter(String field) {
        String setter = "    public void set${field} (String ${fieldEntry}){\n    this.${fieldEntry} = ${fieldEntry};\n    }";
        return setter.replace("${fieldEntry}",field).replace("${field}",CodeBaseUtils.upperCaseFirstChatar(field));
    }

    /**
     * @Description 按照Controller将接口方法分组
     * @Param [codeBaseList]
     * @return java.util.Map<ControllerName,Methods>
     * @Author Aimy
     * @Date
     **/
    private Map<String,List<CodeBase>> controllerSelect(List<CodeBase> codeBaseList){
        Map<String,List<CodeBase>> ret = new HashMap<String,List<CodeBase>>();
        for(CodeBase codeBase : codeBaseList){
            String controller = codeBase.getController();
            if(controller == null) continue;
            List<CodeBase> methodList = ret.get(controller);
            if(methodList == null){
                methodList = new ArrayList<CodeBase>();
            }
            methodList.add(codeBase);
            ret.put(codeBase.getController(),methodList);
        }
        return ret;
    }
}
