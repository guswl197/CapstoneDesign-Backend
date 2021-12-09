package com.capstone.danjinae.vehicle.controller;

import com.capstone.danjinae.post.DTO.postDTO.PostResponse;
import com.capstone.danjinae.post.entity.Post;
import com.capstone.danjinae.user.service.UserService;
import com.capstone.danjinae.vehicle.DTO.VehicleInfoResponse;
import com.capstone.danjinae.vehicle.DTO.VehicleRequest;
import com.capstone.danjinae.vehicle.DTO.VehicleResponse;
import com.capstone.danjinae.vehicle.entity.Vehicle;
import com.capstone.danjinae.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.function.Function;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    // 차량 등록
    @PostMapping("/resident")
    public Boolean inputResident(@RequestBody VehicleRequest vehicle) {

        Vehicle toadd;
        try {
            toadd = Vehicle.builder().userId(vehicle.getUserId()).phone(vehicle.getPhone())
                    .startDate(new Timestamp(vehicle.getStartDate().getTime()))
                    .endDate(new Timestamp(vehicle.getEndDate().getTime())).number(vehicle.getNumber()).build();

            vehicleService.writeResidnet(toadd);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // 게스트 차량 등록
    @PostMapping("/guest")
    public Boolean inputGuest(@RequestBody VehicleRequest vehicle) {

        Vehicle toadd;
        try {
            toadd = Vehicle.builder().userId(vehicle.getUserId()).phone(vehicle.getPhone())
                    .startDate(new Timestamp(vehicle.getStartDate().getTime()))
                    .endDate(new Timestamp(vehicle.getEndDate().getTime())).number(vehicle.getNumber()).build();

            vehicleService.writeGuest(toadd);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // 선택된 차량 리스트
    @GetMapping("/select/list")
    public Page<VehicleResponse> selectList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("number") String number) {
        Page<VehicleResponse> dtoList;
        Page<Vehicle> list = null;

        list = vehicleService.search(number, pageable);

        dtoList = list.map(new Function<Vehicle, VehicleResponse>() {
            @Override
            public VehicleResponse apply(Vehicle entity) {
                VehicleResponse dto = new VehicleResponse();
                dto.setVehicleId(entity.getId());
                dto.setUserId(entity.getUserId());
                dto.setPhone(entity.getPhone());
                dto.setGuest(entity.getGuest());
                dto.setStartDate(entity.getStartDate());
                dto.setEndDate(entity.getEndDate());
                dto.setNumber(entity.getNumber());
                return dto;
            }
        });
        return dtoList;
    }

    // 선택된 차량 세부정보
    @GetMapping("/select/info")
    public VehicleInfoResponse selectInfo(@RequestParam("id") Integer id) {

        Vehicle vehicle = vehicleService.get(id);
        VehicleInfoResponse dto = new VehicleInfoResponse();

        dto.setVehicleId(vehicle.getId());
        dto.setAddress(userService.getUser(vehicle.getUserId()).getAddress());
        dto.setPhone(vehicle.getPhone());
        dto.setGuest(vehicle.getGuest());
        dto.setStartDate(vehicle.getStartDate());
        dto.setEndDate(vehicle.getEndDate());
        dto.setNumber(vehicle.getNumber());
        return dto;
    }

    //전체 차량 리스트
    @GetMapping("/total-list")
    public Page<VehicleResponse> totalList(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<VehicleResponse> dtoList;
        Page<Vehicle> list = null;

        list = vehicleService.totalVehicleList(pageable);

        dtoList = list.map(new Function<Vehicle, VehicleResponse>() {
            @Override
            public VehicleResponse apply(Vehicle entity) {
                VehicleResponse dto = new VehicleResponse();
                dto.setVehicleId(entity.getId());
                dto.setUserId(entity.getUserId());
                dto.setPhone(entity.getPhone());
                dto.setGuest(entity.getGuest());
                dto.setStartDate(entity.getStartDate());
                dto.setEndDate(entity.getEndDate());
                dto.setNumber(entity.getNumber());
                return dto;
            }
        });
        return dtoList;
    }

    //차량 삭제
    @DeleteMapping("/delete")
    public Page<VehicleResponse> deleteVehicle(@RequestParam("id") Integer id, @PageableDefault(page=0,size=10,sort="id",direction= Sort.Direction.DESC) Pageable pageable) {

        vehicleService.deleteVehicle(id);

        Page<VehicleResponse> dtoList;
        Page<Vehicle> list = null;
        list = vehicleService.totalVehicleList(pageable);

        dtoList = list.map(new Function<Vehicle, VehicleResponse>() {
            @Override
            public VehicleResponse apply(Vehicle entity) {
                VehicleResponse dto = new VehicleResponse();
                dto.setVehicleId(entity.getId());
                dto.setUserId(entity.getUserId());
                dto.setPhone(entity.getPhone());
                dto.setGuest(entity.getGuest());
                dto.setStartDate(entity.getStartDate());
                dto.setEndDate(entity.getEndDate());
                dto.setNumber(entity.getNumber());
                return dto;
            }
        });
        return dtoList;
    }
}

