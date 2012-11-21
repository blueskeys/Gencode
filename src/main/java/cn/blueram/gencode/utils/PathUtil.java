package cn.blueram.gencode.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>罗泽军<br>
 * <b>日期：</b> Dec 23, 2011 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class PathUtil {
	
	private static Logger log =Logger.getLogger(PathUtil.class);
	
	/**
	 * 主站项目路径 http://www.yy606.com/
	 * @return
	 */
	public static String getBasePath(){
		String str = Constant.BASE_URL;
    	if(StringUtils.isNotBlank(str)){
            try {
				return URLDecoder.decode(str,"utf-8");
			} catch (UnsupportedEncodingException e) {
				log.error("获取根路径异常：",e);
			}
        }
		return str;
	}

	/**
	 * 主站项目路径 http://www.yy606.com/
	 * @return
	 */
	public static String getBasePath(String patth){
		
		return getBasePath()+patth;
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
    public static String typeUrl(Long typeId) {
    	return getBasePath()+"type/"+typeId;
    }
    
    
    public static String listUrl(Integer pageId){
    	if(pageId != null && pageId <1){
    		pageId = 1;
    	}
    	return getBasePath()+"list/"+pageId;
    }
    /**
     * 
     * @param typeId typeid
     * @param pageId 页面Id
     * @return
     */
    public static String listTypeUrl(Long typeId,Integer pageId){
    	String url = null;
    	if(typeId != null && typeId > 0 ){
    		url = typeUrl(typeId)+"/"+pageId;
    	}else{
    		url = listUrl(pageId);
    	}
    	return url;
    }
    
    
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>缩略图上传路径<br>
	 * <b>作者：</b>罗泽军<br>
	 * <b>日期：</b> Dec 25, 2011 <br>
	 * @param path  源路径 如 /upload/image/11111.jpg
	 * @param cropSize 规格 50_50
	 * @return 返回格式 /upload/image/11111.50_50.jpg 
	 */
	public static String getUploadThumbnailPath(String path,String cropArea){
		if(StringUtils.isBlank(path)){
			return "";
		}
		String postfix = path.substring(path.lastIndexOf("."), path.length());
		String temPath  = path.substring(0,path.lastIndexOf("."));
		String newName = "."+cropArea+postfix;
		String newPath = temPath+newName;
		//System.out.println(newPath);
		return newPath;
	}
	
	
	public static void main(String[] args) {
		//File file = new File("E:\\Soft\\办公软件\\通行证\\100.jpg");
		String file = "E:\\Soft\\办公软件\\通行证\\100.jpg";
		getUploadThumbnailPath(file,"50_50");
	}
	
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>获取图片根路径<br>
	 * <b>作者：</b>罗泽军<br>
	 * <b>日期：</b> Dec 23, 2011 <br>
	 * @param picId
	 * @return http://image.ssi.com/upload/image/
	 */
	public static String getImageBaseURL(){
		return Constant.SSI_IMAGE_SERVICE_URL;
	}
	
	
	/**
	 * 获取图片URL 加上域名
	 * @param picUrl 数据库picUrl字段内容，不带域名
	 * @return
	 */
	public static String pic(String picUrl){
		if(StringUtils.isNotBlank(picUrl)){
    		//将"\"替换成"/"
    		picUrl = picUrl.replaceAll("\\\\","/");
    		return PathUtil.getImageURL(picUrl);
		}
        return "";
	}
	
	/**
	 * 获取图片URL 加上域名
	 * @param picUrl 数据库picUrl字段内容，不带域名
	 * @param cropArea 图片尺寸 格式：50_50 具体规格请查看配置文件appkey.properties
	 * @return 
	 */
	
	 public static String minPic(String picUrl, String cropArea){
		if (StringUtils.isNotBlank(cropArea)) {
			picUrl = PathUtil.getUploadThumbnailPath(picUrl, cropArea);
		}
		return pic(picUrl);
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>图片路径<br>
	 * <b>作者：</b>罗泽军<br>
	 * <b>日期：</b> Dec 23, 2011 <br>
	 * 
	 * @param picId
	 * @return 返回图片路径 http://image.ssi.com/upload/image/photo/1111.jpg
	 */
	public static String getImageURL(String picUrl){
		if(picUrl.indexOf("http://")<0){
			return getImageBaseURL()+picUrl;
		}
		return picUrl;
	}
	

	/**
	 * 
	 * <br>
	 * <b>功能：</b>//微博访问地址<br>
	 * <b>作者：</b>罗泽军<br>
	 * <b>日期：</b> Dec 23, 2011 <br>
	 * @param id
	 * @return  返回路径 http://www.yy606.com/view/{id}.shtml
	 */
	public static String getWbUrl(Long id){
		return getBasePath()+"view/"+id+".shtml";
	}
	
	
	/**
	 * 头部页面的路径
	 * @return
	 */
	public static String getHeaderPath(){
		return "/header.shtml";
	}
	
	/**
	 * 头部页面的模版路径
	 * @return
	 */
	public static String getHeaderTempPath(){
		return "/header.shtml";
	}
	
	/**
	 * 底部页面的路径
	 * @return
	 */
	public static String getFooterPath(){
		return "/footer.shtml";
	}
	
	/**
	 * 底部页面的模板路径
	 * @return
	 */
	public static String getFooterTempPath(){
		return "/footer.shtml";
	}
	
	
	/**
	 * 底部页面的路径
	 * @return
	 */
	public static String getIndexPath(){
		return "/index.shtml";
	}
	
	/**
	 * 底部页面的模板路径
	 * @return
	 */
	public static String getIndexTempPath(){
		return "/index.shtml";
	} 

	
	
}
