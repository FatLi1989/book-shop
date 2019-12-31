package com.contain.centre.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
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
@TableName(value = "notice")
@ApiModel(value="Notice对象", description="")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "是否显示 0:否 1:是")
    private Boolean showFlag;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
