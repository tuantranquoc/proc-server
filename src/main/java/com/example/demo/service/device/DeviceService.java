package com.example.demo.service.device;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.dto.device.ListDevice;
import com.example.demo.dto.device.ListDeviceLogDto;
import com.example.demo.model.device.Device;
import com.example.demo.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Map;
import com.example.demo.dto.device.DeviceLogDto;
import com.example.demo.dto.device.DeviceLogSubmitDto;
import com.example.demo.dto.user.UserTemperature;
import com.example.demo.model.conf.EmailConfig;
import com.example.demo.model.device.DeviceLog;
import com.example.demo.repository.card.UserCardRepository;
import com.example.demo.repository.conf.EmailConfigRepository;
import com.example.demo.repository.conf.TemperatureRepository;
import com.example.demo.repository.device.DeviceLogRepository;
import com.example.demo.repository.device.DeviceRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.auth.AuthenticationService;
import com.example.demo.service.message.Message;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final DeviceLogRepository deviceLogRepository;
    private final Map map;
    private final TemperatureRepository temperatureRepository;
    private final EmailConfigRepository emailConfigRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, AuthenticationService authenticationService,
            UserRepository userRepository, DeviceLogRepository deviceLogRepository, Map map,
            UserCardRepository userCardRepository, TemperatureRepository temperatureRepository,
            EmailConfigRepository emailConfigRepository, JavaMailSender javaMailSender) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.deviceLogRepository = deviceLogRepository;
        this.map = map;
        this.temperatureRepository = temperatureRepository;
        this.emailConfigRepository = emailConfigRepository;

        this.javaMailSender = javaMailSender;
    }

    public boolean submitLog(DeviceLogSubmitDto deviceLogSubmitDto) {
        SimpleMailMessage msg = new SimpleMailMessage();
        int max_temperature = temperatureRepository.findById(1).getTemperature();
        if (deviceRepository.findDeviceById(deviceLogSubmitDto.getDeviceId()) == null
                || userRepository.findUserById(deviceLogSubmitDto.getUserId()) == null) {
            return false;
        }
        DeviceLog deviceLog = new DeviceLog();
        deviceLog.setDevice(deviceRepository.findDeviceById(deviceLogSubmitDto.getDeviceId()));
        deviceLog.setTimestamp(deviceLogSubmitDto.getTimestamp());
        deviceLog.setTemperature(deviceLogSubmitDto.getTemperature());
        deviceLog.setUser(userRepository.findUserById(deviceLogSubmitDto.getUserId()));
        deviceLog.setCardType(deviceLogSubmitDto.getCardType());
        try {
            deviceLogRepository.save(deviceLog);
        } catch (Exception e) {
            return false;
        }
        if (deviceLogSubmitDto.getTemperature() > max_temperature) {
            List<EmailConfig> listEmail = emailConfigRepository.findAll();
            for (EmailConfig list : listEmail) {
                String email = list.getEmail();
                msg.setTo(email);
                msg.setSubject("Alert Temperature");
                msg.setText(userRepository.findUserById(deviceLogSubmitDto.getUserId()).getName()
                        + " have current temperature " + deviceLogSubmitDto.getTemperature() + Message.ALERT_TEMPERATURE
                        + " " + max_temperature);
                javaMailSender.send(msg);
            }
        }
        return true;
    }

    public ListDeviceLogDto getList(int page, int pageSize, Long fromTimestamp, Long toTimestamp, String name,
            String email, int temperature, String filterOnly, String sortByTemperature, String sortByDate,
            String countOnly) {
        System.out.println("timestamp: " + fromTimestamp + " " + toTimestamp);
        List<DeviceLogDto> deviceLogDtoList = new ArrayList<>();
        Pageable paging = getPage(sortByTemperature, sortByDate, page, pageSize);
        Page<DeviceLog> deviceLogPage = null;
        if (filterOnly.equals("N")) {
            assert false;
            deviceLogPage = deviceLogRepository.findAll(paging);
        } else {
            User user = userRepository.findUserByNameAndEmail(name, email);
            if (user != null) {
                if (fromTimestamp != 0 && toTimestamp != 0) {
                    deviceLogPage = deviceLogRepository
                            .findByUserIdAndTemperatureAndTimestampGreaterThanEqualAndTimestampLessThanEqual(
                                    user.getId(), temperature, fromTimestamp, toTimestamp, paging);
                }
                if (fromTimestamp == 0 || toTimestamp == 0) {
                    if (temperature == 0) {
                        deviceLogPage = deviceLogRepository.findByUserId(user.getId(), paging);
                    } else {
                        deviceLogPage = deviceLogRepository.findByUserIdAndTemperature(user.getId(), temperature,
                                paging);
                    }
                }
            } else {
                if (temperature != 0 && (name == null || name.equals("undefined"))
                        && (email == null || email.equals("undefined"))) {
                    deviceLogPage = deviceLogRepository.findByTemperature(temperature, paging);
                    for (DeviceLog deviceLog : deviceLogPage) {
                        deviceLogDtoList.add(map.deviceLogDto(deviceLog));
                    }
                    if (countOnly.equals("N")) {
                        return new ListDeviceLogDto(deviceLogDtoList);
                    }
                    return new ListDeviceLogDto(deviceLogPage.getTotalElements(), deviceLogPage.getTotalPages(),
                            new ArrayList<DeviceLogDto>());
                }
                return new ListDeviceLogDto(deviceLogDtoList);
            }
        }
        if (deviceLogPage != null) {
            for (DeviceLog deviceLog : deviceLogPage) {
                deviceLogDtoList.add(map.deviceLogDto(deviceLog));
            }
            if (countOnly.equals("N")) {
                return new ListDeviceLogDto(deviceLogDtoList);
            }
            deviceLogDtoList.clear();
            return new ListDeviceLogDto(deviceLogPage.getTotalElements(), deviceLogPage.getTotalPages(),
                    new ArrayList<DeviceLogDto>());
        }
        return new ListDeviceLogDto(deviceLogDtoList);
    }

    public Long pastSixMonths() {
        LocalDate currentDate = LocalDate.now();
        LocalDate result = currentDate.minus(6, ChronoUnit.MONTHS);
        Timestamp ts = new Timestamp(convertToDateViaInstant(result).getTime());
        return ts.getTime();
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public List<UserTemperature> getListByTimeInterval(String userId, Long fromTimestamp, Long toTimestamp, int page,
            int pageSize) {
        List<UserTemperature> userTemperatureList = new ArrayList<>();
        Pageable paging = PageRequest.of(page, pageSize, Sort.by("timestamp").descending());
        Page<DeviceLog> deviceLogList = deviceLogRepository
                .findByUserIdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(userId, fromTimestamp, toTimestamp,
                        paging);
        for (DeviceLog deviceLog : deviceLogList) {
            userTemperatureList.add(map.userTemperature(deviceLog));
        }
        return userTemperatureList;
    }

    public ListDevice getListDevice(int page, int pageSize, String countOnly) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<Device> deviceList = new ArrayList<>();
        Page<Device> devicePage = deviceRepository.findAll(paging);
        if (countOnly.equals("N")) {
            for (Device device : devicePage) {
                deviceList.add(device);
            }
            return new ListDevice(deviceList);
        }
        return new ListDevice(devicePage.getTotalElements(), devicePage.getTotalPages(), new ArrayList<Device>());
    }

    public Long countDevice() {
        return deviceRepository.count();
    }

    public Pageable getPage(String sortByTemperature, String sortByDate, int page, int pageSize) {
        if (sortByDate.equals("D")) {
            return PageRequest.of(page, pageSize, Sort.by("timestamp").descending());
        }
        if (sortByDate.equals("A")) {
            return PageRequest.of(page, pageSize, Sort.by("timestamp").ascending());
        }
        if (sortByTemperature.equals("D")) {
            return PageRequest.of(page, pageSize, Sort.by("temperature").descending());
        }
        if (sortByTemperature.equals("A")) {
            return PageRequest.of(page, pageSize, Sort.by("temperature").ascending());
        }
        return PageRequest.of(page, pageSize, Sort.by("timestamp").descending());
    }

}
