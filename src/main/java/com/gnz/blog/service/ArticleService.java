package com.gnz.blog.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import com.gnz.blog.exception.BlogException;
import com.gnz.blog.model.dto.ArticleDTO;
import com.gnz.blog.utils.DateTimeUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.gnz.blog.dao.*;
import com.gnz.blog.model.entity.*;
import com.gnz.blog.model.vo.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static com.gnz.blog.model.enums.ErrorInfoEnum.INVALID_ID;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;


    public PageVO<ArticleVO> getArticles(int page, int limit) {
        //使用条件构造器
        QueryWrapper<ArticlePO> qw = new QueryWrapper<>();
        qw.select(ArticlePO.class, i -> !"content".equals(i.getColumn()));
        Page<ArticlePO> res = articleMapper.selectPage(new Page<>(page, limit), qw);

        List<ArticleVO> articleVOS = res.getRecords().stream()
                //调用fromArticlePO实现PO向VO的转换
                .map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());
        PageVO<ArticleVO> pageVO = PageVO.<ArticleVO>builder()
                .records(articleVOS.isEmpty() ? new ArrayList<>() : articleVOS)
                .total(res.getTotal())
                .current(res.getCurrent())
                .size(res.getSize())
                .build();
        return pageVO;
    }

    /**
     * 返回个人豆瓣数据,(最终需要返回一个对象)
     * @return
     * @throws IOException
     */
    public DoubanVO<JSONObject> getDouban() throws IOException {
        //获取json信息
        String jsons = readJsonData("gnz.json");//传入文件存放地址//File.separatorChar自动匹配斜杠
        //将转换好的字符串写入文件，只有当“{”第一次出现位置是在第二位时才执行写入操作
        if(jsons.indexOf("{")==1){
            FileWriter fw = new FileWriter("gnz.json");
            fw.write(jsons);
            fw.close();
        }
        List<JSONObject> list1 = JSONObject.parseArray(jsons, JSONObject.class);
        DoubanVO<JSONObject> doubanVO = DoubanVO.<JSONObject>builder()
                .records(list1.isEmpty() ? new ArrayList<>() : list1)
                .build();
        return doubanVO;
    }
    /**
     * 读取json文件并且转换成字符串
     * @param pactFile  文件路径
     * @return
     * @throws IOException
     */
    public static String readJsonData(String pactFile) throws IOException {
        // 读取文件数据
        //System.out.println("读取文件数据util");
        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile);//"D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            System.err.println("找不到" + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);
            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            System.out.println(strbuffer.lastIndexOf(","));
            //只在第一次开头增加[，结尾,替换为]
            if(strbuffer.indexOf("{")==0){
                strbuffer.insert(0,"[");
                strbuffer.replace(strbuffer.lastIndexOf(","),strbuffer.lastIndexOf(",")+1,"]");
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        //System.out.println("读取文件结束util");

        return strbuffer.toString();
    }

    public String insArticle(ArticleDTO articleDTO) {
        ArticlePO po = articleDTO.toArticlePO(false);
        String id = IdUtil.objectId();
        po.setId(id);
        articleMapper.insert(po);
        return id;
    }
    @Transactional(rollbackFor = Exception.class)
    public ArticleVO findById(String id) {
        ArticlePO articlePO = articleMapper.selectById(id);
        if (Objects.isNull(articlePO)) {
            throw new BlogException(INVALID_ID);
        }
        articlePO.setViews(articlePO.getViews() + 1);
        articleMapper.updateById(articlePO);
        return ArticleVO.fromArticlePO(articlePO);
    }

    public void deleteArticle(String id) {
        int i = articleMapper.deleteById(id);
        if (i <= 0) {
            throw new BlogException(INVALID_ID);
        }
    }
    public void updateArticle(ArticleDTO articleDTO, String id) {
        ArticlePO dbArticle = articleMapper.selectById(id);
        if (Objects.isNull(dbArticle)) {
            throw new BlogException(INVALID_ID);
        }
        ArticlePO articlePO = articleDTO.toArticlePO(true);
        articlePO.setId(id);
        articleMapper.updateById(articlePO);
    }
    public List<TimelineVO> timeline() {
        ArrayList<TimelineVO> res = new ArrayList<>();
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "gmt_create");
        List<Map<String, Object>> maps = articleMapper.selectMaps(wrapper);
        maps.stream().map(m -> TimelineVO.Item.builder()
                .id((String) m.get("id"))
                .gmtCreate(DateTimeUtils.formatDate((Long) m.get("gmt_create")))
                .title((String) m.get("title"))
                .build())
                .collect(Collectors.groupingBy(item -> {
                    String[] arr = item.getGmtCreate().split("-");
                    String year = arr[0];
                    return year;
                })).forEach((k, v) -> res.add(TimelineVO.builder().year(k).items(v).build()));
        res.sort(Comparator.comparing(TimelineVO::getYear).reversed());
        // log.info("timeline list : {}", JSONUtil.toJsonStr(res));
        return res;
    }
    /**
     * 获取统计表格需要的数据
     *
     * @return
     */
    public StatisticsVO countArticlesByMonth() {
        StatisticsVO statisticsVO = new StatisticsVO();
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("gmt_create");
        List<Map<String, Object>> maps = articleMapper.selectMaps(wrapper);
        Map<String, List<Long>> dateCountMap = maps.stream().map(item -> Convert.toLong(item.get("gmt_create")))
                .collect(Collectors.groupingBy(item -> DateTimeUtils.formatDatetime(item, "yyyy/MM")));
        MapUtil.sort(dateCountMap).forEach((k, v) -> statisticsVO.put(k, v.size()));
        return statisticsVO;
    }

}
