package com.kayfe.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MapConfirmBean implements Serializable {

    private String name;
    private Integer value;
}
