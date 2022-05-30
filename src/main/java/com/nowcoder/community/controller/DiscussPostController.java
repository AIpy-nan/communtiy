package com.nowcoder.community.controller;

import com.nowcoder.community.Util.CommunityConstant;
import com.nowcoder.community.Util.CommunityUtil;
import com.nowcoder.community.Util.HostHolder;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.CommentService;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author sun
 * @create 2022-04-12 17:03
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @RequestMapping(path = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title, String content){
        //获取用户
        User user = hostHolder.getUsers();
        if(user == null){
            return CommunityUtil.getJSONString(403, "去登录，傻批");
        }
        //创建实体
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);

        return CommunityUtil.getJSONString(0,"你的骚评论已经成功");
    }

    /**
     *
     * @param discussPostId
     * @param model
     * @param page 实体类型作为参数 springmvc会将其装配到model
     * @return
     */
    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId,Model model,
                                 Page page){
        DiscussPost post =  discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post", post);
        //作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user",user);

        //查分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail" + discussPostId);
        page.setRows(post.getCommentCount());

        //评论：给帖子的评论
        //回复：评论的评论
        List<Comment> commentList = commentService.findCommentsByEntity(ENTITY_TYPE_POST, post.getId(),
                page.getOffset(),page.getLimit());
        List<Map<String, Object>> commentVOList = new ArrayList<>();
        if(commentList != null){
            for(Comment comment : commentList){
                //评论VO
                HashMap<String, Object> commentVO = new HashMap<>();
                //评论
                commentVO.put("comment", comment);
                //作者
                commentVO.put("user",userService.findUserById(comment.getUserId()));
                //回复列表
                List<Comment> replayList = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT,
                        comment.getId(),0, Integer.MAX_VALUE);
                List<Map<String, Object>> replayVOList = new ArrayList<>();
                if(replayList != null){
                    for(Comment replay : replayList){
                        HashMap<String, Object> replayVO = new HashMap<>();
                        //回复
                        replayVO.put("reply", replay);
                        //作者
                        replayVO.put("user", userService.findUserById(replay.getUserId()));
                        //回复的目标
                        User target = replay.getTargetId() == 0 ? null : userService.findUserById(replay.getTargetId());
                        replayVO.put("target", target);
                        replayVOList.add(replayVO);
                    }
                }
                //把回复装进去
                commentVO.put("replys", replayVOList);
                //回复数量
                int replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentVO.put("replyCount",replyCount);

                commentVOList.add(commentVO);
            }
        }
        model.addAttribute("comments", commentVOList);
        return "/site/discuss-detail";


    }
}
