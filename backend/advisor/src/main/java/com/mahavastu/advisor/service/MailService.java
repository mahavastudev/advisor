package com.mahavastu.advisor.service;

public interface MailService
{
    public void sendQueryRegisteredMail(String subject, String content);

    void sendAdviceWithPdfMail(String subject, String content, String filePath, String to);
}
