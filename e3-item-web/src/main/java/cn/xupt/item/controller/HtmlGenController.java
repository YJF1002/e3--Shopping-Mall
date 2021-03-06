package cn.xupt.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

/**
 * 生成静态页面测试Controller
 * <p>Title: HtmlGenController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class HtmlGenController {

	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	
	@RequestMapping("/genhtml")
	@ResponseBody
	public String genHtml() throws Exception{
		
		Configuration configuration = freeMarkerConfig.getConfiguration();
		//加载模板对象
		Template template = configuration.getTemplate("hello.ftl");
		//创建一个数据集
		Map date = new HashMap<>();
		//指定文件输出路径及文件名
		Writer out = new FileWriter(new File("E:/untitled/hell2.html"));
				
		//输出文件
		template.process(date, out);
		//关闭流
		out.close();
		
		return "ok";
	}
	
	
}
