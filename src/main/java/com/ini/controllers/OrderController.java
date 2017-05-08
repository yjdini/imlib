package com.ini.controllers;

import com.ini.entity.Order;
import com.ini.service.OrderService;
import com.ini.service.UserService;
import com.utils.ConstJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @RequestMapping(value = "/addOrder",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result addSkill(@RequestBody Order order, HttpServletRequest request, HttpServletResponse response)
    {
        //防止恶意为别人创建预约
//        order.setUserId(userService.getSessionUid(request));
        return orderService.addOrder(order);
    }

    @RequestMapping(value ="/cancleOrder/{orderId}")
    public ConstJson.Result cancleOrder(@PathVariable Integer orderId, HttpServletRequest request){
        //防止恶意取消别人的预约
        return orderService.cancleOrder(orderId, userService.getSessionUid(request));
    }

    @RequestMapping(value ="/rejectOrder/{orderId}")
    public ConstJson.Result rejectOrder(@PathVariable Integer orderId, HttpServletRequest request){
        //防止恶意拒绝别人的预约
        return orderService.rejectOrder(orderId, userService.getSessionUid(request));
    }

    @RequestMapping(value ="/deleteOrder/{orderId}")
    public ConstJson.Result deleteOrder(@PathVariable Integer orderId, HttpServletRequest request){
        //防止恶意拒绝别人的预约
        return orderService.deleteOrder(orderId, userService.getSessionUid(request));
    }

    @RequestMapping("/getOrders")
    public List<Order> getOrders(HttpServletRequest request){
        //用户查看自己的预约列表
        return orderService.getOrders(userService.getSessionUid(request));
    }

    @RequestMapping("/getOrderDetail/{orderId}")
    public Order getOrderDetail(@PathVariable Integer orderId, HttpServletRequest request){
        //查看与自己相关的预约详情
        return orderService.getOrderDetail(orderId, userService.getSessionUid(request));
    }


}
