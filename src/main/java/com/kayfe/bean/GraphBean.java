package com.kayfe.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class GraphBean implements Serializable {

    private String date;
    private Integer confirm;
    private Integer heal;
    private Integer dead;
    private Integer confirmAdd;
}
