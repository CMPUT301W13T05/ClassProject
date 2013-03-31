package com.example.easycooking.controller;
/**
 * This class is took from chenlei's ESDemo
 * address:https://github.com/rayzhangcl/ESDemo
 * @author HongZu
 *
 * @param <T>
 */
public class ElasticSearchResponse<T> {
    String _index;
    String _type;
    String _id;
    int _version;
    boolean exists;
    T _source;
    double max_score;
    public T getSource() {
        return _source;
    }
}
