package com.px.tcp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 品讯科技
 */
@Data
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {
    @NotNull
    @Size(min = 1000, max = 65535)
    private int tcpPort;

    @Min(1)
    @NotNull
    private int bossCount;

    @Min(2)
    @NotNull
    private int workerCount;

    @NotNull
    private boolean keepAlive;

    @NotNull
    private int backlog;

    @NotNull
    private int clientTimeout;
}
