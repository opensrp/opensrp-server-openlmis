package org.opensrp.stock.openlmis.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.ResultMatcher;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = TestWebContextLoader.class, locations = {
        "classpath:test-openlmis-application-context.xml"})
public abstract class BaseResourceTest {

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private DataSource openLmisDataSource;

    protected MockMvc mockMvc;

    protected ObjectMapper mapper = new ObjectMapper().enableDefaultTyping();

    protected static String tableName;

    @Before
    public void setUp() {
        setMockMvc();
    }

    protected void truncateTable(String tableName) {
        try {
            Connection connection = DataSourceUtils.getConnection(openLmisDataSource);
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE " + tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
    }

    protected List<Object> getResponseAsList(String url, String parameter, ResultMatcher expectedStatus) throws Exception {

        String finalUrl = url;
        if (parameter != null &&!parameter.isEmpty()) {
            finalUrl = finalUrl + "?" + parameter;
        }

        MvcResult mvcResult = this.mockMvc.perform(get(finalUrl).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(expectedStatus).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        if (responseString.isEmpty()) {
            return null;
        }

        List<Object> result = new Gson().fromJson(responseString, new TypeToken<List<TradeItemMetaData>>(){}.getType());
        return result;
    }

    protected String getResponseAsString(String url, String parameter, ResultMatcher expectedStatus) throws Exception {

        String finalUrl = url;
        if (parameter != null &&!parameter.isEmpty()) {
            finalUrl = finalUrl + "?" + parameter;
        }

        MvcResult mvcResult = this.mockMvc.perform(get(finalUrl).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(expectedStatus).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        if (responseString.isEmpty()) {
            return null;
        }
        return  responseString;
    }


    protected byte[] getRequestAsByteArray(String url, String parameterQuery, ResultMatcher expectedStatus) throws Exception {

        String finalUrl = url;
        if (!parameterQuery.isEmpty()) {
            finalUrl = finalUrl + "?" + parameterQuery;
        }

        MvcResult mvcResult = this.mockMvc.perform(get(finalUrl).accept(MediaType.APPLICATION_JSON))
                .andExpect(expectedStatus).andReturn();

        return mvcResult.getResponse().getContentAsByteArray();
    }

    protected JsonNode postRequestWithJsonContent(String url, String data, ResultMatcher expectedStatus) throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(
                post(url).contentType(MediaType.APPLICATION_JSON).body(data.getBytes()).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(expectedStatus).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        if (responseString.isEmpty()) {
            return null;
        }
        JsonNode actualObj = mapper.readTree(responseString);
        return actualObj;
    }

    protected JsonNode putRequestWithJsonContent(String url, String data, ResultMatcher expectedStatus) throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(
                put(url).contentType(MediaType.APPLICATION_JSON).body(data.getBytes()).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(expectedStatus).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        if (responseString.isEmpty()) {
            return null;
        }
        JsonNode actualObj = mapper.readTree(responseString);
        return actualObj;
    }

    protected MvcResult postRequestWithFormUrlEncode(String url, Map<String, String> parameters, ResultMatcher expectedStatus)
            throws Exception {

        List<BasicNameValuePair> paramList = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        MvcResult mvcResult = this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .body(EntityUtils.toString(new UrlEncodedFormEntity(paramList)).getBytes())
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(expectedStatus).andReturn();

        return mvcResult;
    }

    protected JsonNode postRequestWithBasicAuthorizationHeader(String url, String userName, String password,
                                                            ResultMatcher expectedStatus) throws Exception {

        String basicAuthCredentials = new String(Base64.encode((userName + ":" + password).getBytes()));
        System.out.println(basicAuthCredentials);
        MvcResult mvcResult = this.mockMvc.perform(
                post(url).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic " + basicAuthCredentials)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(expectedStatus).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        if (responseString.isEmpty()) {
            return null;
        }
        JsonNode actualObj = mapper.readTree(responseString);
        return actualObj;
    }

    protected <T> List<T> createObjectListFromJson(JsonNode jsonList, Class<T> classOfT) throws IOException {

        final List<T> objectList = new ArrayList<>();
        for (int i = 0; i < jsonList.size(); i++) {
            T object = mapper.treeToValue(jsonList.get(i), classOfT);
            objectList.add(object);
        }
        return objectList;
    }

    /** Objects in the list should have a unique uuid identifier field **/
    protected void assertTwoListsAreSameIgnoringOrder(List<Object> expectedList, List<Object> actualList, boolean isSync) {

        assertEquals(expectedList.size(), actualList.size());

        Set<String> expectedIds = new HashSet<>();
        for (Object expected : expectedList) {
            expectedIds.add(((BaseMetaData) expected).getId());
        }
        for (Object actual : actualList) {
           assertTrue(expectedIds.contains(((BaseMetaData) actual).getId()));
        }
    }
}

