package com.example.demo.service.device;

import com.example.demo.dto.Map;
import com.example.demo.dto.temperature.ListEmailConfigDto;
import com.example.demo.dto.temperature.TemperatureConfigDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.model.conf.EmailConfig;
import com.example.demo.model.conf.Temperature;
import com.example.demo.model.device.Device;
import com.example.demo.model.device.DeviceLog;
import com.example.demo.repository.conf.EmailConfigRepository;
import com.example.demo.repository.conf.TemperatureRepository;
import com.example.demo.repository.device.DeviceLogRepository;
import com.example.demo.repository.device.DeviceRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.auth.AuthenticationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TemperatureConfService {
    
    private final TemperatureRepository temperatureRepository;
    private final EmailConfigRepository emailConfigRepository;


    @Autowired
    public TemperatureConfService(DeviceRepository deviceRepository, AuthenticationService authenticationService,
                                  DeviceLogRepository deviceLogRepository, Map map, TemperatureRepository temperatureRepository,
                                  EmailConfigRepository emailConfigRepository) {
        
        this.temperatureRepository = temperatureRepository;
        this.emailConfigRepository = emailConfigRepository;
    }


    public boolean Config(TemperatureConfigDto temperatureConfigDto) {
        if (temperatureConfigDto.getEmails().isEmpty()) {
            return false;
        }                 
         
        Temperature temperatureConfig = temperatureRepository.findById(1);
        if (temperatureConfig == null ) {
        	Temperature temperature = new Temperature();
        	temperature.setTemperature(temperatureConfigDto.getTemperature());
        	temperatureRepository.save(temperature);
        }

        temperatureConfig.setTemperature(temperatureConfigDto.getTemperature());
        temperatureRepository.save(temperatureConfig);
        
        String[] emailArray = temperatureConfigDto.getEmails().split(";");       
        
        emailConfigRepository.deleteAll();
        
        for (String email : emailArray) {
        	
               EmailConfig emailConfig = new EmailConfig();
               emailConfig.setTemperature(temperatureConfig);
               emailConfig.setEmail(email);               
               emailConfigRepository.save(emailConfig);   

        }
        return true;
    }

    public ListEmailConfigDto getListConfigEmail(int page, int pageSize, String countOnly){
        Pageable paging = PageRequest.of(page, pageSize);
        List<EmailConfig> emailConfigs = new ArrayList<>();
        Page<EmailConfig> emailConfigPage = emailConfigRepository.findAll(paging);
        if (countOnly.equals("N")){
            for (EmailConfig emailConfig :
                    emailConfigPage) {
                emailConfigs.add(emailConfig);
            }
            return new ListEmailConfigDto(emailConfigs);
        }
        return new ListEmailConfigDto(emailConfigPage.getTotalElements(), emailConfigPage.getTotalPages(), new ArrayList<EmailConfig>());
    }


}
