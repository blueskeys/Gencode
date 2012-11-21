package cn.blueram.gencode.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;

import cn.blueram.gencode.utils.Constant;
import cn.blueram.gencode.utils.PathUtil;
import cn.blueram.gencode.utils.StringUtil;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>罗泽军<br>
 * <b>日期：</b> Dec 27, 2011 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class FunctionEL {
	
	/***路径工具方法 start ***/ 
    public static String basePath() throws UnsupportedEncodingException{
    	String str = Constant.SSI_WEBSITE_URL;
    	if(StringUtils.isNotBlank(str)){
               return URLDecoder.decode(str,"utf-8");
        }
        return"";
    }
    
    /**
     * 
     * <br>
     * <b>功能：</b>获取图片路径<br>
     * <b>作者：</b>罗泽军<br>
     * <b>日期：</b> Dec 28, 2011 <br>
     * @param picUrl 图片的相对路径 icon\2011122617242825368.jpg
     * @return 
     * @throws UnsupportedEncodingException
     */
    public static String pic(String picUrl) throws UnsupportedEncodingException{
        return PathUtil.pic(picUrl);
    }
    
    /**
     * 
     * <br>
     * <b>功能：</b>获取图片缩略图<br>
     * <b>作者：</b>罗泽军<br>
     * <b>日期：</b> Dec 28, 2011 <br>
     * @param picUrl
     * @param cropArea
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String minPic(String picUrl,String cropArea) throws UnsupportedEncodingException{
        return PathUtil.minPic(picUrl, cropArea);
    }
    
    
    /**
     * 
     * <br>
     * <b>功能：</b>根据分类编号，返回路径<br>
     * <b>作者：</b>罗泽军<br>
     * <b>日期：</b> Dec 28, 2011 <br>
     * @param typeId
     * @return 格式：http://www.yy606.com/type/${typeId}
     * @throws UnsupportedEncodingException
     */
    public static String typeUrl(Long typeId) throws UnsupportedEncodingException{
    	return  PathUtil.typeUrl(typeId);
    }
    
    /**
     * 
     * @param typeId 分类编号
     * @param pageId 页面id
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String listTypeUrl(Long typeId,Integer pageId) throws UnsupportedEncodingException{
    	return PathUtil.listTypeUrl(typeId, pageId);
    }
    
    /**
     * 获取页面URL
     * @param pageId 页面id
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String listUrl(Integer pageId) throws UnsupportedEncodingException{
    	return PathUtil.listUrl(pageId);
    }
    /***路径工具方法 end ***/
    
    /***文字处理 end ***/
    
    /**
     *@param  content 内容
     *@param  length 指定长度。 超出这个长度就截取字符串。
     *@param  padding 超出长度后，尾加上字符，如"..."，可以为空
     *@return 返回结果 如果内容没有超出指定的长度。则返回原字符串，超出长度后则截取到指定的长度的内容
     */
    public static String sub(String content,Integer length,String padding) throws UnsupportedEncodingException{
    	return StringUtil.subStr(content, length, padding);
    }
    
    /**
     * 内容处理，如果将换行替换成<br>，以后还可以做字符敏感词过滤
     * @param content
     * @param length
     * @param padding
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String cont(String content) throws UnsupportedEncodingException{
    	if(StringUtils.isBlank(content)){
    		return content;
    	}
    	content = content.replaceAll("\r", "<br/>").replaceAll(" ", "&nbsp;");
//    	System.out.println(content);
    	return content;
    }
    
}
