package com.rapple.baas.bot;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.rapple.baas.bot.actor.BotActor;
import com.rapple.baas.bot.dto.QuestionAndAnswer;
import com.rapple.baas.bot.lucene.LuceneIndex;
import com.rapple.baas.common.actor.ActorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by libin on 14-11-28.
 */

@Component
public class BotBackendLauncher {
    @Autowired
    private LuceneIndex luceneIndex;
    public void start()  {

        List<QuestionAndAnswer> samples= new ArrayList<QuestionAndAnswer>();
        samples.add(new QuestionAndAnswer("你好","您好"));
        samples.add(new QuestionAndAnswer("吃饭了吗","吃过了，你呢？"));
        samples.add(new QuestionAndAnswer("在忙啥，忙吗 有空","忙着产品上线呢！"));
        samples.add(new QuestionAndAnswer("今天吃啥啊，餐厅","最近找到一个川菜馆很不错哦"));
        samples.add(new QuestionAndAnswer("吃红枣，香蕉，梨，水果","我还是觉得苹果好吃"));
        samples.add(new QuestionAndAnswer("玩 踢球 运动 跑步","每天坚持运动很好哦。晚上一起看球吧。"));
        samples.add(new QuestionAndAnswer("烦 开心 痛苦 无聊","要保持积极的心态，我们一起加油吧。"));
        samples.add(new QuestionAndAnswer("天气 下雨 晴天 下雪","天天阳光灿烂多好啊。"));
        samples.add(new QuestionAndAnswer("iphone android 小米 三星","我是铁杆果粉哦！"));
        samples.add(new QuestionAndAnswer("父母 姐妹 儿子 朋友","嗯，家庭和睦比啥都重要！多个朋友多条路。"));
        samples.add(new QuestionAndAnswer("买 便宜 贵 促销","我喜欢双十一扫货，快感大大的！"));
        samples.add(new QuestionAndAnswer("堵车 雾霾 交通 污染","少开车，多走路！"));

        try {
            luceneIndex.createIndex(samples);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
