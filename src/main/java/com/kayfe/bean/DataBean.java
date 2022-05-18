package com.kayfe.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("area")
public class DataBean implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer areaId;
    private String areaName;
    private Integer confirmAdd;
    private Integer confirm;
    private Integer nowConfirm;
    private Integer dead;
    private Integer heal;
    private String updateTime;
}
