package psind.lucene.demo;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import psind.lucene.utility.DocumentsUtility;
import psind.lucene.utility.Constant;

public class FileSearchDemo {
	
	public static final String SEARCH_DIRECTORY = "D:/codeBase/kme/eu-trunk/euTemplate/core/src/main/java/com/kia/eut/core/service";
	public static final String INDEX_DIRECTORY = "D:/dev/luceneIndexs";
	
	public static void main(String[] args) {
		try {
			createIndex();
			//Search("ModelService");
		} catch (Exception e) {
			System.out.println("Excep : "+e.getMessage());
		}
	}
	
	//FullTextSearch from Index Directory
	private static void Search(String fullTextSearchText) throws Exception {
		Directory iDirectory = FSDirectory.open(new File(INDEX_DIRECTORY).toPath());
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

	//Indexing Files of given directory
	private static void createIndex() throws Exception {
		File[] files = new File(SEARCH_DIRECTORY).listFiles();
		Directory directory = FSDirectory.open(new File(INDEX_DIRECTORY).toPath());
		IndexWriterConfig iWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
		IndexWriter iWriter = new IndexWriter(directory, iWriterConfig);
		for (File file : files) {
			Document document = DocumentsUtility.getDocFromFile(file);
			iWriter.addDocument(document);
			System.out.println("Indexed Doc : "+file.getAbsolutePath());
		}
		iWriter.close();
	}
	
	
}
