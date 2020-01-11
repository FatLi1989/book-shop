package com.applet.gateway.config;

import lombok.Data;

import java.time.LocalTime;

/**
 * @author novLi
 * @date 2020年01月11日 15:18
 */
@Data
public class TimeBetweenConfig {

    private LocalTime start;
    private LocalTime end;
}
