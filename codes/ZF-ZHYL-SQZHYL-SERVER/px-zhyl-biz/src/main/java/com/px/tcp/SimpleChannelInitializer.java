package com.px.tcp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 品讯科技
 */
@Component
@RequiredArgsConstructor
public class SimpleChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final StringEncoder stringEncoder = new StringEncoder();

    private final BootNettyChannelInboundHandlerAdapter readerServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(1024000, Unpooled.copiedBuffer("]".getBytes())));
        pipeline.addLast("encoder",stringEncoder);
        pipeline.addLast("decoder",new MessageDecoder());
        pipeline.addLast(readerServerHandler);
    }
}
