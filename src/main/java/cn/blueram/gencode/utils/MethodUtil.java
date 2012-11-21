package cn.blueram.gencode.utils;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.blueram.gencode.json.JSONUtil;
import cn.blueram.gencode.log4j.Log4j_Properties;
public class MethodUtil{
	/**
	 * 初始化
	 * @return
	 */
	public static MethodUtil getInit(){
		return new MethodUtil();
	}
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
/*
 *  MD5 加密  
 * */
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}
	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}
	
	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author <a href="mailto:leo841001@163.com">LiGuoQing</a >
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
	/**
	 * DES加密解密
	 * @param strkey	密钥
	 * @param message	内容
	 * @param type	类型0为加密，1为解密
	 * @return String or null
	 */
	public String DES(String deskey,String str,int type){
		Cipher encryptCipher = null;
		Cipher decryptCipher = null;
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try{
			Key key = getKey(deskey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
			if(type==0){	//0为加密
				return byteArr2HexStr( encryptCipher.doFinal(str.getBytes()));
			}else {
				return new String(decryptCipher.doFinal(hexStr2ByteArr(str))); 
			}
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * MD5加密UTF-8  小写
	 * @param str
	 * @return String 
	 */
	public String getMD5UTF8(String str){
		String md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(str.getBytes("utf-8"));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
			md5 = sb.toString().toLowerCase();
		} catch (Exception e) {
		}
		return md5;
	}
	/**
	 * 	MD5加密GBK
	 * @param message
	 * @return
	 */
	public String getMD5GBK(String message){
		String md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(message.getBytes("GBK"));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
			md5 = sb.toString().toLowerCase();
		} catch (Exception e) {
		}
		return md5;
	}
	/**
	 * 获得随机数（纯数字）
	 * @param num
	 * @return
	 */
	public  String getRandomNumber(int num){
		int codeCount = num ;	
		String str = "" ;
		char[] codeSequence={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random() ;
		for(int i=0;i<codeCount;i++){
			str =  str+codeSequence[random.nextInt(10)] ;
		}
		return str ;
	}
	/**
	 * 拿 id 比如 xxxx-xxxx-xxxx-xxxx
	 * @return
	 */
	public String getRandom(){
		String Random="";
		int j=4;
		for(int i=0;i<4;i++){
			Random+=this.getRandomNumberAndLetter(j);
			Random+="-";
		}
		String rand=Random.substring(0,Random.lastIndexOf("-"));
		return rand;
	}
	/**
	 * 获得随机数（数字加字母）
	 * @param num
	 * @return
	 */
	public String getRandomNumberAndLetter(int num){
		int codeCount = num ;	//随机数的位数
		String str = "" ;
		char[] codeSequence={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				   'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				   'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 
				   'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		Random random = new Random() ;
		for(int i=0;i<codeCount;i++){
			str =  str+codeSequence[random.nextInt(62)] ;
		}
		return str ;
	}
	/**
	 * 获取日期
	 * @param type
	 * @return
	 */
	public String getDate(String type){
		Date d = new Date();
//		String str = d.toString();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd  kk:mm:ss ");//其中yyyy-MM-dd是你要表示的格式
		SimpleDateFormat sdf=new SimpleDateFormat(type);
//		可以任意组合，不限个数和次序；具体表示为：MM-month,dd-day,yyyy-year;kk-hour,mm-minute,ss-second;
		String str=sdf.format(d);
		return str;
	}
	/**
	 * 生成订单（时间+4位随机数）
	 * @param str
	 * @return true表示是数字，false表示不是数字
	 */
	public String getOrderID(){
		return getDate("yyyyMMddHHmmss")+getRandomNumber(4) ;
	}
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 */ 
	public  String getDateTimeyyyyMMddHHmmss() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * @return String
	 */ 
	public String getDateTime(){
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = outFormat.format(now);
		return s;
	}
	public String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(date);
		return strDate;
	}
	/**
	 * 
	 * @function:
	 * @return
	 * @author wolf
	 * @QQ 405645010
	 * @date:2011-11-2 下午02:23:09
	 * @version :2.0
	 * @description :yyyyMMhh 年月日
	 */
	public String getDateyyyyMMdd(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	/**
	 * 获取unix时间，从1970-01-01 00:00:00开始的秒数
	 * @param date
	 * @return long
	 */
	public static long getUnixTime(Date date) {
		if( null == date ) {
			return 0;
		}
		
		return date.getTime()/1000;
	}
	/**
	 * 判断是否为数字
	 * @param str
	 * @return true表示是数字，false表示不是数字
	 */
	public boolean getIsNumber(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
    	Matcher isNum = pattern.matcher(str);
    	if(str.length()==0||str==null){	//判断传进去的数字是否为空
    		return false ;
    	}
    	if(isNum.matches()){
    		return true ;
    	}else{
    		return false ;
    	}
	}
	/**
	 * 获取编码字符集
	 * @param request
	 * @param response
	 * @return String
	 */
	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {
		
		if(null == request || null == response) {
			return "gbk";
		}
		
		String enc = request.getCharacterEncoding();
		if(null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}
		
		if(null == enc || "".equals(enc)) {
			enc = "gbk";
		}
		
		return enc;
	}
	/**
	 * 返回 InputStream 流
	 * @param PropertiesName
	 * @return
	 */
   public InputStream getProperties(String PropertiesName){
	   InputStream is=this.getClass().getClassLoader().getResourceAsStream("conf/"+PropertiesName);
       try{
    	   if(is==null){
    		   Log4j_Properties.getLogClass(this.getClass()).warn("获取配置文件失败，PropertiesName"+PropertiesName);
    	   }
       }catch(Exception e){
    	   Log4j_Properties.getLogClass(this.getClass()).error("异常PropertiesName="+PropertiesName);
       }
       return is;
   }


  /**
   *  时间验证 
   * @param current_time 当前时间
   * @param compare_time 比较时间
   * @return time 为多少秒 ,60秒为一分钟
   */
   public long getDate_time_Validate(String current_time,String compare_time){
	        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        long time=0;
	        try{
			    Date c_tiem=sf.parse(current_time);
				Date com_time=sf.parse(compare_time);
					long l = c_tiem.getTime() - com_time.getTime() > 0 ? c_tiem.getTime()
				              - com_time.getTime() : com_time.getTime() - c_tiem.getTime();
				 time=l / 1000;  // 算出超时秒数
	        }catch(Exception e){
	        	Log4j_Properties.getLogClass(this.getClass()).error("时间计算异常e="+e.toString()+",current_time="+current_time+",compare_time="+compare_time);
	        }
			return time;
   }

   /**
    * 输出json
    * @param Object
    * @param response
    */
   public void JsonOutMsg(Object obj,HttpServletResponse response){
   	try {
   		   response.setContentType("text/json;charset=UTF-8");
			PrintWriter out=response.getWriter();
			String result=JSONUtil.toJSONString(obj);
			System.out.println("json="+result);
			out.write(result);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
   	
   }

   /**
    * 
    * @param obj    对象
    * @param response  
    * @param ContentType 规则 默认为  text/html;charset=UTF-8
    * @param outType  规则  json or null
    */
   public void OutMsg(Object obj,HttpServletResponse response,String ContentType,String outType){
	   PrintWriter out=null;
	   try {
   		    if(null==ContentType){
   		    	ContentType="text/html;charset=UTF-8";
   		    }
   		    response.setContentType(ContentType);
   		    out=response.getWriter();
			String result=obj.toString();
			if(null!=outType && outType.equals("json")){
				result=JSONUtil.toJSONString(obj);
			}
			System.out.println("OutMsg="+result);
			out.write(result);
		} catch (Exception e) {
			  e.printStackTrace();
		}finally{
			 out.close();
		}
   	
   }
   /**
    * 处理时间的加减 运算
    * @param dateSource  时间格式为  yyyy-MM-dd HH:mm:ss
    * @param addLong     60*60 为一个小时  60*60*24 为一天
    * @param args        0为加 1为减
    * @return
    */
   public String getDateAdd(String dateSource,long addLong,int args){
	   String returnDate=null;
	   try{
	      Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateSource);
	      Long time=null;
	      switch(args){
	        case 0:
	        	 time=(date.getTime()/1000)+addLong; break;
	        case 1:
	        	 time=(date.getTime()/1000)-addLong; break;
	        default:
	        	 time=(date.getTime()/1000)+addLong; break;
	      }
          date.setTime(time*1000);
          returnDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	   }catch(Exception e){
		  e.printStackTrace();
		  return returnDate;
	   }
	   return returnDate;
   }
   /**
    * 处理时间的加减 运算
    * @param dateSource  时间格式为  yyyy-MM-dd HH:mm:ss
    * @param addLong     60*60 为一个小时  60*60*24 为一天
    * @param args        0为加 1为减
    * @return
    */
   public long getDateAdd(String startTime,String endTime,int args){
	    long time=0l;
	   try{
		  if(startTime.indexOf(".")>0){
			  startTime=startTime.substring(0, startTime.indexOf("."));
		  }
		  if(endTime.indexOf(".")>0){
			  endTime=endTime.substring(0, endTime.indexOf("."));
		  }
	      Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
	      Date addLong=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
	      switch(args){
	        case 0:
	        	 time=(date.getTime()/1000)+(addLong.getTime()/1000); break;
	        case 1:
	        	 time=(date.getTime()/1000)-(addLong.getTime()/1000); break;
	        default:
	        	 time=(date.getTime()/1000)+(addLong.getTime()/1000); break;
	      }
          date.setTime(time*1000);
          //returnDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
          time=date.getTime();
	   }catch(Exception e){
		  e.printStackTrace();
	   }
	   return time;
   }
   /**
    * 返回最大day
    * @param time 时间
    * @return
    * obj[0]=maxMonth;
	* obj[1]=time;
    */
   public Object[] getMaxMonth(String time){
	     Object[] obj=new Object[2];
	     Date date=null;
		 try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		  } catch (ParseException e) {
			e.printStackTrace();
		  }
		  Calendar a=Calendar.getInstance();
		  a.setTime(date);
		  a.set(Calendar.DATE, 1); //把日期设置为当月第一天
		  a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		  int maxMonth=a.get(Calendar.DATE);
		  a.roll(Calendar.DATE, 1);
		 // System.out.println("该月最大天数:"+maxMonth+",某月="+a.get(Calendar.MONTH));
		  time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(a.getTime());
		 // System.out.println("time="+time);
		  obj[0]=maxMonth;
		  obj[1]=time;
		  return obj;
   }
   /**
    * 
    * <br>
    * <b>功能：</b>uid<br>
    * <b>作者：</b>肖财高<br>
    * <b>日期：</b> 2012-2-13 <br>
    * @return 12位日期加7为随机整数
    */
   public String getUid(){
	   return new SimpleDateFormat("yyMMddHHmmss").format(new Date())+getRandomNumber(7);
   }
   public long getLongId(){
	   return Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+getRandomNumber(5));
   }
   public static void main(String[] args){
		MethodUtil util=new MethodUtil();
		System.out.println(util.getUid());
		/*try{
			String sign=util.DES("123456789", "26930250", 0);
			System.out.println("des加密:"+sign);
			String sign1=util.DES("123456789", sign, 1);
			System.out.println("des解密:"+sign1);
		}catch(Exception e){
			System.out.println("--");
		}*/
	}
}
