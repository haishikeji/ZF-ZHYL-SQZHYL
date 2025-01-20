package com.px.modulars.upms.util.websocket;

import com.px.basic.alone.security.annotation.Inner;
import com.px.modulars.upms.util.websocket.util.WebSocketServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 */
@RestController
@RequestMapping("/ws")
public class WebSocketController {


    /**
     * 群发消息内容
     *
     * @param message
     * @return
     */
    @Inner(value = false)
    @RequestMapping(value = "/sendAll", method = RequestMethod.GET)
    @ApiOperation("群发消息内容")
    public String sendAllMessage(@RequestParam(required = true) String message) {
        try {
            WebSocketServer.BroadCastInfo(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }


    /**
     * 指定会话ID发消息
     *
     * @param message 消息内容
     * @param id      连接会话ID
     * @return
     */
    @RequestMapping(value = "/sendOne", method = RequestMethod.GET)
    @ApiOperation("指定会话ID发消息")
    public String sendOneMessage(@RequestParam(required = true) String message, @RequestParam(required = true) String id) {
        try {
            WebSocketServer.SendMessage(message, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}