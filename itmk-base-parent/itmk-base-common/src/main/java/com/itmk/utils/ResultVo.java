package com.itmk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回值类型
 * @Author: AlbertHPW
 * @Date: 2024/11/21 22:44
 */
@Data
@AllArgsConstructor
public class ResultVo<T> {
    private String msg;
    private int code;
    private T data;
}
