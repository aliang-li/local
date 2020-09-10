package label.dcm;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.List;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import label.bean.dcmSize;



public class dcmSepBySize {
	
	/**
	 * 传入二级目录的路径，扫描路径下的全部dcm文件，按照dcm大小，将所有dcm文件分类并封装成dcmSize对象，
	 * 并将对象存到list列表中
	 * @param path
	 * @throws Exception
	 */
	public static void dcmSep(String path) throws Exception{
		
		List<dcmSize> list = new ArrayList<dcmSize>(); 
		//file相当于是第二级目录，下一级是dcm文件
		File file = new File(path);
		for (File file1 : file.listFiles()) {
			//获取文件的绝对路径
			String absolutePath = file1.getAbsolutePath();
			//System.out.println("absolutePath::"+absolutePath);
			//获取文件的名字
			//String name = file1.getName();
			//System.out.println("name::"+name);
			//调用其他类对dcm文件进行解析
			File dcmfile = new File(absolutePath);
	        DisplayTag d = new DisplayTag(dcmfile);
	        Attributes attrs = d.loadDicomObject(dcmfile);
	        //获取行
	        int row = attrs.getInt(Tag.Rows, 1);
	        //获取列
	        int columns = attrs.getInt(Tag.Columns, 1);
	        //System.out.println("" + "row=" + row + ",columns=" + row + ",row*columns = " + row * columns);
	        
	        //list中没有任何一个对象时
	        if(list.size() == 0){
	        	dcmSize dcmSize = new dcmSize();
	        	dcmSize.setRow(row);
	        	dcmSize.setColumns(columns);
	        	dcmSize.setSize(row*columns);
	        	List<String> urls = dcmSize.getUrlsList();
	        	if(urls == null) {
	        		List<String> lis = new ArrayList<>(); 
	        		lis.add(absolutePath);
	        		dcmSize.setUrlsList(lis);
	        	}else {
	        		urls.add(absolutePath);
	        		dcmSize.setUrlsList(urls);
	        	}
	        	list.add(dcmSize);
	        	//System.out.println(list);
	        }else {
	        	//list有对象时，并且根据dcm大小找到list中相应的dcmSize对象
	        	int flag=0;
	        	for(int i=0;i<list.size();i++) {
	        		dcmSize dcmSize1 =list.get(i);
	        		if(dcmSize1.getRow()==row && dcmSize1.getColumns()==columns) {
	        			flag=1;
	        			list.remove(i);
	        			//System.out.println("长度："+list.size());
	        			List<String> urls2 = dcmSize1.getUrlsList();
	        			
	        			urls2.add(absolutePath);
	        			dcmSize1.setUrlsList(urls2);
	        			System.out.println("url长度："+dcmSize1.getUrlsList().size());
	        			list.add(dcmSize1);
	        			//System.out.println(list);
	        			//System.out.println("添加后长度："+list.size());
	        			break;
	        		}
	        	}
	        	if(flag == 0) {
	        		//list中有dcmSize对象但找不到大小相对应的dcmSize对象时，需要新建一个dcmSize对象
	        		dcmSize dcmSize2 = new dcmSize();
		        	dcmSize2.setRow(row);
		        	dcmSize2.setColumns(columns);
		        	dcmSize2.setSize(row*columns);
		        	List<String> lis = new ArrayList<>(); 
	        		lis.add(absolutePath);
	        		dcmSize2.setUrlsList(lis);
	        		list.add(dcmSize2);
	        		System.out.println("第三种"+list);
        			//System.out.println("第三种添加后长度："+list.size());
	        	}
	        }  
		}//扫描一个文件夹结束，将不同大小的dcm以dcmSize对象为容器类别 封装到list列表中
		
		//寻找list列表中dcmSize.urlsList中的最大值，并将其在list中删除
		int maxIndex = 0;
		int max = list.get(0).getUrlsList().size();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getUrlsList().size() > max) {
				maxIndex=i;
			}
		}
		list.remove(maxIndex);
		//System.out.println("删除后的"+list);
		//System.out.println("删除后的"+list.size());
		//获取到path的上级目录名
	/*	int indexOf = path.lastIndexOf("\\");//window写法
		String parentPath = path.substring(0, indexOf);
		String filename = path.substring(indexOf+1);*/
		//System.out.println(parentPath);
		//System.out.println(filename);
		
		//对删除后的list进行扫描，list中的每一个dcmSize对象都要在实际中建立一个文件夹，将dcmSize对象中包含的相同大小的dcm文件存进去
		for(int i=0;i<list.size();i++) {
			String mkpath = path+"_"+list.get(i).getRow()+"X"+list.get(i).getColumns();
			System.out.println(mkpath);
			File mkpathfile = new File(mkpath);
			if(!mkpathfile.exists()) {
				mkpathfile.mkdir();
			}
			
			dcmSize dcmSize = list.get(i);
			List<String> urlsList = dcmSize.getUrlsList();
			for(int j=0;j<urlsList.size();j++) {
				copyFile(urlsList.get(j),mkpath);
				String delPath = urlsList.get(j);
				File file3 = new File(delPath);
				//System.out.println(file3.exists()+"---"+file3.getAbsolutePath());
				boolean delete = file3.delete();
				//System.out.println("删除？"+delete);

			}
			
		}
		
	}
	public static void copyFile(String srcPathStr, String desPathDirStr) {

        //1.获取源文件的名称
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\")+1); //源文件地址  window
        //String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("/")+1); //源文件地址  linux
      
        desPathDirStr = desPathDirStr + File.separator + newFileName; //目标文件地址
        //System.out.println("目的路径："+desPathDirStr);

        try{
            //2.创建输入输出流对象
            FileInputStream fis = new FileInputStream(srcPathStr);
      
            FileOutputStream fos = new FileOutputStream(desPathDirStr);

            //创建搬运工具
            byte datas[] = new byte[1024*8];
            //创建长度
            int len = 0;
            //循环读取数据
            while((len = fis.read(datas))!=-1){
                fos.write(datas,0,len);
            }
            //3.释放资源
            fos.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        //return newFileName;
    }
	
	
}
