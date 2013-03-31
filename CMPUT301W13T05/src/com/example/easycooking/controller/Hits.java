package com.example.easycooking.controller;

import java.util.Collection;

/**
 * This class is took from chenlei's ESDemo
 * address:https://github.com/rayzhangcl/ESDemo
 * @author HongZu
 *
 * @param <T>
 */
public class Hits<T> {
    int total;
    double max_score;
    Collection<ElasticSearchResponse<T>> hits;
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits;
    }
    public String toString() {
        return (super.toString()+","+total+","+max_score+","+hits);
    }
}