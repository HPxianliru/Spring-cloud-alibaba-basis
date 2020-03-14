package com.xian.es.controller;


import com.xian.es.data.Novel;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2020/02/02 2:17 下午
 */
@RestController
public class NovelController {


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @RequestMapping("/search")
    public String findDoc() {
        // 构造搜索条件
        QueryBuilder builder = QueryBuilders.existsQuery("word_count");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
        // 执行查询
        List<Novel> novels = elasticsearchTemplate.queryForList(searchQuery, Novel.class);
        for (Novel novel : novels) {
            System.out.println(novel);
        }
        return "Search Success";
    }

}
