package cn.xupt.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.xupt.item.pojo.Item;
import cn.xupt.pojo.TbItem;
import cn.xupt.pojo.TbItemDesc;
import cn.xupt.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class HtmlGenListener implements MessageListener{
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${HTML_GEN_PATH}")
	private String HTML_GEN_PATH;
	
	
	@Override
	public void onMessage(Message message){
		try {
			//创建一个模板，参考jsp
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			
			//从消息中取出商品id
			Long itemId = new Long(text);
			//等待事务提交
			Thread.sleep(1000);
			//根据商品id查询商品信息，商品基本信息和商品描述
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			//创建一个 数据集，把商品数据封装
			TbItemDesc tbItemDesc = itemService.geTbItemDescById(itemId);
			Map data = new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", tbItemDesc);
			//加载模板对象
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			//创建一个输出液，指定输出的目录及文件名
			Writer out = new FileWriter(HTML_GEN_PATH + itemId + ".html");
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
