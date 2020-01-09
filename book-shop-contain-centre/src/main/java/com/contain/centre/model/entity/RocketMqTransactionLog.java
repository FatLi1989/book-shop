package com.contain.centre.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * <p>
 * RocketMQ事务日志表
 * </p>
 *
 * @author novLi
 * @since 2020-01-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="RocketmqTransactionLog对象", description="RocketMQ事务日志表")
@TableName(value = "rocketmq_transaction_log")
public class RocketMqTransactionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识")
    private String id;

    @ApiModelProperty(value = "事务标识")
    private String transactionId;

    @ApiModelProperty(value = "日志")
    private String log;


}
