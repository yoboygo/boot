package ml.idream.copyhtml;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* 提取单页html，并将其中需求要的样式文件整合
* */
public class TestCopyHtml {

    private static String basePath = "E:\\doc\\模板\\shell181-首页\\";//,分割

    private static String destPath = "";//目标路径


    public static void main(String[] args){
        String fileName = "index.html";
        File htmlFile = new File(basePath + fileName);
        cssStyleProceser(htmlFile);//处理CSS文件
    }

    /*
    * 处理CSS文件
    * */
    private static void cssStyleProceser(File htmlFile) {

        //提取CSS路径 $3 带引号，单双都有可能
        List<String> cssPaths = new ArrayList<String>();
        Set<String> cssStyles = new HashSet<String>();
        Pattern titlePattern = Pattern.compile("title");
        Pattern linkPattern = Pattern.compile("href(\\s+)?=(\\s+)?[\"'](.*\\.css?)[\"']");
        Pattern cssPattern = Pattern.compile("class(\\s+)?=(\\s+)?[\"'](.*?)[\"']");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(htmlFile)))){

            while (br.ready()){
                String line = br.readLine();
//                System.out.println(line);
                Matcher titleMatcher  = titlePattern.matcher(line);

                if(titleMatcher.matches()){
                    System.out.println(line + "---" + "123123123");
                    return;
                }

                Matcher linkMatcher  = linkPattern.matcher(line);
                Matcher cssMatcher  = cssPattern.matcher(line);
                //记录Link地址
                if(linkMatcher.matches()){
                    String linkPath = linkMatcher.group(3);
                    cssPaths.add(linkPath);
                }
                //记录CSS样式名称
                if(cssMatcher.matches()){
                    String css = cssMatcher.group(3);
                    cssStyles.addAll(Arrays.asList(css.split(" ")));
                }
//                System.out.println(line.replace("title","123"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(cssPaths.toString());
        System.out.println(cssStyles.toString());
    }


}
