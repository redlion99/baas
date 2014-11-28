package com.rapple.baas.bot.dto;

/**
 * Created by libin on 14-11-28.
 */
public class QuestionAndAnswer {
    private String question;
    private String answer;

    public QuestionAndAnswer() {
    }

    public QuestionAndAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
