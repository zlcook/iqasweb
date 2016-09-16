package com.noumenon.similarity.hownet2000.concept;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import com.noumenon.similarity.hownet2000.sememe.LiuqunSememeParser;
import com.noumenon.similarity.hownet2000.sememe.SememeParser;
import com.noumenon.similarity.hownet2000.util.BlankUtils;

/**
 * 刘群老师的相似度计算方式，对概念解析的处理方式
 * 
 * @author 于欢
 */
public class LiuConceptParser extends ConceptParser {

    private static LiuConceptParser instance = null;

    public static LiuConceptParser getInstance() {
        if (instance == null) {
            try {
                instance = new LiuConceptParser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private LiuConceptParser() throws IOException {
        super(new LiuqunSememeParser());
    }

    private LiuConceptParser(SememeParser sememeParser) throws IOException {
        super(sememeParser);
    }

    public void setParameter(double b1, double b2, double b3, double b4) {
        beta1 = b1;
        beta2 = b2;
        beta3 = b3;
        beta4 = b4;
    }

    protected double calculate(double sim_v1, double sim_v2, double sim_v3, double sim_v4) {
        return beta1 * sim_v1 + beta2 * sim_v1 * sim_v2 + beta3 * sim_v1 * sim_v2 * sim_v3 + beta4 * sim_v1 * sim_v2 * sim_v3 * sim_v4;
    }

    public static void main(String[] args) {
    }
}
