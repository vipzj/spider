package com.liuy202;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;

@Component
public class IndexImpl implements Index {

	@Override
	public void index(String content, String uri) {
		// TODO Auto-generated method stub
		try {
			Analyzer analyzer = new StandardAnalyzer();

			IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST,
					analyzer);
			SimpleFSDirectory simpleFSDirectory = new SimpleFSDirectory(
					new File("c:/temp/lucene.index"));

			IndexWriter writer = new IndexWriter(simpleFSDirectory, conf);
			Document doc = new Document();
			content = removeHtmlTag(content);
			doc.add(new TextField("content", content, Store.YES));
			doc.add(new StoredField("uri", uri));
			writer.addDocument(doc);
			writer.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String search(String searchContent) {
		// TODO Auto-generated method stub

		String result = "";
		try {
			Analyzer analyzer = new StandardAnalyzer();
			SimpleFSDirectory simpleFSDirectory = new SimpleFSDirectory(
					new File("c:/temp/lucene.index"));
			QueryBuilder builder = new QueryBuilder(analyzer);
			Query q = builder.createPhraseQuery("content", searchContent);

			int hitsPerPage = 10;
			IndexReader reader = DirectoryReader.open(simpleFSDirectory);

			IndexSearcher searcher = new IndexSearcher(reader);

			TopScoreDocCollector collector = TopScoreDocCollector.create(
					hitsPerPage, true);

			searcher.search(q, collector);

			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			System.out.println("Found " + hits.length + " results.");

			for (int i = 0; i < hits.length; ++i) {
				ScoreDoc hit = hits[i];
				int docId = hit.doc;
				Document d = searcher.doc(docId);
				String oneUri = d.get("uri");
				System.out.println((i + 1) + ". " + oneUri);
				result += "<p><a href='"+d.get("uri") + "'>"+d.get("uri")+"</a></p>";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String removeHtmlTag(String inputString) {
		if (inputString == null)
			return null;
		String htmlStr = inputString; // String including html tags
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_special;
		java.util.regex.Matcher m_special;
		try {
			// define the regex of script{or <script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			// define the regex of style{or <style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			// define the regex of tags
			String regEx_html = "<[^>]+>";
			// define the regex of some special string£º&nbsp;
			String regEx_special = "\\&[a-zA-Z]{1,10};";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // filter out script label
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // filter out style label
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // filter out tags label
			p_special = Pattern
					.compile(regEx_special, Pattern.CASE_INSENSITIVE);
			m_special = p_special.matcher(htmlStr);
			htmlStr = m_special.replaceAll(""); // filter out special string
												// label
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// return result
	}

}
