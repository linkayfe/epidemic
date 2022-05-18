package com.kayfe.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class City implements Serializable {
    @TableId(type= IdType.ASSIGN_ID)
    private Integer cityId;
    private String cityName;
    private Integer confirmAdd;
    private Integer confirm;
    private Integer nowConfirm;
    private Integer dead;
    private Integer heal;
    private String provinceName;
    private String updateTime;
}
