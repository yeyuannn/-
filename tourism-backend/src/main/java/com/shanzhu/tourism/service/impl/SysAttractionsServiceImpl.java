package com.shanzhu.tourism.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.shanzhu.tourism.domain.SysAttractions;
import com.shanzhu.tourism.domain.SysComments;
import com.shanzhu.tourism.mapper.SysAttractionsMapper;
import com.shanzhu.tourism.mapper.SysCommentsMapper;
import com.shanzhu.tourism.service.SysAttractionsService;
import com.shanzhu.tourism.utils.recommend.CoreMath;
import com.shanzhu.tourism.utils.recommend.dto.ProductDTO;
import com.shanzhu.tourism.utils.recommend.dto.RelateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysAttractionsServiceImpl extends ServiceImpl<SysAttractionsMapper, SysAttractions> implements SysAttractionsService {

    @Value("${useRecommend}")
    private Boolean userRecommend;

    @Resource
    private SysCommentsMapper sysCommentsMapper;

    @Resource
    private SysAttractionsMapper sysAttractionsMapper;

    @Override
    public List<SysAttractions> searchAttractions(String userId) {
        //判断是否需要走推荐算法
        if(BooleanUtil.isFalse(userRecommend)){
            QueryWrapper<SysAttractions> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysAttractions::getState,1).last("limit 3");
            return this.list(queryWrapper);
        }else{
            //通过协同过滤推荐搜索
            return recommendAttractions(userId);
        }
    }

    private List<SysAttractions> recommendAttractions(String userId){
        CoreMath coreMath = new CoreMath();
        //1、构造推荐对象数据集
        List<RelateDTO> relateDTOList = generateRelates();

        //2、执行协同过滤推荐算法、获取推荐数据(获取的是id列表)
        List<String> recommendations = coreMath.recommend(userId, relateDTOList);

        //3、获取所有推荐景点的详细数据（基于id列表，取详细的推荐景点数据）
        List<SysAttractions> sysAttractions = new ArrayList<>();
        for (String productId : recommendations) {
            //根据Id获取景点数据
            sysAttractions.add(sysAttractionsMapper.selectById(productId));
        }

        return sysAttractions;
    }

    /**
     * 构造推荐对象数据集
     *
     * @return 推荐数据
     */
    private List<RelateDTO> generateRelates() {
        //查询所有景点的评论
        List<SysComments> comments = sysCommentsMapper.selectList(Wrappers.emptyWrapper());

        //基于评论构造推荐数据
        List<RelateDTO> relateDTOList = Lists.newArrayList();
        for (SysComments sysComment : comments) {
            RelateDTO relateDTO = new RelateDTO();
            //用户id
            relateDTO.setUserId(sysComment.getUserId());
            //推荐景点对象（景点id）
            relateDTO.setProductId(sysComment.getAttractionsId());
            //推荐指数（评论分数）
            relateDTO.setIndex(sysComment.getScore());
            relateDTOList.add(relateDTO);
        }
        if (CollectionUtils.isEmpty(relateDTOList)) {
            log.info("--------------------List<RelateDTO>为空！");
        }

        return relateDTOList;
    }


}
