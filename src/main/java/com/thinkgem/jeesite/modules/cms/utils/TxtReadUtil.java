package com.thinkgem.jeesite.modules.cms.utils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.CategoryService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zsl
 * @date
 */
public class TxtReadUtil {
    public static String getTxtContent(String id) throws Exception {
        File file = new File(MessageFormat.format("{0}{1}", Global.getConfig("configBasePath"),Global.getConfig("SpecialForm")));
        Set<String> set = null;
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
        try {
            //文件流是否存在
            if (file.isFile() && file.exists()) {
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                //读取文件，将文件内容放入到set中
                while ((txt = bufferedReader.readLine()) != null) {
                    set.add(txt);
                }
            }else{
                throw new RuntimeException("特殊栏目配置文件不存在");
            }
            for (String s:set) {
                if(s.contains(id)){
                    return s.split("#")[2];
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            read.close();     //关闭文件流
        }
        return null;
    }

    public static boolean isHaveParentId(Category category) throws Exception {
        File file = new File(MessageFormat.format("{0}{1}", Global.getConfig("configBasePath"),Global.getConfig("SpecialForm")));
        Set<String> set = null;
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
        try {
            //文件流是否存在
            if (file.isFile() && file.exists()) {
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                //读取文件，将文件内容放入到set中
                while ((txt = bufferedReader.readLine()) != null) {
                    set.add(txt);
                }
            }else{
                throw new RuntimeException("特殊栏目配置文件不存在");
            }
            for (String s:set) {

                if(Arrays.asList(s.split("#")).contains(category.getId())){
                    return true;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            read.close();     //关闭文件流
        }
        return false;
    }

    /**
     * 查询当前或者父ID是否存在模板里
     * @param id
     * @return
     * @throws Exception
     */
    public static String getFormContent(String id,CategoryService categoryService) throws Exception {
        Category category = categoryService.get(id);
        if(!TxtReadUtil.isHaveParentId(category)){
            String[] split = category.getParentIds().split(",");
            for (int i = 0; i < split.length; i++) {
                if(TxtReadUtil.isHaveParentId(new Category(split[i]))){
                    String txtContent = TxtReadUtil.getTxtContent(split[i]);
                    return txtContent != null ? txtContent : "modules/cms/articleForm";
                }
            }
        }
        return TxtReadUtil.getTxtContent(id);
    }



    public static void main(String[] args) {
       String id = "31291eaa342f4856a5c39f728e47e9d3";
        try{
            System.out.println(getTxtContent(id));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
