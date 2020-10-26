package com.example.demo.repository.device;

import com.example.demo.model.device.DeviceLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceLogRepository extends JpaRepository<DeviceLog, Integer> {
	
    DeviceLog findDeviceLogById(int id);

    Page<DeviceLog> findByUserIdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(String userId, Long fromTimestamp, Long toTimestamp, Pageable pageable);
}
