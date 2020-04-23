package com.wlc.sightrec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sight {
    private int id;
    private String name;
    private String province;
    private String city;
    private String location;
    private String imageUrl;
    private float point;
    private String level;
    private String description;
    private String introduction;
    private String subject;
    private String coordinate;
    private float heat;
    private int status;
}
