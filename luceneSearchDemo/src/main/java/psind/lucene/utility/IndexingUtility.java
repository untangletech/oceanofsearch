package psind.lucene.utility;

import java.io.File;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.derby.tools.sysinfo;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexingUtility {

	private static Analyzer analyzer = new StandardAnalyzer();
		
	//Indexing Files of given directory
	public static void createFilesIndex() throws Exception {
		createFilesIndex(Constant.SEARCH_DIRECTORY,Constant.INDEX_DIRECTORY);
	}

	//Indexing
	public static void createFilesIndex(String searchDir, String indexDir) throws Exception {
		File[] files = new File(searchDir).listFiles();
		Directory directory = FSDirectory.open(new File(indexDir).toPath());
		IndexWriterConfig iWriterConfig = new IndexWriterConfig(analyzer);
		IndexWriter iWriter = new IndexWriter(directory, iWriterConfig);
		for (File file : files) {
			Document document = DocumentsUtility.getDocFromFile(file);
			iWriter.addDocument(document);
			System.out.println("Indexed Doc : "+file.getAbsolutePath());
		}
		iWriter.close();
	}
	
	//AEM Pages Indexing
	public static void createAemPagesIndex(String rootPagepath, String indexDir) {
		try {
			Directory directory = FSDirectory.open(new File(indexDir).toPath());
			IndexWriterConfig iWriterConfig = new IndexWriterConfig(analyzer);
			IndexWriter iWriter = new IndexWriter(directory, iWriterConfig);
			
			Repository repository = JcrUtils.getRepository("http://127.0.0.1:4502/crx/server");
			Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()),"crx.default");
			Node rootPageNode = session.getNode(rootPagepath);
			
			createAemIndex(iWriter,rootPageNode);			
			iWriter.close();
		} catch (Exception e) {
			System.out.println("Excep : "+e.getMessage());
		}
	}

	//creating index for page
	private static void createAemIndex(IndexWriter iWriter, Node rootPageNode) throws RepositoryException {
		try {
			iWriter.addDocument(DocumentsUtility.getDocFromAemPage(rootPageNode));
			System.out.println("page indexed : " + rootPageNode.getPath());
		} catch (Exception e) {
			System.out.println("Exception in " + e.getMessage());
		}
		NodeIterator nodeItr = rootPageNode.getNodes();
		while (nodeItr.hasNext()) {
			Node childNode = nodeItr.nextNode();
			if(AemPageUtility.isPageNode(childNode)) {
				createAemIndex(iWriter, childNode);
			}
		}
		
	}
	
}
