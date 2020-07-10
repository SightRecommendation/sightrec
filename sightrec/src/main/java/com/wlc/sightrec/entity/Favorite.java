package com.wlc.sightrec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private int id;
    private int userId;
    private int sightId;
    private Date createdDate;
    private int status;
}
