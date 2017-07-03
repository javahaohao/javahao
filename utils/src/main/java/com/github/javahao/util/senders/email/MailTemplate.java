package com.github.javahao.util.senders.email;

import com.github.javahao.util.FileUtil;
import com.github.javahao.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * usedfor：邮件发送模板
 * Created by javahao on 2017/6/26.
 * auth：JavaHao
 */
public class MailTemplate {

    private String templateBody;
    private List<String> ccMails = new ArrayList<String>();
    private List<String> toMails = new ArrayList<String>();
    private List<String> fileList = new ArrayList<String>();

    public MailTemplate() {
    }

    public MailTemplate(String templetPath, String toMail, String ccMail) throws IOException {

        loadTemplet(templetPath);

        if(PatternKit.isEmail(toMail)){
            this.toMails.add(toMail);
        }

        if(PatternKit.isEmail(ccMail)){
            this.ccMails.add(ccMail);
        }
    }

    /**
     * 添加附件
     * @param filePath 附件路径
     * @return 结果
     */
    public MailTemplate addFile(String filePath){
        if(FileUtil.isFile(filePath)){
            this.fileList.add(filePath);
        }
        return this;
    }

    /**
     * 添加附件列表
     * @param files 附件
     * @return 模板
     */
    public MailTemplate addFiles(List<String> files){
        if(null != files && files.size() > 0){
            this.fileList.addAll(files);
        }
        return this;
    }

    /**
     * 发送给谁
     * @param toMails 发送人
     * @return 结果
     */
    public MailTemplate toMail(String ... toMails){
        if(null != toMails && toMails.length > 0){
            for(String toMail : toMails){
                if(PatternKit.isEmail(toMail)){
                    this.toMails.add(toMail);
                }
            }
        }
        return this;
    }

    /**
     * 抄送人
     * @param ccMails 抄送人
     * @return 结果
     */
    public MailTemplate ccMail(String... ccMails){
        if(null != ccMails && ccMails.length > 0){
            for(String ccMail : ccMails){
                if(PatternKit.isEmail(ccMail)){
                    this.ccMails.add(ccMail);
                }
            }
        }
        return this;
    }

    /**
     * 加载模板
     * @param templetPath 模板路径
     * @return 结果
     * @throws IOException 异常
     */
    public MailTemplate loadTemplet(String templetPath) throws IOException {
        InputStream input = null;
        InputStreamReader read = null;
        BufferedReader reader = null;

        if (!new File(templetPath).exists()) {
            templateBody = "";
        }
        try {
            input = new FileInputStream(templetPath);
            read = new InputStreamReader(input, "UTF-8");
            reader = new BufferedReader(read);
            String line;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line + "\n";
            }
            templateBody = result.substring(result.indexOf("<html>"));
        } catch (Exception e) {
            e.printStackTrace();
            templateBody = "";
        } finally {
            reader.close();
            read.close();
            input.close();
        }

        return this;
    }

    @Override
    public String toString() {
        return this.templateBody;
    }

    /**
     * 获取发送人
     * @return 结果
     */
    public String getToMail() {
        if(null != toMails && toMails.size() > 0){
            StringUtils.join(toMails, ",").substring(1);
        }
        return null;
    }

    public String getCcMail() {
        if(null != ccMails && ccMails.size() > 0){
            StringUtils.join(ccMails, ",").substring(1);
        }
        return null;
    }

    /**
     * 获取附件
     * @return 结果
     */
    public List<String> getFileList() {
        return fileList;
    }

}