package com.github.javahao.util.senders.email;

import com.github.javahao.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * usedfor：邮件发送类
 * Created by javahao on 2017/6/26.
 * auth：JavaHao
 */
public class MailKit {

//  private static final Logger LOGGER = Logger.getLogger(MailKit.class);

    /**常见的邮件发送协议地址**/
    public static final String SMTP_QQ = "smtp.qq.com";
    public static final String SMTP_163 = "smtp.163.com";
    public static final String SMTP_126 = "smtp.126.com";
    public static final String SMTP_SINA = "smtp.sina.com";
    public static final String SMTP_GMAIL = "smtp.gmail.com";

    private static String CFG_SMTP = SMTP_QQ;
    private static String SEND_USER = "";
    private static String SEND_PASSWORD = "";

    /**
     * 配置邮件协议和账户
     * @param smtp          smtp协议
     * @param user          发送人邮件
     * @param password      发送人密码
     */
    public static void config(String smtp, String user, String password){
        if(StringUtils.isNotBlank(smtp)){
            CFG_SMTP = smtp;
        }
        if(StringUtils.isNotBlank(user)){
            SEND_USER = user;
        }
        if(StringUtils.isNotBlank(password)){
            SEND_PASSWORD = password;
        }
    }

    /**
     * 根据模板发送
     * @param mailTemplate 邮件模板
     * @param subject 标题
     * @return 结果
     */
    public static boolean send(MailTemplate mailTemplate, String subject){
        if(null != mailTemplate && StringUtils.isNotBlank(subject)){
            return sendProcess(CFG_SMTP, SEND_USER, SEND_PASSWORD,
                    mailTemplate.getToMail(), mailTemplate.getCcMail(),
                    subject, mailTemplate.toString(), mailTemplate.getFileList());
        }
        return false;
    }

    /**
     * 发送邮件
     * @param toMail        收件人地址
     * @param subject       发送主题
     * @param content       发送内容
     * @return              成功返回true，失败返回false
     */
    public static boolean send(String toMail, String subject, String content){
        return sendProcess(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, null, subject, content, null);
    }


    /**
     * 发送邮件并发送附件
     * @param toMail        收件人地址
     * @param subject       发送主题
     * @param content       发送内容
     * @param files         附件列表
     * @return              成功返回true，失败返回false
     */
    public static boolean send(String toMail, String subject, String content, String ...files){
        return sendProcess(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, null, subject, content, Arrays.asList(files));
    }

    /**
     * 发送邮件并发送附件
     * @param toMail        收件人地址
     * @param subject       发送主题
     * @param content       发送内容
     * @param files         附件列表
     * @return              成功返回true，失败返回false
     */
    public static boolean send(String toMail, String subject, String content, List<String> files){
        return sendProcess(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, null, subject, content, files);
    }

    /**
     * 发送并抄送
     * @param toMail        收件人地址
     * @param ccMail        抄送地址
     * @param subject       发送主题
     * @param content       发送内容
     * @return              成功返回true，失败返回false
     */
    public static boolean sendAndCc(String toMail, String ccMail, String subject, String content){
        return sendProcess(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, ccMail, subject, content, null);
    }

    /**
     * 发送邮件并抄送，带附件
     * @param toMail 发送给谁
     * @param ccMail 抄送
     * @param subject 主题
     * @param content 内容
     * @param files 附件
     * @return 返回
     */
    public static boolean sendAndCc(String toMail, String ccMail, String subject, String content, String ...files){
        return sendProcess(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, ccMail, subject, content, Arrays.asList(files));
    }

    /**
     * 发送邮件并抄送，带附件
     * @param toMail 发送给谁
     * @param ccMail 抄送
     * @param subject 主题
     * @param content 内容
     * @param files 附件
     * @return 返回
     */
    public static boolean sendAndCc(String toMail, String ccMail, String subject, String content, List<String> files){
        return sendProcess(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, ccMail, subject, content, files);
    }

