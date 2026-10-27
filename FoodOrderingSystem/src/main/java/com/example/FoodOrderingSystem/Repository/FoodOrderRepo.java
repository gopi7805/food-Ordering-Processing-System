package com.example.FoodOrderingSystem.Repository;

import com.example.FoodOrderingSystem.Model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOrderRepo extends JpaRepository<FoodOrder,Integer> {

}
