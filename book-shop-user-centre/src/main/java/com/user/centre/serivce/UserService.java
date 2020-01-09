package com.user.centre.serivce;

import com.user.centre.model.dto.messaging.UserAddBonusMsgDTO;
import com.user.centre.model.entity.User;

public interface UserService {

    User findById(Integer id);

    void addBonus(UserAddBonusMsgDTO message);
}
