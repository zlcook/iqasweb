package com.noumenon.similarity.hownet2000.concept;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.noumenon.similarity.hownet2000.sememe.SememeParser;
import com.noumenon.similarity.hownet2000.util.BlankUtils;
import com.noumenon.similarity.hownet2000.util.FileUtils;
import com.noumenon.similarity.word.hownet2000.HownetMeta;

/**
 * 概念解析器: 包括概念的加载，内部组织、索引、查询以及概念的相似度计算等.
 * 概念保存到数组中, 没有保存到Map中, 以尽量降低对内存空间的使用.
 * 算法的核心思想请参看论文
 * <br/><br/>
 * improvement:
 * <ol>
 * <li>两个义原集合的运算方式支持均值方式或Fuzzy方式</li>
 * </ol>
 * 
 * 
 * @author 于欢
 */
public abstract class ConceptParser implements HownetMeta {

    //β1实词概念第一基本义原描述式的权重
    public double beta1;
    //β2实词概念其他基本义原描述式的权重
    public double beta2;
    //β3实词概念关系义原描述式的权重
    public double beta3;
    //β4实词概念符号义原描述式的权重
    public double beta4;

    /** the logger */
    //protected Log LOG = LogFactory.getLog(this.getClass());
    /** 所有概念存放的数组 */
    private static Concept[] CONCEPTS = null;
    protected SememeParser sememeParser = null;
    //protected SememeParser sememeParser = null;

    /** 集合运算类型，目前支持均值运算和模糊集运算两种形式 */
    public enum SET_OPERATE_TYPE {

        AVERAGE, FUZZY
    };
    /** 默认的集合运算类型为均值法 */
    private SET_OPERATE_TYPE currentSetOperateType = SET_OPERATE_TYPE.AVERAGE;

    public ConceptParser(SememeParser sememeParser) throws IOException {
        this.sememeParser = sememeParser;
        synchronized (this) {
            if (CONCEPTS == null) {
                String conceptFile = getClass().getPackage().getName().replaceAll("\\.", "/") + "/concept.dat";
                InputStream input = this.getClass().getClassLoader().getResourceAsStream(conceptFile);
                load(input, "UTF-8");
            }
        }
    }

    /**
     * 从文件中加载概念知识，并自定进行排序
     *
     * @throws IOException
     */
    public void load(InputStream input, String encoding) throws IOException {
        ConceptDictTraverseEvent event = new ConceptDictTraverseEvent();
        //LOG.info("loading conecpt dictionary...");
        long time = System.currentTimeMillis();

        FileUtils.traverseLines(input, encoding, event);
        CONCEPTS = event.getConcepts();
        System.out.println("共有概念数量:" + CONCEPTS.length);

        time = System.currentTimeMillis() - time;
        //LOG.info("loading concept dictionary completely. time elapsed: " + time);
    }

    /**
     * 判断一个词语是否是一个概念
     * @param word
     * @return
     */
    public boolean isConcept(String word) {
        return !BlankUtils.isBlank(getDefinitions(word));
    }

    /**
     * 根据名称获取对应的概念定义信息，由于一个词语可能对应多个概念，因此返回一个数组<br/>
     * 查找的过程采用二分查找
     *
     * @param key 要查找的概念名称
     * @return
     */
    public Collection<Concept> getConcepts(String key, String definition) {
        Collection<Concept> results = new ArrayList<Concept>();
        for (int i = 0; i < CONCEPTS.length; i++) {
            if (CONCEPTS[i].getWord().equals(key) && CONCEPTS[i].getDefine().equals(definition)) {
                results.add(CONCEPTS[i]);
            }
        }
        return results;
    }

    public Collection<String> getDefinitions(String key) {
        Collection<Concept> results = new ArrayList<Concept>();
        Collection<String> definitions = new ArrayList<String>();
        for (int i = 0; i < CONCEPTS.length; i++) {
            if (CONCEPTS[i].getWord().equals(key)) {
                results.add(CONCEPTS[i]);
            }
        }
        for (Concept temp : results) {
            if (!definitions.contains(temp.getDefine())) {
                definitions.add(temp.getDefine());
            }
        }
        return definitions;
    }


    /** 设置当前的集合运算类型 */
    public void setSetOperateType(SET_OPERATE_TYPE type) {
        this.currentSetOperateType = type;
    }

}