    /**
     * 发送邮件
     * @param smtp        邮件协议
     * @param fromAddress 发送人地址
     * @param fromPass    发送人密码
     * @param toMailList   收件人地址
     * @param ccAdress    抄送人地址
     * @param subject     发送主题
     * @param content     发送内容
     * @param fileList    附件
     * @return  结果
     */
    public static boolean sendProcess(String smtp,String fromAddress,String fromPass,String toMailList,
                                      String ccAdress,String subject, String content,List<String> fileList){
        try{
            EmailHandle emailHandle = new EmailHandle(smtp);
            emailHandle.setFrom(fromAddress);
            emailHandle.setNeedAuth(true);
            emailHandle.setSubject(subject);
            emailHandle.setBody(content);
            emailHandle.setToList(toMailList);

            /**添加抄送**/
            if(StringUtils.isNotEmpty(ccAdress)){
                emailHandle.setCopyToList(ccAdress);
            }

            emailHandle.setFrom(fromAddress);
            emailHandle.setNamePass(fromAddress, fromPass);

            if(null != fileList && fileList.size() > 0){
                /** 附件文件路径 **/
                for(String file : fileList){
                    emailHandle.addFileAffix(file);
                }
            }
            return emailHandle.sendEmail();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }



    /*********************************************异步发送:S*******************************************/

    /**
     * 根据模板发送
     * @param mailTemplate 邮件模板
     * @param subject 主题
     */
    public static void asynSend(MailTemplate mailTemplate, String subject){
        if(null != mailTemplate && StringUtils.isNotBlank(subject)){
            asynSend(CFG_SMTP, SEND_USER, SEND_PASSWORD, mailTemplate.getToMail(), mailTemplate.getCcMail(),
                    subject, mailTemplate.toString(), mailTemplate.getFileList());
        }
    }

    /**
     * 异步发送邮件
     * @param toMail 发送人
     * @param subject 主题
     * @param content 内容
     */
    public static void asynSend(final String toMail, final String subject, final  String content){
        asynSend(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, null, subject, content, null);
    }

    /**
     * 异步发送并抄送
     * @param toMail 发送人
     * @param ccMail 抄送
     * @param subject 主题
     * @param content 内容
     */
    public static void asynSendAndCc(final String toMail, final String ccMail, final String subject, final String content){
        asynSend(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, ccMail, subject, content, null);
    }

    /**
     * 异步发送邮件并发送附件
     * @param toMail 发送人
     * @param subject 主题
     * @param content 内容
     * @param files 附件
     */
    public static void asynSend(final String toMail, final String subject, final  String content, final String ...files){
        asynSend(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, null, subject, content, Arrays.asList(files));
    }

    /**
     * 异步发送邮件并发送附件
     * @param toMail 发送人
     * @param subject 主题
     * @param content 内容
     * @param files 附件
     */
    public static void asynSend(final String toMail, final String subject, final  String content, final List<String> files){
        asynSend(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, null, subject, content, files);
    }


    /**
     * 异步发送邮件并抄送，带附件
     * @param toMail 发送人
     * @param ccMail 抄送
     * @param subject 主题
     * @param content 内容
     * @param files 附件
     */
    public static void asynSendAndCc(final String toMail, final String ccMail, final String subject, final String content, final String ...files){
        asynSend(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, ccMail, subject, content, Arrays.asList(files));
    }

    /**
     * 异步发送邮件并抄送，带附件
     * @param toMail 发送人
     * @param ccMail 抄送
     * @param subject 主题
     * @param content 内容
     * @param files 附件
     */
    public static void asynSendAndCc(final String toMail, final String ccMail, final String subject, final String content, final List<String> files){
        asynSend(CFG_SMTP, SEND_USER, SEND_PASSWORD, toMail, ccMail, subject, content, files);
    }

    /**
     * 发送邮件
     * @param smtp        邮件协议
     * @param fromAddress 发送人地址
     * @param fromPass    发送人密码
     * @param toAddress   收件人地址
     * @param ccAdress    抄送人地址
     * @param subject     发送主题
     * @param content     发送内容
     * @param fileList 附件
     * @return 结果
     */
    public static boolean asynSend(final String smtp,final String fromAddress,final String fromPass,final String toAddress,
                                   final String ccAdress,final String subject, final String content,final List<String> fileList){
        Boolean flag = Boolean.FALSE;
        FutureTask<Boolean> futureTask = null;
        ExecutorService excutorService = Executors.newCachedThreadPool();
        // 执行任务
        futureTask = new FutureTask<Boolean>(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                EmailHandle emailHandle = new EmailHandle(smtp);
                emailHandle.setFrom(fromAddress);
                emailHandle.setNeedAuth(true);
                emailHandle.setSubject(subject);
                emailHandle.setBody(content);
                emailHandle.setToList(toAddress);
                /**添加抄送**/
                if(StringUtils.isNotEmpty(ccAdress)){
                    emailHandle.setCopyToList(ccAdress);
                }
                emailHandle.setFrom(fromAddress);
                emailHandle.setNamePass(fromAddress, fromPass);
                if(null != fileList && fileList.size() > 0){
                    /** 附件文件路径 **/
                    for(String file : fileList){
                        emailHandle.addFileAffix(file);
                    }
                }
                return emailHandle.sendEmail();
            }
        });
        excutorService.submit(futureTask);

        try {
            // 任务没超时说明发送成功
            flag = futureTask.get(5L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            e.printStackTrace();
        } catch (ExecutionException e) {
            futureTask.cancel(true);
            e.printStackTrace();
        } catch (TimeoutException e) {
            futureTask.cancel(true);
            e.printStackTrace();
        } finally {
            excutorService.shutdown();
        }
        return flag;
    }
    /*********************************************异步发送:E*******************************************/

}