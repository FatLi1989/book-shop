package com.contain.centre.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.applet.common.util.JSONResult;
import com.contain.centre.feignclient.UserCentreFeignClient;
import com.contain.centre.mapper.ShareMapper;
import com.contain.centre.model.dto.share.ShareDTO;
import com.contain.centre.model.dto.user.UserDTO;
import com.contain.centre.model.entity.Share;
import com.contain.centre.service.ShareService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author novLi
 * @date 2019年12月31日 12:53
 */
@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private UserCentreFeignClient userCentreFeignClient;

    @Autowired
    private ShareMapper shareMapper;

    @Override
    public ShareDTO findById(Integer id) {
        Share share = shareMapper.selectById(id);

        ShareDTO shareDTO = new ShareDTO();

        BeanUtils.copyProperties(share, shareDTO);
        if (ObjectUtil.isNotNull(share.getUserId())) {
           UserDTO userDTO = userCentreFeignClient.findById(share.getUserId());
            if (ObjectUtil.isNotNull(userDTO)) {
                shareDTO.setWxNickname(userDTO.getWxNickname());
            }
        }
        return shareDTO;
    }
}
