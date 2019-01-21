package psind.lucene.utility;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;

public class DocumentsUtility {
	
	
	public DocumentsUtility() {
		
	}
	
	//get doc from file path
	public static Document getDocFromFile(String filePath) throws Exception {
		File file = new File(filePath);
		return getDocFromFile(file);
	}
	
	//get document from file
	public static Document getDocFromFile(File file) throws Exception {
		Reader reader = new FileReader(file);
		Field nameField = new TextField(Constant.FILE_NAME, file.getName(), Store.YES);
		Field pathField = new TextField(Constant.FILE_PATH, file.getAbsolutePath(), Store.YES);
		Field contentField = new TextField(Constant.CONTENTS, reader);
		Document document = new Document();
		document.add(nameField);
		document.add(pathField);
		document.add(contentField);
		return document;
	}
	
	//get Document from AEM Page
	public static Document getDocFromAemPage(Node pageNode) throws PathNotFoundException, RepositoryException {
		Field pagePath = new TextField("PagePath", pageNode.getPath(), Store.YES);
		Field pageContent = new TextField("Content", AemPageUtility.getPageData(pageNode),Store.NO);
		Document document = new Document();
		document.add(pagePath);
		document.add(pageContent);
		return document;
	}
	
}
