package psind.lucene.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import psind.lucene.utility.Constant;
import psind.lucene.utility.IndexingUtility;

public class SearchUtility {

    //FullTextSearch from Index Directory
	public static List<String> searchFullTextInAEM(String fullTextSearchText) throws Exception {
        List<String> aemSearchList = new ArrayList<String>();
		Directory iDirectory = FSDirectory.open(new File(Constant.INDEX_DIRECTORY).toPath());
		DirectoryReader directoryReader = DirectoryReader.open(iDirectory);
		IndexSearcher iSearcher = new IndexSearcher(directoryReader);
		Query query = new QueryParser("Content", new StandardAnalyzer()).parse(fullTextSearchText);
		ScoreDoc[] hits = iSearcher.search(query, 30).scoreDocs;
		for (ScoreDoc hit : hits) {
            System.out.println("Hit : " + iSearcher.doc(hit.doc).get("PagePath"));
            aemSearchList.add(iSearcher.doc(hit.doc).get("PagePath"));
		}
		iDirectory.close();
        directoryReader.close();        
        return aemSearchList;
	}

}