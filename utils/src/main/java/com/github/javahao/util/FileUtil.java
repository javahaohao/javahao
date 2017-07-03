package com.github.javahao.util;

import cn.org.rapid_framework.util.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class FileUtil {
    private static final transient Log log = LogFactory.getLog(FileUtil.class);

	/**

	 * 判断是否为文件，如果path为null，则返回false

	 *

	 * @param path 文件路径

	 * @return 如果为文件true

	 */
	public static boolean isFile(String path) {
		return (path == null) ? false : new File(path).isDirectory();
	}

	/**

	 * 判断是否为文件，如果file为null，则返回false

	 *

	 * @param file 文件

	 * @return 如果为文件true

	 */
	public static boolean isFile(File file) {
		return (file == null) ? false : file.isDirectory();
	}
	/**
	 * 如果指定路径不存在，则创建
	 * @param path 路径
	 */
	public static void fileIsExists(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	private static List<File> fileList = new ArrayList<File>();
	/**
	 * 读取txt文件，返回读取之后的内容
	 * @param path 路径
	 * @return 读取结果
	 */
	public static List<String> readTxt(String path){
		List<String> result = new ArrayList<String>();
		BufferedReader bufferedReader =null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
			String lineTxt = null;
			while(ObjectUtils.isNotEmpty((lineTxt = bufferedReader.readLine()))){
				result.add(lineTxt);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	/**
	 * 将内容输出到文件
	 * @param path 路径
	 * @param value 输出值
	 * @throws Exception 输出异常
	 */
	public static void printTxtToFile(String path,String value) throws Exception{
		File file = new File(path);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
        Writer writer = new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8");
        writer.write(value);
        writer.close();
	}

	/**
	 * 遍历指定文件夹下的文件名并用关键字连接起来
	 * @param path 路径
	 * @param join 连接分割
	 * @return 返回结果
	 */
	public static String joinFilName(String path,String join){
		StringBuffer sb = new StringBuffer();
		File files = new File(path);
		String[] filess = files.list();
		//遍历要导入引入的包
		for (int i = 0; i < filess.length; i++) {
			sb.append(path);
			sb.append(filess[i]);
			sb.append(join);
		}
		return sb.toString();
	}
	/**
     * 获取工程路径
     * @return 获取项目路径
     */
    public static String getProjectPath(){
		String projectPath = "";
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(org.apache.commons.lang3.StringUtils.join(new Object[]{file.getPath() , File.separator , "src" , File.separator , "main"}));
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }

	/**
	 * 获取项目的编译路径
	 * @return 获取项目路径
	 */
	public static String getProjectClassPath(){
		try {
			return new DefaultResourceLoader().getResource("").getFile().getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除指定路径下面所有文件
	 * @param path 路径
	 * @throws Exception 删除异常
	 */
	public static void deleteFileByFolder(String path) throws Exception{
		File file = new File(path);
		deleteFileUtil(file);
		for(int i = fileList.size()-1 ; i >=0 ; i--){
			fileList.get(i).delete();
		}
	}
	/**
	 * 删除指定文件
	 * @param path 路径
	 * @throws Exception 删除异常
	 */
	public static void deleteFileByPath(String path) throws Exception{
		File file = new File(path);
		file.delete();
	}
	/**
	 * 删除文件辅助方法
	 * @param file 要删除的文件
	 * @throws Exception 删除异常
	 */
	private static void deleteFileUtil(File file)throws Exception{
		File[] childFile = file.listFiles();
		fileList.addAll(Arrays.asList(childFile));
		for(File f : childFile){
			if(f.isDirectory())
				deleteFileUtil(f);
		}
	}
	
	static BufferedInputStream bis = null;
	static BufferedOutputStream bos = null;
	/**
	 * 复制指定路径的路径文件到指定路径
	 * @param sourcePath 原路径
	 * @param toPath 目标路径
	 * @param fileName 文件名称
	 * @return 结果
	 */
	public static boolean copyFileToPath(String sourcePath,String toPath,String fileName){
		File inputFile  =  new File(sourcePath);
		File outPath = new File(toPath);
		File outFile  =  new File(toPath+fileName);
		if(!outPath.getParentFile().exists())
			outPath.getParentFile().mkdirs();
		byte[] b = new byte[1024];
		int len;
		try {
			bis = new BufferedInputStream(new FileInputStream(inputFile));
			bos = new BufferedOutputStream(new FileOutputStream(outFile));
			while((len = bis.read(b))!=-1){
				bos.write(b,0,len);
			}
			System.out.println("复制完成"+fileName+"！");
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到！请确定文件路径正确！");
			return false;
		} catch (IOException e) {
			System.out.println("复制文件失败！");
			return false;
		}
		finally{
			closeAll();
		}
		return true;
	}
	/**
	 * 关闭输入输出流
	 */
	private static void closeAll(){
		try {
			if(ObjectUtils.isNotEmpty(bis)&&ObjectUtils.isNotEmpty(bos)){
				bis.close();
				bos.close();
			}
		} catch (IOException e) {
			System.out.println("文件流关闭失败！");
		}
	}
	public static final String NETWORK="network";
	public static final String CLASSPATH="classpath";
	/**
	 * 读取properties文件
	 * @param path 读取路径
	 * @param readType 读取方式：NETWORK是通过http://..方式读取，LOCAL是通过本地读取
	 * @param readKey 要获得指定key对应的值
	 * @param defaultValue 如果key对应的值不存在获取的默认值
	 * @return 结果
	 */
	public static String readProperties(String path,String readType,String readKey,String defaultValue){
		Properties properties = readPropertiesUtil(path, readType);
		String result = properties.getProperty(readKey, defaultValue);
		return result;
	}
	/**
	 * 获取properties中的key,value的对应值都取出来
	 * @param path 路径
	 * @param readType 读取类型
	 * @return 结果
	 */
	public static Map<String, String> getPropertiesKeyValue(String path,String readType){
		Properties properties = readPropertiesUtil(path, readType);
		@SuppressWarnings("rawtypes")
		Enumeration propNames = properties.propertyNames();
		Map<String, String> keyValue = new HashMap<String, String>();
		while (propNames.hasMoreElements()) {
			String key = (String) propNames.nextElement();
			String value = properties.getProperty(key);
			keyValue.put(key, value);
		}
		return keyValue;
	}
	/**
	 * 读取properties辅助类
	 * @param path 路径
	 * @param readType  读取类型
	 * @return 结果
	 */
	public static Properties readPropertiesUtil(String path,String readType){
		Properties properties = new Properties();
		try {
			if(NETWORK.equals(readType)){
				URL url = new URL(path);
				properties.load(url.openStream());
			}else if(CLASSPATH.equals(readType)){
				InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
				properties.load(in);
			}else{
				properties.load(new FileInputStream(new File(path)));
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到！---------"+e);
		} catch (IOException e) {
			System.out.println("文件读取异常！---------"+e);
		}
		return properties;
	}
	/**
	 * 计算文件的MD5值
	 * @param file 检验文件
	 * @return 结果
	 */
	public static String getFileMD5(File file) {
	    if (!file.isFile()) {
	        return null; 
	    } 
	    MessageDigest digest = null; 
	    FileInputStream in = null; 
	    byte buffer[] = new byte[8192]; 
	    int len; 
	    try { 
	        digest =MessageDigest.getInstance("MD5"); 
	        in = new FileInputStream(file); 
	        while ((len = in.read(buffer)) != -1) { 
	            digest.update(buffer, 0, len); 
	        } 
	        BigInteger bigInt = new BigInteger(1, digest.digest()); 
	        return bigInt.toString(16); 
	    } catch (Exception e) { 
	        e.printStackTrace(); 
	        return null; 
	    } finally { 
	        try { 
	            in.close(); 
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	    } 
	}
	/**
	 *  计算文件的 SHA-1 值 
	 * @param file 校验文件
	 * @return 结果
	 */
	public static String getFileSha1(File file) { 
	    if (!file.isFile()) { 
	        return null; 
	    } 
	    MessageDigest digest = null; 
	    FileInputStream in = null; 
	    byte buffer[] = new byte[8192]; 
	    int len; 
	    try { 
	        digest =MessageDigest.getInstance("SHA-1"); 
	        in = new FileInputStream(file); 
	        while ((len = in.read(buffer)) != -1) { 
	            digest.update(buffer, 0, len); 
	        } 
	        BigInteger bigInt = new BigInteger(1, digest.digest()); 
	        return bigInt.toString(16); 
	    } catch (Exception e) { 
	        e.printStackTrace(); 
	        return null; 
	    } finally { 
	        try { 
	            in.close(); 
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	    } 
	}
	/**
     * MD5签名
     * @param content   要签名的内容
     * @return 结果
     */
	public static String md5(String content){
        StringBuffer sb = new StringBuffer();
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(content.getBytes("UTF-8"));
            byte[] tmpFolder = md5.digest();

            for(int i = 0; i < tmpFolder.length; i++){
                sb.append(Integer.toString((tmpFolder[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        }catch(NoSuchAlgorithmException ex){
            log.error("无法生成文件的MD5签名", ex);
            return null;
        }catch(UnsupportedEncodingException ex){
            log.error("无法生成文件的MD5签名", ex);
            return null;
        }
    }
}
