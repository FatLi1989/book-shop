package com.applet.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AuditStatusEnum {



    /**
     * 待审核
     */
    NOT_YET("NOT_YET"),
    /**
     * 审核通过
     */
    PASS("PASS"),
    /**
     * 审核不通过
     */
    REJECT("REJECT");

    private String message;

}
