/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.service;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cms.dao.ArticleDao;
import com.thinkgem.jeesite.modules.cms.dao.ArticleDataDao;
import com.thinkgem.jeesite.modules.cms.dao.CategoryDao;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.ArticleData;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 文章Service
 *
 * @author zsl
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao, Article> {

    @Autowired
    private ArticleDataDao articleDataDao;
    @Autowired
    private CategoryDao categoryDao;

    @Transactional(readOnly = false)
    public Page<Article> findPage(Page<Article> page, Article article, boolean isDataScopeFilter) {
        // 更新过期的权重，间隔为“6”个小时
        Date updateExpiredWeightDate = (Date) CacheUtils.get("updateExpiredWeightDateByArticle");
        if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null
                && updateExpiredWeightDate.getTime() < new Date().getTime())) {
            dao.updateExpiredWeight(article);
            CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
        }
        if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId()) && !Category.isRoot(article.getCategory().getId())) {
            Category category = categoryDao.get(article.getCategory().getId());
            if (category == null) {
                category = new Category();
            }
            category.setParentIds(category.getId());
            category.setSite(category.getSite());
            article.setCategory(category);
        } else {
            article.setCategory(new Category());
            if(Global.YES.equals(article.getRemarks())){
                return super.findPage(page, article);
            }
            return super.findArticlePage(page, article);
        }
        return super.findPage(page, article);
    }

    @Transactional(readOnly = false)
    public Page<Article> searchArticle(Page<Article> page, Article article) {
        return super.searchArticlePage(page, article);
    }

    @Transactional(readOnly = false)
    public Page<Article> findArticlePage(Page<Article> page, Article article, boolean isDataScopeFilter) {
        // 更新过期的权重，间隔为“6”个小时
        Date updateExpiredWeightDate = (Date) CacheUtils.get("updateExpiredWeightDateByArticle");
        if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null
                && updateExpiredWeightDate.getTime() < new Date().getTime())) {
            dao.updateExpiredWeight(article);
            CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
        }
        if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId()) && !Category.isRoot(article.getCategory().getId())) {
            Category category = categoryDao.get(article.getCategory().getId());
            if (category == null) {
                category = new Category();
            }
            category.setParentIds(category.getId());
            category.setSite(category.getSite());
            article.setCategory(category);
        } else {
            article.setCategory(new Category());
        }
        return super.findArticlePage(page, article);

    }

    @Transactional(readOnly = false)
    public void save(Article article) {
        if (Objects.nonNull(article.getArticleData()) && article.getArticleData().getContent() != null) {
            article.getArticleData().setContent(StringEscapeUtils.unescapeHtml4(
                    article.getArticleData().getContent()));
        }
        // 如果没有审核权限，则将当前内容改为待审核状态
        if (!UserUtils.getSubject().isPermitted("cms:article:audit")) {
            article.setDelFlag(Article.DEL_FLAG_AUDIT);
        }
        // 如果栏目不需要审核，则将该内容设为发布状态
        if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId())) {
            Category category = categoryDao.get(article.getCategory().getId());
            if (!Global.YES.equals(category.getIsAudit())) {
                article.setDelFlag(Article.DEL_FLAG_NORMAL);
            }
        }
        article.setUpdateBy(UserUtils.getUser());
        article.setUpdateDate(new Date());
        if (StringUtils.isNotBlank(article.getViewConfig())) {
            article.setViewConfig(StringEscapeUtils.unescapeHtml4(article.getViewConfig()));
        }

        ArticleData articleData = new ArticleData();
        ;
        if (StringUtils.isBlank(article.getId())) {
            article.preInsert();
            articleData = article.getArticleData();
            articleData.setId(article.getId());
            dao.insert(article);
            articleDataDao.insert(articleData);
        } else {
            article.preUpdate();
            articleData = article.getArticleData();
            articleData.setId(article.getId());
            dao.update(article);
            articleDataDao.update(article.getArticleData());


        }
    }

    @Transactional(readOnly = false)
    public void delete(Article article, Boolean isRe) {
//		dao.updateDelFlag(id, isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
        // 使用下面方法，以便更新索引。
        //Article article = dao.get(article.getId());
        article.setDelFlag(isRe ? Article.DEL_FLAG_DELETE : Article.DEL_FLAG_NORMAL);
        //dao.insert(article);
        String promulgator1Id = null;
        if(Objects.nonNull(article.getPromulgator1())){
            promulgator1Id = article.getPromulgator1().getId();
        }
        dao.newDelete(article.getDelFlag(), article.getId(),promulgator1Id);
    }

    /**
     * 通过编号获取内容标题
     *
     * @return new Object[]{栏目Id,文章Id,文章标题}
     */
    public List<Object[]> findByIds(String ids) {
        if (ids == null) {
            return new ArrayList<Object[]>();
        }
        List<Object[]> list = Lists.newArrayList();
        String[] idss = StringUtils.split(ids, ",");
        Article e = null;
        for (int i = 0; (idss.length - i) > 0; i++) {
            e = dao.get(idss[i]);
            list.add(new Object[]{e.getCategory().getId(), e.getId(), StringUtils.abbr(e.getTitle(), 50)});
        }
        return list;
    }

    /**
     * 点击数加一
     */
    @Transactional(readOnly = false)
    public void updateHitsAddOne(String id) {
        dao.updateHitsAddOne(id);
    }

    /**
     * 更新索引
     */
    public void createIndex() {
        //dao.createIndex();
    }

    /**
     * 全文检索
     */
    //FIXME 暂不提供检索功能
    public Page<Article> search(Page<Article> page, String q, String categoryId, String beginDate, String endDate) {

        // 设置查询条件
//		BooleanQuery query = dao.getFullTextQuery(q, "title","keywords","description","articleData.content");
//		
//		// 设置过滤条件
//		List<BooleanClause> bcList = Lists.newArrayList();
//
//		bcList.add(new BooleanClause(new TermQuery(new Term(Article.FIELD_DEL_FLAG, Article.DEL_FLAG_NORMAL)), Occur.MUST));
//		if (StringUtils.isNotBlank(categoryId)){
//			bcList.add(new BooleanClause(new TermQuery(new Term("category.ids", categoryId)), Occur.MUST));
//		}
//		
//		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {   
//			bcList.add(new BooleanClause(new TermRangeQuery("updateDate", beginDate.replaceAll("-", ""),
//					endDate.replaceAll("-", ""), true, true), Occur.MUST));
//		}   

        //BooleanQuery queryFilter = dao.getFullTextQuery((BooleanClause[])bcList.toArray(new BooleanClause[bcList.size()]));

//		System.out.println(queryFilter);

        // 设置排序（默认相识度排序）
        //FIXME 暂时不提供lucene检索
        //Sort sort = null;//new Sort(new SortField("updateDate", SortField.DOC, true));
        // 全文检索
        //dao.search(page, query, queryFilter, sort);
        // 关键字高亮
        //dao.keywordsHighlight(query, page.getList(), 30, "title");
        //dao.keywordsHighlight(query, page.getList(), 130, "description","articleData.content");

        return page;
    }

    /**
     * 查找标题列表
     */
    public List<String> findTitle(Article article) {
        return dao.findTitle(article);
    }

    public List<Article> findHostAuthors(Article article) {
        return dao.findHostAuthors(article);
    }

    public List<Article> findHostKeywords(Article article) {
        return dao.findHostKeywords(article);
    }

    public List<Article> findByCategoryIdIn(List<String> ids) {
        return dao.findByCategoryIdIn(ids);
    }

    public List<Article> findByCreateUser(List<Article> list ,String userId) {
        List<Article> newList = Lists.newArrayList();
        for (Article a : list ) {
            if(userId.equals(a.getCreateBy().getId())){
                newList.add(a);
            }
        }
        return newList;
    }

    public List<Article> findByCategoryIdInAndPageNum(List<String> ids,String pageNum) {
        return dao.findByCategoryIdInAndPageNum(ids,pageNum);
    }

    public List<Article> findArticles() {
        return dao.findArticles();
    }

    /**
     * 查找拥有当前栏目数据
     *
     * @param categoryId
     * @return
     */
    public Integer findOwnNum(String categoryId, String createBy) {
        return dao.findOwnNum(categoryId, createBy);
    }

    /**
     * 统计一年12月份数据
     *
     * @param
     * @eturn
     */
    public HashMap<String, Object> findByYearCount() {
        return dao.findByYearCount();
    }

    /**
     *
     */
    public List<Article> findHostPosts(Article article) {
        return dao.findHostPosts(article);
    }

    /**
     * 增加点赞数
     */
    @Transactional(readOnly = false)
    public void updateLikeNum(Article article) {
        String likeNum = Integer.parseInt(article.getLikeNum()) + 1 + "";
        article.setLikeNum(likeNum);
        dao.updateLikeNum(article);
    }
    /**
     * 减少点赞数
     */
    @Transactional(readOnly = false)
    public void deleteLikeNum(Article article) {
        String likeNum = Integer.parseInt(article.getLikeNum()) - 1 + "";
        article.setLikeNum(likeNum);
        dao.updateLikeNum(article);
    }

    /**
     * 修改推荐顶状态
     */
    @Transactional(readOnly = false)
    public void updateIsRecommend(Article article) {
        dao.updateIsRecommend(article);
    }

    /**
     * 修改置顶状态
     */
    @Transactional(readOnly = false)
    public void updateIsTop(Article article) {
        dao.updateIsTop(article);
    }

    /**
     * 增加收藏数
     */
    @Transactional(readOnly = false)
    public void updateCollectNum(Article article) {
        String collectNum = Integer.parseInt(article.getCollectNum()) + 1 + "";
        article.setCollectNum(collectNum);
        dao.updateCollectNum(article);
    }
    /**
     * 减少收藏数
     */
    @Transactional(readOnly = false)
    public void deleteCollectNum(Article article) {
        String collectNum = Integer.parseInt(article.getCollectNum()) - 1 + "";
        article.setCollectNum(collectNum);
        dao.updateCollectNum(article);
    }

    public List<Article> listSort(List<Article> list) {
        Collections.sort(list, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                try {
                    if (o1.getCreateDate().getTime() > o2.getCreateDate().getTime()) {
                        return -1;
                    } else if (o1.getCreateDate().getTime() < o2.getCreateDate().getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return list;
    }

    @Transactional(readOnly = false)
    public void updataArticleCommentNum(Article article){
        dao.updataArticleCommentNum(article);
    }

    @Transactional(readOnly = false)
    public void updateArticleHits(Article article){
        dao.updateArticleHits(article);
    }

    public List<Article> searchArticle(Article article){
        return dao.searchArticle(article);
    }
}
