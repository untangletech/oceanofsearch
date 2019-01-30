package psind.luceneSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import psind.lucene.utility.SearchUtility;

public class AemPageSearchTest {

	Logger logger = LoggerFactory.getLogger(AemPageSearchTest.class);
	
	@Test
	public static void PageSearchTest() {
		String pagepath = "/content/we-retail/us/en/experience/arctic-surfing-in-lofoten";
		String searchResult = null;
		try {
			searchResult = SearchUtility.searchFullTextInAEM("freshwater").get(0);
		} catch (Exception e) {
			
		}
		Assert.assertEquals(searchResult, pagepath);
		Assert.assertNotEquals(searchResult, "ydsatyufatsuft");
	}

}
