package com.spring.cloud.util;

import java.math.BigDecimal;

/**
 * @author 23947
 * @title: ToBigDecimalFunction
 * @projectName taoke
 * @description: TODO
 * @date 2020/10/1910:45
 */
@FunctionalInterface
public interface ToBigDecimalFunction<T> {
    BigDecimal applyAsBigDecimal(T value);
}
