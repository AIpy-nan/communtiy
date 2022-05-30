package com.nowcoder.community.Util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;

/**
 * @author sun
 * @create 2022-04-11 15:37
 */

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    //替换符号
    private static final String REPLACEMENT = "***";

    private TrieNode root = new TrieNode();
    //初始化前缀树

    @PostConstruct
    public void init(){
        try(
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words");
                //缓冲流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ){
            //reader中读取数据
            String keyword;
            while ((keyword = reader.readLine()) != null){
                System.out.println(keyword);
                //添加到前缀树
                this.addKeyWord(keyword);

            }
        }catch (IOException e){
            logger.error("加载敏感词文件失败" + e.getMessage());
        }
        //任意对象获取类加载器，从类路径下获取资源，就是在class路径之下

    }

    //字符添加到树中
    private void addKeyWord(String keyWord){
        //temp指针指向根节点
        TrieNode temp = root;
        for(int i = 0 ; i < keyWord.length(); i++){
            char c = keyWord.charAt(i);
            TrieNode subNode = temp.getSubNode(c);
            if(subNode == null){
                subNode = new TrieNode();
                //挂到当前节点
                temp.addSubNode(c, subNode);
            }
            //指针下移
            temp = subNode;

            //设置结束的标识
            if(i == keyWord.length() - 1){
                temp.setKeywordEnd(true);
            }
        }
    }

    /**
     *  过滤敏感词
     * @param text 带过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text){
        //如果文本不为空就不用过滤了
        if (StringUtils.isBlank(text)){
            return null;
        }
        //指针1 指向树
        TrieNode tempNode = root;
        //指针2  指向文本初始
        int begin = 0;
        //指针3 文本的末尾
        int position = 0;
        //结果
        System.out.println(11111);
        StringBuilder sb = new StringBuilder();
        while (position < text.length()){
            char c = text.charAt(position);
            //跳过符号
            if(isSymbol(c)){
                //若指针1在根节点 还在根节点，指针2,3往下动
                if(tempNode == root){
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            //检查下级节点
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                sb.append(text.charAt(begin));
                //进入下一个位置
                position = ++begin;

                //重新指向根节点
                tempNode = root;
            }else if (tempNode.isKeywordEnd){
                //发现敏感词
                sb.append(REPLACEMENT);
                begin = ++position;
                tempNode = root;
            }else{
                //继续检查
                position++;
            }

        }
        System.out.println(sb.toString());
        sb.append(text.substring(begin));
        return sb.toString();

    }

    //判断是否为符号
    private boolean isSymbol(Character c){
        //c < 0x2E80 || c > 0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }


    //前缀树
    private class TrieNode{
        //关键词的标识位，就是路径后的最后一个
        private boolean isKeywordEnd = false;

        //子节点（key 是下级节点字符， value是下级节点）
        HashMap<Character, TrieNode> subNodes = new HashMap<>();

        //get方法
        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        //set方法
        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        //添加节点
        public void addSubNode(Character c, TrieNode node){
            subNodes.put(c, node);
        }

        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
