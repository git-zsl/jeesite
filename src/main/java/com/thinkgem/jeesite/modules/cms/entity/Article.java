/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.modules.articleclassify.entity.CmsArticleClassify;
import com.thinkgem.jeesite.modules.classifying.entity.CmsClassifying;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;

/**
 * 文章Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Article extends DataEntity<Article> {

    public static final String DEFAULT_TEMPLATE = "frontViewArticle";
	
	private static final long serialVersionUID = 1L;
	private Category category;// 分类编号
	private String title;	// 标题
    private String link;	// 外部链接
	private String color;	// 标题颜色（red：红色；green：绿色；blue：蓝色；yellow：黄色；orange：橙色）
	private String image;	// 文章图片
	private String keywords;// 关键字
	private String description;// 描述、摘要
	private Integer weight;	// 权重，越大越靠前
	private Date weightDate;// 权重期限，超过期限，将weight设置为0
	private Integer hits;	// 点击数
	private String posid;	// 推荐位，多选（1：首页焦点图；2：栏目页文章推荐；）
    private String customContentView;	// 自定义内容视图
   	private String viewConfig;	// 视图参数
    private String url;
    private String isRecommend;  //是否推荐
    private String isTop;        //是否置顶
	private ArticleData articleData;	//文章副表
	private String author;            //作者
	private Date beginDate;	// 开始时间
	private Date endDate;	// 结束时间
	private String company;   // 公司
	private String phone;     //电话
	private String area;      //地区
	private String pay;       //薪资
	private String education;    //学历
	private String experience;    // 经验
	private String posts;    // 岗位id
	private String cPosts;    // 岗位名称
	private String jcity;     // 城市名称
	private CmsArticleClassify cmsArticleClassify;  //文章的分类
	private CmsClassifying classifying; //除文章外的分类
	private String brand;    // 品牌
	private String collectNum;  // 被收藏数量
	private String likeNum;    //点赞数
	private String pageNum;    // 第几页的广告
	private String isPutaway;   // 是否已上架
	private String isRead;   // 是否已上架

	private User user;
    
	public Article() {
		super();
		this.weight = 0;
		this.hits = 0;
		this.posid = "";
		this.likeNum = "0";
		this.collectNum = "0";
		this.isPutaway = "0";
	}

	public Article(String id){
		this();
		this.id = id;
	}
	public Article(String id,String title){
		this();
		this.title = title;
	}

	public Article(Category category){
		this();
		this.category = category;
	}
	public Article(Category category,User user){
		this();
		this.category = category;
		this.user = user;
	}

	public void prePersist(){
		//super.prePersist();
		articleData.setId(this.id);
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    @Length(min=0, max=255)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

	@Length(min=0, max=50)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Length(min=0, max=255)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
        this.image = image;//CmsUtils.formatImageSrcToDb(image);
	}

	@Length(min=0, max=255)
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Length(min=0, max=255)
	public String getDescription() {
		return description;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Date getWeightDate() {
		return weightDate;
	}

	public void setWeightDate(Date weightDate) {
		this.weightDate = weightDate;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	@Length(min=0, max=10)
	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

    public String getCustomContentView() {
        return customContentView;
    }

    public void setCustomContentView(String customContentView) {
        this.customContentView = customContentView;
    }

    public String getViewConfig() {
        return viewConfig;
    }

    public void setViewConfig(String viewConfig) {
        this.viewConfig = viewConfig;
    }

	public ArticleData getArticleData() {
		return articleData;
	}

	public void setArticleData(ArticleData articleData) {
		this.articleData = articleData;
	}

	public List<String> getPosidList() {
		List<String> list = Lists.newArrayList();
		if (posid != null){
			for (String s : StringUtils.split(posid, ",")) {
				list.add(s);
			}
		}
		return list;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPosidList(List<String> list) {
		posid = ","+StringUtils.join(list, ",")+",";
	}

   	public String getUrl() {
        return CmsUtils.getUrlDynamic(this);
   	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageSrc() {
        return CmsUtils.formatImageSrcToWeb(this.image);
   	}


	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getPosts() {
		return posts;
	}

	public void setPosts(String posts) {
		this.posts = posts;
	}

	public String getcPosts() {
		return cPosts;
	}

	public void setcPosts(String cPosts) {
		this.cPosts = cPosts;
	}

	public String getJcity() {
		return jcity;
	}

	public void setJcity(String jcity) {
		this.jcity = jcity;
	}

	public CmsClassifying getClassifying() {
		return classifying;
	}

	public void setClassifying(CmsClassifying classifying) {
		this.classifying = classifying;
	}

	public CmsArticleClassify getCmsArticleClassify() {
		return cmsArticleClassify;
	}

	public void setCmsArticleClassify(CmsArticleClassify cmsArticleClassify) {
		this.cmsArticleClassify = cmsArticleClassify;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(String collectNum) {
		this.collectNum = collectNum;
	}

	public String getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(String likeNum) {
		this.likeNum = likeNum;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getIsPutaway() {
		return isPutaway;
	}

	public void setIsPutaway(String isPutaway) {
		this.isPutaway = isPutaway;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
}


