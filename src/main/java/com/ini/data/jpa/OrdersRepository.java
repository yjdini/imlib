package com.ini.data.jpa;

import com.ini.data.entity.Orders;
import com.ini.data.schema.OrderUserSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer>, QueryByExampleExecutor<Orders> {

    List<OrderUserSet> getFromOrders();

    List<OrderUserSet> getToOrders();
}
