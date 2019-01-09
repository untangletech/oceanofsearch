package psind.lucene.demo;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class PrabhatsLuceneDemo {

	public static void main(String[] args) {
		try {
			
			//Indexing
			Analyzer analyzer = new StandardAnalyzer();
			Directory directory = FSDirectory.open(new File("/prabhat/luceneDemoDir").toPath());
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
		    IndexWriter iwriter = new IndexWriter(directory, config);
		    addDocs(iwriter);
			
		    //Searching the Index
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		    
		    //query
		    Query query = new QueryParser("title", analyzer).parse("Computer");
		    
		    ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
		    for (int i = 0; i < hits.length; i++) {
		        Document hitDoc = isearcher.doc(hits[i].doc);
		        System.out.println(hitDoc.get("title"));
		    }
		    ireader.close();
		    directory.close();
		} catch (Exception e) {
			System.out.println("Exception : "+ e.getMessage());
		}
	}
	
	private static void addDocs(IndexWriter iwriter) throws Exception {
		addDoc(iwriter, "Lucene in Action", "193398817");
		addDoc(iwriter, "Lucene for Dummies", "55320055Z");
		addDoc(iwriter, "Managing Gigabytes", "55063554A");
		addDoc(iwriter, "The Art of Computer Science", "9900333X");
		iwriter.close();
	}
	private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
		  Document doc = new Document();
		  doc.add(new TextField("title", title, Store.YES));
		  doc.add(new StringField("isbn", isbn, Store.YES));
		  w.addDocument(doc);
	}

}
