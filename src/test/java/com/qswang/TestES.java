package com.qswang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qswang.ElasticSearch.User;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestES {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private String index = "springbootstudy";

    @Autowired
    private ObjectMapper mapper;

    // create an index
    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("springbootstudy");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    // check existence of index created
    @Test
    public void getIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("springbootstudy");
        boolean exist = restHighLevelClient.indices().exists(request,RequestOptions.DEFAULT);
        assertTrue(exist);
    }

    // delete the created index
    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("springbootstudy");
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request,RequestOptions.DEFAULT);
        assertTrue(response.isAcknowledged());
    }

    // add a doc to index
    @Test
    public void addDoc() throws IOException {
        User user = new User("json",32);
        IndexRequest request = new IndexRequest(index);
        // put /springbootstudy/_doc/1
        request.id("2");
        request.timeout(TimeValue.timeValueSeconds(1));
        //ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        request.source(jsonString, XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(request,RequestOptions.DEFAULT);

        System.out.println(response.status());
        System.out.println(response.toString());

    }

    // try to get a doc we added before
    @Test
    public void checkDoc() throws IOException {
        GetRequest getRequest = new GetRequest(index,"1");
        //
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exist = restHighLevelClient.exists(getRequest,RequestOptions.DEFAULT);
        assertTrue(exist);
    }

    @Test
    public void getDoc() throws IOException {
        GetRequest request = new GetRequest(index,"2");
        //
        GetResponse response = restHighLevelClient.get(request,RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    @Test
    public void updateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest(index,"1");
        request.timeout(TimeValue.timeValueSeconds(1));
        User user = new User("qiushi1",21);
        request.doc(mapper.writeValueAsString(user),XContentType.JSON);
        UpdateResponse response = restHighLevelClient.update(request,RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response);
    }

    @Test
    public void deleteDoc() throws IOException {
        DeleteRequest request = new DeleteRequest(index,"1");
        request.timeout("1s");
        DeleteResponse response = restHighLevelClient.delete(request,RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    @Test
    public void testBulkRequestAdd() throws IOException{
        // leave argument empty
        BulkRequest request = new BulkRequest();
        request.timeout("10s");
        List<User> list = new ArrayList<>();
        list.add(new User("user1",31));
        list.add(new User("user2",32));
        list.add(new User("user3",33));
        list.add(new User("user4",34));
        list.add(new User("user5",35));
        list.add(new User("user6",36));
        list.add(new User("user7",37));
        // add all elements
        for(int id = 3;id<3+list.size();id++){
            request.add(
                    new IndexRequest(index).id(""+id).
                            source(mapper.writeValueAsString(list.get(id-3)),XContentType.JSON));
        }
        BulkResponse responses = restHighLevelClient.bulk(request,RequestOptions.DEFAULT);
        System.out.println(responses.status());
        System.out.println(responses.hasFailures());
    }

    @Test
    public void testBulkRequestUpdate() throws IOException{
        // leave argument empty
        BulkRequest request = new BulkRequest();
        request.timeout("10s");
        List<User> list = new ArrayList<>();
        list.add(new User("user1",21));
        list.add(new User("user2",22));
        list.add(new User("user3",23));
        list.add(new User("user4",24));
        list.add(new User("user5",25));
        list.add(new User("user6",26));
        list.add(new User("user7",27));
        // add all elements
        for(int id = 3;id<3+list.size();id++){
            request.add(
                    new UpdateRequest(index,""+id).
                            doc(mapper.writeValueAsString(list.get(id-3)),XContentType.JSON));
        }
        BulkResponse responses = restHighLevelClient.bulk(request,RequestOptions.DEFAULT);
        System.out.println(responses.status());
        System.out.println(responses.hasFailures());
    }

    @Test
    public void testSearch() throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name","json");
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(TimeValue.timeValueSeconds(5));
        request.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(request,RequestOptions.DEFAULT);

        System.out.println(mapper.writeValueAsString(searchResponse.getHits()));
        System.out.println("###");
        for(SearchHit sh:searchResponse.getHits().getHits()){
            System.out.println(sh.getSourceAsMap());
            User u = mapper.readValue(sh.getSourceAsString(), User.class);
        }

    }

}
