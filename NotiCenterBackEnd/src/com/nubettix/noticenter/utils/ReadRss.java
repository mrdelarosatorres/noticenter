package com.nubettix.noticenter.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.nubettix.noticenter.rss.RssObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ReadRss {
	private static ReadRss instance = null;

	private URL rssURL;

	private ReadRss() {
	}

	public static ReadRss getInstance() {
		if (instance == null)
			instance = new ReadRss();
		return instance;
	}

	public void setURL(String url) {

		try {
			rssURL = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Return a list of RssObjects from Rss url
	 * 
	 * @return
	 */
	public List<RssObject> writeFeed() {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(rssURL.openStream());

			NodeList items = doc.getElementsByTagName("item");
			List<RssObject> rssList = new ArrayList<RssObject>();
			for (int i = 0; i < items.getLength(); i++) {
				Element item = (Element) items.item(i);
				RssObject r = new RssObject();
				r.setTitle(getValue(item, "title"));
				r.setDescription(getDescriptionValue(item, "description"));
				r.setLink(getValue(item, "link"));
				r.setPubDate(getValue(item, "pubDate"));
				rssList.add(r);
			}
			return rssList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.EMPTY_LIST;
	}

	public String getValue(Element parent, String nodeName) {
		return parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue();
	}
	public String getDescriptionValue(Element parent, String nodeName) {
		return parent.getElementsByTagName(nodeName).item(0).getTextContent();
	}
	

	public static void main(String[] args) {
		try {
			ReadRss reader = ReadRss.getInstance();
			//reader.setURL(new URL("https://actualidad.rt.com/feeds/actualidad.rss"));
			reader.writeFeed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
