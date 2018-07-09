package ml.idream.global;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
@RequestMapping("/advice")
public class GlobalAdviceController {

    @RequestMapping(value = "/err",method = RequestMethod.GET)
    public String getSomething(@ModelAttribute("msg") String msg, DemoAdvice demoAdvice, String demo) throws Exception{
        System.out.println("advice error");
        throw new IllegalAccessException("非常抱歉，参数有误/" + "来自@ModelAttribute:" + msg + " address:" + demoAdvice.getAddress() + " demo" + demo);
    }

    /*
     * 上传文件
     * */
    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) throws Exception{

        try {
            System.out.println("--->" + id);
            FileUtils.writeByteArrayToFile(new File("f:/upload/" + file.getOriginalFilename()), file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("上传文件失败，" + e.getMessage());
        }
        return "成功！";
    }

}
