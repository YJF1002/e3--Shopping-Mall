package cn.xupt.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 商品详情展示
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
import org.springframework.web.bind.annotation.RequestMapping;

import cn.xupt.item.pojo.Item;
import cn.xupt.pojo.TbItem;
import cn.xupt.pojo.TbItemDesc;
import cn.xupt.service.ItemService;
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId,Model model){
		
		TbItem tbItem = itemService.getItemById(itemId);
		Item item  = new Item(tbItem);
		
		TbItemDesc itemDesc = itemService.geTbItemDescById(itemId);
		
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);  
		
		return "item";
	}
}
