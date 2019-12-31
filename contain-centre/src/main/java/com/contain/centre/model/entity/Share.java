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
 * 分享表
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
@TableName(value = "share")
@ApiModel(value="Share对象", description="分享表")
public class Share implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "发布人id")
    private Integer userId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否原创 0:否 1:是")
    private Boolean isOriginal;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "概要信息")
    private String summary;

    @ApiModelProperty(value = "价格（需要的积分）")
    private Integer price;

    @ApiModelProperty(value = "下载地址")
    private String downloadUrl;

    @ApiModelProperty(value = "下载数 ")
    private Integer buyCount;

    @ApiModelProperty(value = "是否显示 0:否 1:是")
    private Boolean showFlag;

    @ApiModelProperty(value = "审核状态 NOT_YET: 待审核 PASSED:审核通过 REJECTED:审核不通过")
    private String auditStatus;

    @ApiModelProperty(value = "审核不通过原因")
    private String reason;


}
