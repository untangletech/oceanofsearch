package psind.lucene.demo;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import psind.lucene.utility.Constant;

public class AemPageSearchDemo {

	public static void main(String[] args) {
		//IndexingUtility.createAemPagesIndex("/content/we-retail/ca/en/men", Constant.INDEX_DIRECTORY);
		try {
			Search("ModelService");
		} catch (Exception e) {
			System.out.println("Exception : "+e.getMessage());
		}
	}
	//FullTextSearch from Index Directory
	private static void Search(String fullTextSearchText) throws Exception {
		Directory iDirectory = FSDirectory.open(new File(Constant.INDEX_DIRECTORY).toPath());
		DirectoryReader directoryReader = DirectoryReader.open(iDirectory);
		IndexSearcher iSearcher = new IndexSearcher(directoryReader);
		Query query = new QueryParser(Constant.CONTENTS, new StandardAnalyzer()).parse(fullTextSearchText);
		ScoreDoc[] hits = iSearcher.search(query, 30).scoreDocs;
		for (ScoreDoc hit : hits) {
			System.out.println("Hit : " + iSearcher.doc(hit.doc).get(Constant.FILE_PATH));
		}
		iDirectory.close();
		directoryReader.close();
	}

}
