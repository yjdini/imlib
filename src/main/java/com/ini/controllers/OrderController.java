package com.ini.controllers;

import com.ini.dao.entity.Orders;
import com.ini.service.OrderService;
import com.ini.service.UserService;
import com.utils.ConstJson;
import com.utils.SessionUtil;
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
    @Autowired
    private SessionUtil sessionUtil;

    @RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result addSkill(@RequestBody Orders order, HttpServletRequest request, HttpServletResponse response)
    {
        //防止恶意为别人创建预约
        order.setFromUserId(sessionUtil.getUserId(request));
        return orderService.addOrder(order);
    }

    @RequestMapping(value ="/cancle/{orderId}")
    public ConstJson.Result cancleOrder(@PathVariable Integer orderId, HttpServletRequest request){
        //防止恶意取消别人的预约
        return orderService.cancleOrder(orderId, sessionUtil.getUserId(request));
    }

    @RequestMapping(value ="/reject/{orderId}")
    public ConstJson.Result rejectOrder(@PathVariable Integer orderId, HttpServletRequest request){
        //防止恶意拒绝别人的预约
        return orderService.rejectOrder(orderId, sessionUtil.getUserId(request));
    }

    @RequestMapping(value ="/finish/{orderId}")
    public ConstJson.Result finishOrder(@PathVariable Integer orderId, HttpServletRequest request){
        //防止恶意完成别人的预约
        return orderService.finishOrder(orderId, sessionUtil.getUserId(request));
    }

    @RequestMapping(value ="/delete/{orderId}")
    public ConstJson.Result deleteOrder(@PathVariable Integer orderId, HttpServletRequest request){
        //防止恶意删除别人的预约
        return orderService.deleteOrder(orderId, sessionUtil.getUserId(request));
    }

    @RequestMapping("/list")
    public List<Orders> getOrders(HttpServletRequest request){
        //用户查看自己的预约列表
        return orderService.getOrdersByUserId(sessionUtil.getUserId(request));
    }

    @RequestMapping("/info/{orderId}")
    public Orders getOrderDetail(@PathVariable Integer orderId, HttpServletRequest request){
        //查看与自己相关的预约详情
        return orderService.getOrderDetail(orderId, sessionUtil.getUserId(request));
    }


}
