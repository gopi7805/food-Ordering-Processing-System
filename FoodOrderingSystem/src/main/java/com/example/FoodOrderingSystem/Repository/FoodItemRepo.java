package com.example.FoodOrderingSystem.Repository;

import com.example.FoodOrderingSystem.Model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepo extends JpaRepository <FoodItem,Integer>{

}
