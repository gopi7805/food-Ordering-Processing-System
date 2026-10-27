package com.example.FoodOrderingSystem.Controller;

import com.example.FoodOrderingSystem.Model.FoodItem;
import com.example.FoodOrderingSystem.Model.FoodOrder;
import com.example.FoodOrderingSystem.Service.FoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class FoodController {

    @Autowired
    FoodService foodService;

    // ==================== POST OPERATIONS ====================

    // Post - Add Single FoodItem
    @PostMapping("/food-items")
    public Object addFood(@RequestBody FoodItem food) {
        return foodService.addFood(food);
    }


    // Post - Add Bulk FoodItems
    @PostMapping("/food-items/bulk")
    public Object addFoods(@RequestBody List<FoodItem> foods) {
        return foodService.addFoods(foods);
    }


    // Post - Place Single FoodOrder
    @PostMapping("/orders")
    public Object placeOrder(@RequestBody FoodOrder order) {
        return foodService.placeOrder(order);
    }


    // Post - Place Bulk FoodOrders
    @PostMapping("/orders/bulk")
    public Object placeBulkOrders(@RequestBody List<FoodOrder> orders) {
        return foodService.placeBulkOrders(orders);
    }


    // ==================== GET OPERATIONS ====================

    // Get - All FoodItems
    @GetMapping("/food-items")
    public Object getAllFoods() {
        return foodService.getAllFoods();
    }


    // Get - FoodItem By ID
    @GetMapping("/food-items/id/{id}")
    public Object getFoodById(@PathVariable Integer id) {
        return foodService.getFoodById(id);
    }


    // Get - FoodItem By Name
    @GetMapping("/food-items/name/{name}")
    public Object getFoodByName(@PathVariable String name) {
        return foodService.getFoodByName(name);
    }


    // Get - FoodItems By Category
    @GetMapping("/food-items/category/{category}")
    public Object getFoodsByCategory(@PathVariable String category) {
        return foodService.getFoodsByCategory(category);
    }


    // Get - All FoodOrders
    @GetMapping("/orders")
    public Object getAllOrders() {
        return foodService.getAllOrders();
    }


    // Get - FoodOrder By ID
    @GetMapping("/orders/{id}")
    public Object getOrderById(@PathVariable Integer id) {
        return foodService.getOrderById(id);
    }


    // ==================== UPDATE OPERATIONS ====================

    // Update - FoodItem By ID
    @PutMapping("/food-items/{id}")
    public Object updateFood(
            @PathVariable Integer id,
            @RequestBody FoodItem food) {
        return foodService.updateFood(id, food);
    }


    // Update - FoodOrder By ID
    @PutMapping("/orders/{id}")
    public Object updateOrder(
            @PathVariable Integer id,
            @RequestBody FoodOrder order) {
        return foodService.updateOrder(id, order);
    }


    // ==================== DELETE OPERATIONS ====================

    // Delete - FoodItem By ID
    @DeleteMapping("/food-items/{id}")
    public String deleteFood(@PathVariable Integer id) {
        return foodService.deleteFood(id);
    }


    // Delete - FoodOrder By ID
    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        return foodService.deleteOrder(id);
    }

}


