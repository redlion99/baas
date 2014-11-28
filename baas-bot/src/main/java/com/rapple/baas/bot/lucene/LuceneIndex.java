package com.rapple.baas.bot.lucene;

import com.rapple.baas.bot.dto.QuestionAndAnswer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by libin on 14-11-28.
 */
@Component
public class LuceneIndex {
    private RAMDirectory ramdir=new RAMDirectory();
    public static String FILE_INDEX="/tmp/idx";

    public LuceneIndex createIndex(Collection<QuestionAndAnswer> questionAndAnswers) throws IOException {
        Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_47);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, luceneAnalyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter(ramdir, config);
        for(QuestionAndAnswer qa:questionAndAnswers){
            Document doc = new Document();
            doc.add(new TextField("question",qa.getQuestion(), Field.Store.YES));
            doc.add(new TextField("answer",qa.getAnswer(), Field.Store.YES));
            indexWriter.addDocument(doc);
        }
        indexWriter.close();
        return this;
    }

    public void mergeIndex() throws IOException {
        File indexDir = new File(FILE_INDEX);
        FSDirectory fsdir=FSDirectory.open(indexDir);
        Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_47);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, luceneAnalyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter fswriter=new IndexWriter(fsdir, config);
        fswriter.addIndexes(new Directory[]{ramdir});//合并数据
        fswriter.close();
    }

    public String searchByKeyWords(String keyWords) throws IOException, ParseException {
        IndexReader reader = DirectoryReader.open(ramdir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_47);
        QueryParser parser = new QueryParser(Version.LUCENE_47, "question",luceneAnalyzer);
        Query query = parser.parse(keyWords);
        TopDocs results = searcher.search(query,2);
        ScoreDoc[] score = results.scoreDocs;
        if (score.length == 0) {
            return null;
        }else {
            Document doc = searcher.doc(score[0].doc);
            return doc.get("answer");
        }
    }
}
