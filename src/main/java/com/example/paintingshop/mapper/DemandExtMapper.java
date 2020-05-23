package com.example.paintingshop.mapper;

import com.example.paintingshop.model.Demand;

public interface DemandExtMapper {


    int incFollowCount(Demand record);
    int incEnlist(Demand record);
    int reduceEnlist(Demand record);
}
