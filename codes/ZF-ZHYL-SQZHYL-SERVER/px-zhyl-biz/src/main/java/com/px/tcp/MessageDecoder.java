package com.px.tcp;

import cn.hutool.core.util.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 品讯科技
 */
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        byte[] b = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(b);
        String body= HexUtil.encodeHexStr(b,false);
        Map<String,String> map=new HashMap<>();
        body=body
                .replaceAll("7D05", "2A")
                .replaceAll("7D04", "2C")
                .replaceAll("7D03", "5D")
                .replaceAll("7D02", "5B")
                .replaceAll("7D01", "7D");
        map.put("hex", body);
        body=HexUtil.decodeHexStr(body);
        map.put("str", body+"]");
        list.add(map);
    }
}
