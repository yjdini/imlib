package com.ini.controllers;

import com.ini.aop.authentication.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.dao.entity.Orders;
import com.ini.service.abstrac.OrderService;
import com.ini.service.abstrac.UserService;
import com.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/4/5.
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionUtil sessionUtil;

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map addOrder(@RequestBody Orders order)
    {
        //防止恶意为别人创建预约
        return orderService.addOrder(order).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value ="/cancle/{orderId}")
    public Map cancleOrder(@PathVariable Integer orderId){
        //防止恶意取消别人的预约
        return orderService.cancleOrder(orderId).getMap();
    }

    @Authentication(value = AuthenticationType.Master)
    @RequestMapping(value ="/reject/{orderId}")
    public Map rejectOrder(@PathVariable Integer orderId, @RequestBody Map<String, String> body){
        //防止恶意拒绝别人的预约
        return orderService.rejectOrder(orderId, body.get("rejectReason")).getMap();
    }

    @Authentication(value = AuthenticationType.Master)
    @RequestMapping(value ="/agree/{orderId}")
    public Map agreeOrder(@PathVariable Integer orderId){
        //防止恶意同意别人的预约
        return orderService.agreeOrder(orderId).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value ="/finish/{orderId}")
    public Map finishOrder(@PathVariable Integer orderId){
        //防止恶意完成别人的预约
        return orderService.finishOrder(orderId).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value ="/delete/{orderId}")
    public Map deleteOrder(@PathVariable Integer orderId){
        //防止恶意删除别人的预约
        return orderService.deleteOrder(orderId).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping("/from/list")
    public Map getFromOrders(){
        //用户查看自己的预约列表
        return orderService.getFromOrders().getMap();
    }

    @Authentication(value = AuthenticationType.Master)
    @RequestMapping("/to/list")
    public Map getToOrders(){
        //用户查看自己的预约列表
        return orderService.getToOrders().getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping("/info/{orderId}")
    public Map getOrderDetail(@PathVariable Integer orderId){
        //查看与自己相关的预约详情
        return orderService.getOrderDetail(orderId).getMap();
    }


}
