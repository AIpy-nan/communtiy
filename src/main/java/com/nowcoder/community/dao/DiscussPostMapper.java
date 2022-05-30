package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sun
 * @create 2022-02-24 16:59
 */

@Mapper
public interface DiscussPostMapper {

    /**
     * 首页查询并显示数据
     * @param userId  根据用户查询 为了后来用户在个人中心中查看自己帖子
     * @param offset  分页的起始页数
     * @param limit   每一页的数目
     * @return
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    /**
     * 查询总数评论
     * @param userId
     * @return
     *
     * @param注解用于给参数取别名
     * 如果只有一个参数的时候，并且在动态sql中<if>里面使用，则必须要增加别名
     */
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     * 插入评论 返回行数
     * @param discussPost
     * @return
     */
    int insertDiscussPost(DiscussPost discussPost);

    /**
     *
     * @param id  帖子id
     * @return 返回帖子详情
     */
    DiscussPost selectDiscussPostById(int id);

    /**
     * 更新某一条帖子的评论数量，就是说当前帖子的有多少评论，当评论增加的时候对应评论表中的数目也要更新
     * @param id
     * @param commentCount
     * @return
     */
    int updateCommentCount(int id, int commentCount);



}
