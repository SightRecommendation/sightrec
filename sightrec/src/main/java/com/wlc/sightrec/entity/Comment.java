package com.wlc.sightrec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private int userId;
    private int sightId;
    private String content;
    private Date createdDate;
    private int status;
}
