package com.fusemachines.fusevitae.junit;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.extension.listener.AnnotationEnabler;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PowerMockListener;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.spi.PowerMockTestListener;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.tests.utils.impl.PowerMockTestNotifierImpl;

import com.fusemachines.fusevitae.config.EsClient;
import com.fusemachines.fusevitae.config.utils.DateUtils;
import com.fusemachines.fusevitae.repo.impl.EmployeeRepositoryImpl;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
//@PrepareForTest({EmployeeRepositoryImpl.class})
//@PrepareForTest(fullyQualifiedNames = "com.fusemachines.fusevitae.*")
//@PowerMockListener(AnnotationEnabler.class)
public class TestGetEmployeeRepository_Email  extends PowerMockTestNotifierImpl{

	public TestGetEmployeeRepository_Email(PowerMockTestListener[] powerMockTestListeners) {
		super(powerMockTestListeners);
		// TODO Auto-generated constructor stub
	}

	@Mock private EsClient esClient;
	@Mock private TransportClient transportClient;
	@Mock private SearchRequestBuilder searchRequestBuilder;
	@Mock private SearchResponse searchResponse;
	@Mock private DateUtils dateUtils;
	@Mock private SearchHit searchHit;
	
	@InjectMocks private EmployeeRepositoryImpl employeeRepositoryImpl;
	
	
	
	
	@Test
	public void TestGetEmployeeByEmail_Sucess() {
		when(esClient.getClient()).thenReturn(transportClient);
		when(transportClient.prepareSearch(anyString())).thenReturn(searchRequestBuilder);
		when(searchRequestBuilder.setTypes(anyString())).thenReturn(searchRequestBuilder);
		
		when(searchRequestBuilder.get()).thenReturn(searchResponse);
		
		
		System.out.println("------------"+searchHit);
		Long wdate = 1524459889000l;
		Long jdate = 1524459889000l;
		
		Map<String, Object> result = new HashMap<>();
		result.put("fullName", "ruman");
		result.put("workDate", wdate);
		result.put("joinedDate", jdate);
		
//		SearchHit searchHit = new  SearchHit(0);
		SearchHit[] searchHitsInitial = new SearchHit[1];
		searchHitsInitial[0] = searchHit;
		PowerMockito.mockStatic(SearchHit.class);
		PowerMockito.mockStatic(SearchHits.class);
		SearchHit searchHit = Mockito.mock(SearchHit.class);
		SearchHits searchHits = Mockito.mock(SearchHits.class);
		
		//SearchHits searchHits = new SearchHits(searchHitsInitial, 1, 0);
		PowerMockito.when(searchResponse.getHits()).thenReturn(searchHits);
		PowerMockito.when(searchHit.getSourceAsMap()).thenReturn(result);
		
		System.out.println("SearchHits =======> "+ searchHits);
		System.out.println("SearchHitGet =======> "+ searchHits.getHits());

		//when(searchHits.getHits()[0]).thenReturn(searchHit);
		//when(searchHit.getSourceAsMap()).thenReturn(result);
		
		//result.put("esId", "");
		
		Map<String, Object> expectedResult = new HashMap<>();
		expectedResult.put("fullName", "ruman");
		expectedResult.put("workDate", "2018-04");
		expectedResult.put("joinedDate", "2018-04");

		Map<String, Object> obtainedResult = employeeRepositoryImpl.getEmployeeByEmailId("ruman@gmail.com");
		
		assertEquals(expectedResult.get("workDate"), obtainedResult.get("workDate"));
		assertEquals(expectedResult.get("joinedDate"), obtainedResult.get("joinedDate"));
		assertEquals(expectedResult.get("fullName"), obtainedResult.get("fullName"));
		
		
	
		
		
	}

}
