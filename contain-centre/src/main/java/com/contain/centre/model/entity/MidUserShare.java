package com.contain.centre.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * <p>
 * 用户-分享中间表【描述用户购买的分享】
 * </p>
 *
 * @author novLi
 * @since 2019-12-31
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mid_user_share")
@ApiModel(value="MidUserShare对象", description="用户-分享中间表【描述用户购买的分享】")
public class MidUserShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "share.id")
    private Integer shareId;

    @ApiModelProperty(value = "user.id")
    private Integer userId;


}
