package ml.idream.autocode;

import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 解析HTML
 * @Author Aimy
 * @Date 2018/10/11 15:23
 **/
public class HtmlProcesser {

    //文件路径
    private static final String filePath = "C:\\Users\\Aimy\\Desktop\\wiki\\查看源.html";

    public static void main(String[] args) throws Exception {

        //读取文件内容
        String content = FileUtils.readFileToString(new File(filePath), Charset.forName("UTF-8"));
        //从文件内容中解析出数据
        List<CodeBase> codeBase = codeInfoProcesserWithJsoup(content);

        System.out.println(JSONArray.fromObject(codeBase));
    }

    /**
     * @Description 使用Jsoup解析
     * @Param [content]
     * @return java.util.List<ml.idream.autocode.CodeBase>
     * @Author Aimy
     * @Date  
     **/
    private static List<CodeBase> codeInfoProcesserWithJsoup(String content) {
        List<CodeBase> ret = new ArrayList<>();
        Document doc = Jsoup.parse(content);
        Elements tables = doc.getElementsByTag("table");
        for(Element table : tables){
            Elements trs = table.getElementsByTag("tr");
            int index = 0;
            CodeBase codeBase = new CodeBase();
            for(Element tr : trs){
                Elements tds = tr.getElementsByTag("td");
                if(tds.size() == 0) continue;
                String line = tds.get(1).text();
                switch (index){
                    case 0:
                        codeBase.setUrl(line);
                        codeBase.setController(CodeBaseUtils.controllerPrasser(line));
                        codeBase.setMethod(CodeBaseUtils.methodPattern(line));
                        break;
                    case 1:codeBase.setParam(line);break;
                    case 2:codeBase.setResponse(line);break;
                }
                ++index;
            }
            ret.add(codeBase);
        }
        return ret;
    }


}
