package com.a101.ssafy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a101.ssafy.project.model.alert.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long>{

}
