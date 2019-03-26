package com.imooc.house.entity;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.imooc.house.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

public class HouseEntity {

    private Long id;
    private Integer type;
    private Integer price;
    private String images;
    private String name;
    private Integer areas;
    private  Integer beds;
    private Integer baths;
    private Double rating;

    private String remarks;
    private String properties;
    private String floorPlan;
    private String tags;
    private String createTime;
    private Integer cityId;
    private Integer communityId;
    private String address;
    private String firstImg;
    private List<String> imageList = Lists.newArrayList();
    private List<String> floorPlanList = Lists.newArrayList();
    private List<MultipartFile> houseFiles;

    public List<String> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<String> featureList) {
        this.featureList = featureList;
        StringBuilder stringBuilder = new StringBuilder();
        if(!featureList.isEmpty()){
            for(String str : featureList){
                stringBuilder.append(str + ",");
            }
        }
        this.properties = stringBuilder.substring(0,stringBuilder.lastIndexOf(",")).toString();
    }

    private List<String> featureList;

    private List<MultipartFile> floorPlanFiles;
    private Long userId;
    private Boolean bookmarked;
    private Integer state;
    private List<Long> ids;
    private String sort = "time_desc";
    private String typeStr;

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
        if(type == 1){
            this.typeStr = "For Sale";
        }else{
            this.typeStr = "For Rent";
        }
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
        if(StringUtils.isNotBlank(images)){
            List<String> list = Splitter.on(",").splitToList(images);
            if(!list.isEmpty()){
                this.firstImg = list.get(0);
                this.imageList = list;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAreas() {
        return areas;
    }

    public void setAreas(Integer areas) {
        this.areas = areas;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getBaths() {
        return baths;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
        if(StringUtils.isNotBlank(properties)){
            this.featureList = Splitter.on(",").splitToList(properties);
        }
    }

    public String getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(String floorPlan) {
        this.floorPlan = floorPlan;
        if(StringUtils.isNotBlank(floorPlan)){
            List<String> list = Splitter.on(",").splitToList(floorPlan);
            for(String str : list){
                this.floorPlanList.add(Constants.FILE_URL + str);
            }
        }
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;

    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getFloorPlanList() {
        return floorPlanList;
    }

    public void setFloorPlanList(List<String> floorPlanList) {
        this.floorPlanList = floorPlanList;
    }

    public List<MultipartFile> getHouseFiles() {
        return houseFiles;
    }

    public void setHouseFiles(List<MultipartFile> houseFiles) {
        this.houseFiles = houseFiles;
    }

    public List<MultipartFile> getFloorPlanFiles() {
        return floorPlanFiles;
    }

    public void setFloorPlanFiles(List<MultipartFile> floorPlanFiles) {
        this.floorPlanFiles = floorPlanFiles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
