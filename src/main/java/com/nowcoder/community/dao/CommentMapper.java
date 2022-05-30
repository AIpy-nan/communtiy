package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author sun
 * @create 2022-04-15 10:22
 */

@Mapper
public interface CommentMapper {

    /**
     * 查询某一页数据
     * @param entityType 关于谁的评论
     * @param entityId  评论id
     * @param offset 起始页
     * @param limit 每一页多少条数据
     * @return
     */
    List<Comment> selectCommentsByEntity(int entityType,int entityId,int offset, int limit);

    /**
     * 查询一共多少条数据
     * @param entityType
     * @param entityId
     * @return
     */
    int selectCountByEntity(int entityType,int entityId);

    /**
     *  添加评论
     * @param comment 评论实体
     * @return
     */
    int insertComment(Comment comment);
}
