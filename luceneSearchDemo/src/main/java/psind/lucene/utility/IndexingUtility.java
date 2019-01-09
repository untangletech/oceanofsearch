package psind.lucene.utility;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexingUtility {
	
	public void Indexer(Analyzer analyzer, Directory directory) throws Exception {
		analyzer = new StandardAnalyzer();
		directory = FSDirectory.open(new File("/prabhat/luceneDemoDir").toPath());
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	}

}
