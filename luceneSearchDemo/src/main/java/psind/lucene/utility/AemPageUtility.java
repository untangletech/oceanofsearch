package psind.lucene.utility;

import java.util.Arrays;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

public class AemPageUtility {

	static String[] ignoreProperties = {"cq:lastModified","cq:lastModifiedBy","cq:lastRolledout","cq:lastRolledoutBy", "cq:template", "jcr:created", "jcr:createdBy", "jcr:mixinTypes", "jcr:primaryType" ,"sling:resourceType", "jcr:lastModifiedBy","jcr:lastModified"};
	static String[] indexProperties = {"","",""};
	
	public static void main(String[] args) {
		//print all pages
	}
	
	public static void printAllPagePaths(Node pageNode) throws RepositoryException {
		System.out.println(pageNode.getPath());
		NodeIterator nodeItr = pageNode.getNodes();
		while (nodeItr.hasNext()) {
			Node childNode = nodeItr.nextNode();
			if(isPageNode(childNode)) {
				printAllPagePaths(childNode);
			}
		}
	}
	
	//returns page content
	public static String getPageData(Node pageNode) throws PathNotFoundException, RepositoryException {
		StringBuilder pageContent = new StringBuilder();
		Node pageJcrContentNode = pageNode.getNode("jcr:content");
		getContent(pageJcrContentNode, pageContent);
		System.out.println("Page Content : "+pageContent.toString());
		return pageContent.toString();
	}

	//checks if the node is AEM Page type of node
	public static boolean isPageNode(Node node) throws RepositoryException {
		if (node.hasNode("jcr:content")) {
			return true;
		}
		return false;
	}
	
	//get content from page - collets content of all properties of all nodes under the page type of node
	public static void getContent(Node node, StringBuilder pageContent) throws RepositoryException {
		pageContent.append(getNodeContent(node));
		pageContent.append("\n");
		if (node.hasNodes()) {
			NodeIterator childNodesItr = node.getNodes();
			while (childNodesItr.hasNext()) {
				Node childNode = childNodesItr.nextNode();
				getContent(childNode, pageContent);
			}
		}
	}
	
	//returns content from one jcr node
	public static String getNodeContent(Node node) throws RepositoryException {
		PropertyIterator propertyItr = node.getProperties();
		StringBuilder nodeContent = new StringBuilder();
		while (propertyItr.hasNext()) {
			Property property = propertyItr.nextProperty();
			if (!Arrays.asList(ignoreProperties).contains(property.getName())) {
				if (property.isMultiple()) {
					for(Value value : property.getValues()) {
						nodeContent.append(value.getString());
					}
				} else {
					nodeContent.append(" "+ property.getValue().getString() +" ");
				}
			}
		}
		return nodeContent.toString();
	}

}
