package com.contain.centre.service;

import com.contain.centre.model.dto.content.ShareAuditDTO;
import com.contain.centre.model.dto.content.ShareDTO;
import com.contain.centre.model.entity.Share;

public interface ShareService {

    ShareDTO findById(Integer id);

    Share auditById(Integer id, ShareAuditDTO auditDTO);
}
