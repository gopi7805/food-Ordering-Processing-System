package com.example.FoodOrderingSystem.Service;

import com.example.FoodOrderingSystem.Model.FoodItem;
import com.example.FoodOrderingSystem.Model.FoodOrder;
import com.example.FoodOrderingSystem.Repository.FoodItemRepo;
import com.example.FoodOrderingSystem.Repository.FoodOrderRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    @Autowired
    FoodItemRepo fooditemRepo;

    @Autowired
    FoodOrderRepo foodorderRepo;

    // ==================== VALIDATION METHODS ====================

    // Validate FoodItem
    private String validateFood(FoodItem food) {

        if (food == null) {
            return "Food details are required";
        }

        if (food.getName() == null || food.getName().trim().isEmpty()) {
            return "Food name is required";
        }

        if (food.getCategory() == null || food.getCategory().trim().isEmpty()) {
            return "Food category is required";
        }

        if (food.getPrice() == null || food.getPrice() <= 0) {
            return "Food price must be greater than zero";
        }

        return null;
    }


    // Validate FoodOrder
    private String validateOrder(FoodOrder order) {

        if (order == null) {
            return "Order details are required";
        }

        if (order.getCustomerName() == null ||
                order.getCustomerName().trim().isEmpty()) {
            return "Customer name is required";
        }

        if (order.getFoodName() == null ||
                order.getFoodName().trim().isEmpty()) {
            return "Food name is required";
        }

        if (order.getQuantity() == null || order.getQuantity() <= 0) {
            return "Quantity must be greater than zero";
        }

        return null;
    }


    // Find FoodItem By Name
    private FoodItem findFoodByName(String name) {

        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        List<FoodItem> foods = fooditemRepo.findAll();

        for (FoodItem food : foods) {

            if (food.getName() != null &&
                    food.getName().equalsIgnoreCase(name.trim())) {
                return food;
            }
        }

        return null;
    }


    // ==================== POST OPERATIONS ====================

    // Post - Add Single FoodItem
    public Object addFood(FoodItem food) {

        String message = validateFood(food);

        if (message != null) {
            return message;
        }

        food.setId(null);

        return fooditemRepo.save(food);
    }


    // Post - Add Bulk FoodItems
    public Object addFoods(List<FoodItem> foods) {

        if (foods == null || foods.isEmpty()) {
            return "FoodItems list cannot be empty";
        }

        for (FoodItem food : foods) {

            String message = validateFood(food);

            if (message != null) {
                return message;
            }
        }

        for (FoodItem food : foods) {
            food.setId(null);
        }

        return fooditemRepo.saveAll(foods);
    }


    // Post - Place Single FoodOrder
    public Object placeOrder(FoodOrder order) {

        String message = validateOrder(order);

        if (message != null) {
            return message;
        }

        FoodItem food = findFoodByName(order.getFoodName());

        if (food == null) {
            return "FoodItem Not Found: " + order.getFoodName();
        }

        order.setId(null);
        order.setFoodName(food.getName());

        order.setTotalPrice(
                food.getPrice() * order.getQuantity()
        );

        return foodorderRepo.save(order);
    }


    // Post - Place Bulk FoodOrders
    public Object placeBulkOrders(List<FoodOrder> orders) {

        if (orders == null || orders.isEmpty()) {
            return "FoodOrders list cannot be empty";
        }

        List<FoodItem> matchedFoods = new ArrayList<>();

        for (FoodOrder order : orders) {

            String message = validateOrder(order);

            if (message != null) {
                return message;
            }

            FoodItem food = findFoodByName(order.getFoodName());

            if (food == null) {
                return "FoodItem Not Found: " + order.getFoodName();
            }

            matchedFoods.add(food);
        }

        for (int i = 0; i < orders.size(); i++) {

            FoodOrder order = orders.get(i);
            FoodItem food = matchedFoods.get(i);

            order.setId(null);
            order.setFoodName(food.getName());

            order.setTotalPrice(
                    food.getPrice() * order.getQuantity()
            );
        }

        return foodorderRepo.saveAll(orders);
    }


    // ==================== GET OPERATIONS ====================

    // Get - All FoodItems
    public Object getAllFoods() {

        List<FoodItem> foods = fooditemRepo.findAll();

        if (foods.isEmpty()) {
            return "No FoodItems Found";
        }

        return foods;
    }


    // Get - FoodItem By ID
    public Object getFoodById(Integer id) {

        if (id == null || id <= 0) {
            return "Please Provide a Valid FoodItem ID";
        }

        if (!fooditemRepo.existsById(id)) {
            return "FoodItem Not Found With ID: " + id;
        }

        return fooditemRepo.findById(id).get();
    }


    // Get - FoodItem By Name
    public Object getFoodByName(String name) {

        if (name == null || name.trim().isEmpty()) {
            return "Please Provide a FoodItem Name";
        }

        FoodItem food = findFoodByName(name);

        if (food == null) {
            return "FoodItem Not Found With Name: " + name;
        }

        return food;
    }


    // Get - FoodItems By Category
    public Object getFoodsByCategory(String category) {

        if (category == null || category.trim().isEmpty()) {
            return "Please Provide a FoodItem Category";
        }

        List<FoodItem> foods = fooditemRepo.findAll();
        List<FoodItem> matchedFoods = new ArrayList<>();

        for (FoodItem food : foods) {

            if (food.getCategory() != null &&
                    food.getCategory().equalsIgnoreCase(category.trim())) {
                matchedFoods.add(food);
            }
        }

        if (matchedFoods.isEmpty()) {
            return "FoodItems Not Found In Category: " + category;
        }

        return matchedFoods;
    }


    // Get - All FoodOrders
    public Object getAllOrders() {

        List<FoodOrder> orders = foodorderRepo.findAll();

        if (orders.isEmpty()) {
            return "No FoodOrders Found";
        }

        return orders;
    }


    // Get - FoodOrder By ID
    public Object getOrderById(Integer id) {

        if (id == null || id <= 0) {
            return "Please Provide a Valid FoodOrder ID";
        }

        if (!foodorderRepo.existsById(id)) {
            return "FoodOrder Not Found With ID: " + id;
        }

        return foodorderRepo.findById(id).get();
    }


    // ==================== UPDATE OPERATIONS ====================

    // Update - FoodItem By ID
    public Object updateFood(Integer id, FoodItem food) {

        if (id == null || id <= 0) {
            return "Please Provide a Valid FoodItem ID";
        }

        if (!fooditemRepo.existsById(id)) {
            return "FoodItem Not Found With ID: " + id;
        }

        String message = validateFood(food);

        if (message != null) {
            return message;
        }

        FoodItem existingFood = fooditemRepo.findById(id).get();

        existingFood.setName(food.getName());
        existingFood.setCategory(food.getCategory());
        existingFood.setPrice(food.getPrice());

        return fooditemRepo.save(existingFood);
    }


    // Update - FoodOrder By ID
    public Object updateOrder(Integer id, FoodOrder order) {

        if (id == null || id <= 0) {
            return "Please Provide a Valid FoodOrder ID";
        }

        if (!foodorderRepo.existsById(id)) {
            return "FoodOrder Not Found With ID: " + id;
        }

        String message = validateOrder(order);

        if (message != null) {
            return message;
        }

        FoodItem food = findFoodByName(order.getFoodName());

        if (food == null) {
            return "FoodItem Not Found: " + order.getFoodName();
        }

        FoodOrder existingOrder = foodorderRepo.findById(id).get();

        existingOrder.setCustomerName(order.getCustomerName());
        existingOrder.setFoodName(food.getName());
        existingOrder.setQuantity(order.getQuantity());

        existingOrder.setTotalPrice(
                food.getPrice() * order.getQuantity()
        );

        return foodorderRepo.save(existingOrder);
    }


    // ==================== DELETE OPERATIONS ====================

    // Delete - FoodItem By ID
    public String deleteFood(Integer id) {

        if (id == null || id <= 0) {
            return "Please Provide a Valid FoodItem ID";
        }

        if (!fooditemRepo.existsById(id)) {
            return "FoodItem Not Found With ID: " + id;
        }

        fooditemRepo.deleteById(id);

        return "FoodItem Deleted Successfully. ID: " + id;
    }


    // Delete - FoodOrder By ID
    public String deleteOrder(Integer id) {

        if (id == null || id <= 0) {
            return "Please Provide a Valid FoodOrder ID";
        }

        if (!foodorderRepo.existsById(id)) {
            return "FoodOrder Not Found With ID: " + id;
        }

        foodorderRepo.deleteById(id);

        return "FoodOrder Deleted Successfully. ID: " + id;
    }


}
