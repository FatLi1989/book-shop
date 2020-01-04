package com.contain.centre.model.dto.content;

import com.applet.common.enums.AuditStatusEnum;
import lombok.*;

/**
 * @author novLi
 * @date 2020年01月04日 14:21
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShareAuditDTO {
    /**
     * 审核状态
     */
    private AuditStatusEnum auditStatusEnum;
    /**
     * 原因
     */
    private String reason;
}
