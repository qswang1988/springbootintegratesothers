package com.qswang.ElasticSearch.bank.QueryBuilders;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Api(tags = "bank api test swagger2")
@RestController
@RequestMapping("elasticsearch")
@PropertySource("classpath:/ElasticSearch/bank.properties")
public class BankController {

    @Value("${index}")
    private String index;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //http://localhost:8080/elasticsearch/bank/age/34
    @RequestMapping("bank/age/{num}")
    @ResponseBody
    @ApiOperation("all accounts")
    public String SearchByExactAge(@PathVariable(value="num") String age) throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("age",age);

        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(TimeValue.timeValueSeconds(5));

        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        StringBuilder sb = new StringBuilder();
        for(SearchHit sh:response.getHits().getHits()){
            sb.append(sh.getSourceAsMap());
        }

        return sb.toString();
    }


    // http://localhost:8080/elasticsearch/bank/name?FirstName=Mcgee&&LastName=Mooney
    @RequestMapping("bank/name")
    @ResponseBody
    @ApiOperation("search by first name + last name")
    public String SearchByName(@RequestParam(value="FirstName",required = true) String FirstName, @RequestParam(value="LastName",required = true) String LastName) throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //TermQueryBuilder termQueryBuilder = QueryBuilders.
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("firstname",FirstName));
        boolQueryBuilder.must(QueryBuilders.matchQuery("lastname",LastName));
        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request,RequestOptions.DEFAULT);
        return Arrays.toString(response.getHits().getHits());
    }

    // http://localhost:8080/elasticsearch/bank/agerange/5/50?from=35&&to=38
    @RequestMapping("bank/agerange/{page}/{size}")
    @ResponseBody
    @ApiOperation("search by age range, from ? (inclusive) to ? (inclusive)")
    public String SearchAgeRange(@RequestParam(value="from",required = true) String from, @RequestParam(value = "to",required = true) String to,@PathVariable(value="page") int page,@PathVariable(value = "size") int size) throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // SET SIZE (OF A PAGE in such circumstance)
        sourceBuilder.size(size);
        // SET PAGE NUMBER
        sourceBuilder.from(page);

        sourceBuilder.timeout(new TimeValue(60, TimeUnit.MILLISECONDS));
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // set range of a property
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("age").from(from).to(to));


        // SORT
        sourceBuilder.sort("age", SortOrder.DESC);

        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);

        SearchResponse response = restHighLevelClient.search(request,RequestOptions.DEFAULT);
        System.out.println(response.getHits().getHits().length);
        return Arrays.toString(response.getHits().getHits());
    }


    // http://localhost:8080/elasticsearch/bank/address/0/50?address=Howard%20Avenue
    @ResponseBody
    @RequestMapping("bank/address/{page}/{size}")
    @ApiOperation("search by customer's address")
    public String searchAddress(@PathVariable(value="page") int page,@PathVariable(value="size") int size,@RequestParam(value="address",required=true) String address) throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        System.out.println(address);
        //TermQueryBuilder queryBuilder = QueryBuilders.termQuery("address",address);
        //FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("address",address).fuzziness(Fuzziness.AUTO);



        AbstractQueryBuilder queryBuilder;

        //if(address.contains(" "))

        queryBuilder = QueryBuilders.matchPhrasePrefixQuery("address",address);
        //else
        //    queryBuilder = QueryBuilders.matchQuery("address",address);

        sourceBuilder.from(page);
        sourceBuilder.size(size);

        sourceBuilder.query(queryBuilder);
        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request,RequestOptions.DEFAULT);

        return Arrays.toString(response.getHits().getHits());
    }

}
