package cn.xupt.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.xupt.mapper.TbItemMapper;
import cn.xupt.pojo.TbItem;
import cn.xupt.pojo.TbItemExample;

public class PageHelperTest {
	
	@Test
	public void testPageHelper() throws Exception{
		//初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/*");
		//从容器中获得代理对象
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		//执行sql语句之前设置分页信息使用的PageHelper的startPage方法。
		PageHelper.startPage(1, 10);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取分页信息。pageInfo、1.总记录数2、总页数。当前页码
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
		System.out.println(list.size());
	}
}