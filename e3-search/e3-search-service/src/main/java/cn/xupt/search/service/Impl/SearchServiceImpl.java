package cn.xupt.search.service.Impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xupt.common.pojo.SearchResult;
import cn.xupt.search.dao.SearchDao;
import cn.xupt.search.service.SearchService;

/**
* 商品搜索service
* <p>Title: search</p>
* <p>Description: </p>
* @param keyword
* @param page
* @param rows
* @return
* @see cn.xupt.search.service.SearchService#search(java.lang.String, int, int)
*/

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String keyword, int page, int rows) throws Exception {
		// 创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(keyword);
		//设置分页条件
		if(page <=0) page = 1;
		query.setStart((page -1) * rows);
		query.setRows(20);
		//设置默认搜索域
		query.set("df", "item_title");
		//开启高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		//调用dao执行查询
		SearchResult searchResult = searchDao.search(query);
		//计算总页数
		long recordCount = searchResult.getRecordCount();
		int totalPage = (int) (recordCount / rows);
		if(recordCount % rows > 0){
			totalPage ++;
		}
		searchResult.setTotalPages(totalPage);
		return searchResult;
	}
}
