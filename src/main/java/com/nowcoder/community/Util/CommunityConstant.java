package com.nowcoder.community.Util;

/**
 * @author sun
 * @create 2022-04-02 22:14
 */
public interface CommunityConstant {
    /**
     * 激活成功
     */

    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */

    int ACTIVATION_REPEAT = 1;

    /**
     * 激活成功
     */

    int ACTIVATION_FAILURE = 2;

    /**
     * 默认的登录凭证时间 12小时
     */

    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;


    /**
     * 记住我后，给一个比较长时间 100天
     */

    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * 实体类型:帖子
     */
    int ENTITY_TYPE_POST = 1;

    /**
     * 实体类型：评论就是回复
     */
    int ENTITY_TYPE_COMMENT = 2;
}
