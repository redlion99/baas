package com.rapple.baas.bot;

import com.rapple.baas.bot.lucene.LuceneIndex;
import com.rapple.baas.common.BotBackend;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by libin on 14-11-28.
 */
@Component
public class BotBackendImpl implements BotBackend {

    @Autowired
    private LuceneIndex luceneIndex;


    @Override
    public String ask(String question) {
        try {
            return luceneIndex.searchByKeyWords(question);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String welcomeMessage() {
        return "欢迎你哦，我是小红枣!";
    }
}
