package com.wlc.sightrec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating implements Serializable {
    private int id;
    private int userId;
    private int sightId;
    private int rating;
    private Date createdDate;
    private int status;
}
