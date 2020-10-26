package com.example.demo.controller.device;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.model.device.Device;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.device.DeviceLogDto;
import com.example.demo.dto.device.DeviceLogSubmitDto;
import com.example.demo.dto.user.UserTemperature;
import com.example.demo.repository.device.DeviceRepository;
import com.example.demo.service.device.DeviceService;
import com.example.demo.service.message.Message;
import com.example.demo.service.message.ResponseMessage;


@RestController
@RequestMapping("/rest/device/")
public class DeviceRestController {

    private final DeviceService deviceService;

    public DeviceRestController(DeviceRepository deviceRepository, DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public Object submit(HttpServletResponse response,@Validated @RequestBody DeviceLogSubmitDto deviceLogSubmitDto, BindingResult result) {
    	if(result.hasErrors()) {    	
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.MISS_INFORMATION);
    		return reMessage;
    	}
    	
        if (deviceService.submitLog(deviceLogSubmitDto)) {
        	response.setStatus(HttpServletResponse.SC_OK);
          	
        	ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.SUCCESS);
        	return reMessage;
        }
       
        ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.SUBMIT_FAILURE);
        return reMessage;        
    }

   

	@RequestMapping(value = "list/log", method = RequestMethod.GET)
    public List<DeviceLogDto> listDeviceLog(@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
                                            @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize
    ) {
        return deviceService.getList(pageIndex, pageSize);
    }

    @RequestMapping(value = "list/user/temperature", method = RequestMethod.GET)
    public List<UserTemperature> listTemperatureOfUser(@RequestParam(name = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                                       @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize,
                                                       @RequestParam(name = "fromTimestamp") Long fromTimestamp,
                                                       @RequestParam(name = "toTimestamp") Long toTimestamp,
                                                       @RequestParam(name = "userId") String userId) {
        return deviceService.getListByTimeInterval(userId, fromTimestamp, toTimestamp, pageIndex, pageSize);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Page<Device> listDevice(@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize
    ) {
        return deviceService.getListDevice(pageIndex, pageSize);
    }
}
