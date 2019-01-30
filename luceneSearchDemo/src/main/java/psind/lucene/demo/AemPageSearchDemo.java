package psind.lucene.demo;

import psind.lucene.utility.Constant;
import psind.lucene.utility.IndexingUtility;
import psind.lucene.utility.SearchUtility;

public class AemPageSearchDemo {

	public static void main(String[] args) {
		//IndexingUtility.createAemPagesIndex("/content/we-retail/us/en", Constant.INDEX_DIRECTORY);
		try {
			System.out.println(SearchUtility.searchFullTextInAEM("freshwater").get(0));
		} catch (Exception e) {
			System.out.println("Exception : "+e.getMessage());
		}
	}

}
