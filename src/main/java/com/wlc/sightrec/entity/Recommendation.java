package com.wlc.sightrec.entity;

import lombok.Data;

@Data
public class Recommendation {
    private Integer userId;
    private String itemIds;
}
