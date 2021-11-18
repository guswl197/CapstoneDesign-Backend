package com.capstone.danjinae.MgFee.controller;

import java.util.function.Function;

import com.capstone.danjinae.MgFee.DTO.NewMgFeeRequest;
import com.capstone.danjinae.MgFee.DTO.UserMgFeeResponse;
import com.capstone.danjinae.MgFee.entity.MgFee;
import com.capstone.danjinae.MgFee.service.MgFeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/mgfee")
public class MgFeeController {

    @Autowired
    private MgFeeService mgFeeService;

    @PutMapping("/userispaid/{mgfeeid}")
    public Boolean UserPayMoney(@PathVariable Integer mgfeeid) {
        MgFee mgfee = mgFeeService.getMgFee(mgfeeid);
        mgfee.paid();
        return true;
    }

    @GetMapping("/getmgfee")
    public Page<UserMgFeeResponse> userMgFeeList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "userId") Integer userId, @RequestParam(value = "aptId") Integer aptId) {

        Page<UserMgFeeResponse> dtolist;
        try {
            Page<MgFee> entitylist = mgFeeService.getUserMgFeeList(userId, aptId, pageable);

            dtolist = entitylist.map(new Function<MgFee, UserMgFeeResponse>() {
                @Override
                public UserMgFeeResponse apply(MgFee entity) {
                    UserMgFeeResponse dto = new UserMgFeeResponse();
                    dto.setId(entity.getId());
                    dto.setDate(entity.getDate());
                    dto.setFee(entity.getFee());
                    dto.setPaid(entity.getPaid());
                    dto.setUserId(entity.getUserId());
                    dto.setAptId(entity.getAptId());
                    dto.setCatId(entity.getCatId());
                    return dto;
                }
            });

        } catch (Exception e) {
            return null;
        }

        return dtolist;
    }

    @GetMapping("/getmanagermgfee")
    public Page<UserMgFeeResponse> managerMgFeeList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "aptId") Integer aptId) {

        Page<UserMgFeeResponse> dtolist;
        try {
            Page<MgFee> entitylist = mgFeeService.getManagerMgFeeList(aptId, pageable);

            dtolist = entitylist.map(new Function<MgFee, UserMgFeeResponse>() {
                @Override
                public UserMgFeeResponse apply(MgFee entity) {
                    UserMgFeeResponse dto = new UserMgFeeResponse();
                    dto.setId(entity.getId());
                    dto.setDate(entity.getDate());
                    dto.setFee(entity.getFee());
                    dto.setPaid(entity.getPaid());
                    dto.setUserId(entity.getUserId());
                    dto.setAptId(entity.getAptId());
                    dto.setCatId(entity.getCatId());
                    return dto;
                }
            });

        } catch (Exception e) {
            return null;
        }

        return dtolist;
    }

    @PostMapping(value = "/setManagerMgFee")
    public Boolean addNewMgFee(@RequestBody NewMgFeeRequest request) {
        MgFee toadd;
        try {
            toadd = MgFee.builder().fee(request.getFee()).userId(request.getUserId()).aptId(request.getAptId())
                    .catId(request.getCatId()).build();

            mgFeeService.write(toadd);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}