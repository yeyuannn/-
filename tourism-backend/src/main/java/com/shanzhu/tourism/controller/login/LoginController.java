package com.shanzhu.tourism.controller.login;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shanzhu.tourism.domain.*;
import com.shanzhu.tourism.service.*;
import com.shanzhu.tourism.utils.JwtUtil;
import com.shanzhu.tourism.utils.PasswordUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 登陆controller
 */
@Controller
@ResponseBody
@RequestMapping("login")
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private SysHotelOrderService sysHotelOrderService;
    @Resource
    private SysAttractionOrderService sysAttractionOrderService;

    @Resource
    private SysAttractionsService sysAttractionsService;

    @Resource
    private SysLineService lineService;

    @Resource
    private SysHotelService sysHotelService;


    @PostMapping()
    public Result login(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("loginAccount");
        String password = jsonObject.getString("password");
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getLoginAccount, username);
        User user = userService.getOne(query);
        if (user == null) {
            return Result.fail("用户名不存在！");
        }
        //比较加密后得密码
        boolean decrypt = PasswordUtils.decrypt(password, user.getPassword() + "$" + user.getSalt());
        if (!decrypt) {
            return Result.fail("用户名或密码错误！");
        }
        if (user.getStatus() == 1) {
            return Result.fail("用户被禁用！");
        }
        //密码正确生成token返回
        String token = JwtUtil.sign(user.getId(), user.getPassword());
        JSONObject json = new JSONObject();
        json.put("token", token);
        return Result.success(json);
    }

    @GetMapping("logout")
    public Result logout() {
        return Result.success();
    }

    @GetMapping("getManageData")
    public Result getManageData() {
        JSONObject jsonObject = new JSONObject();

        List<String> dates = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        List<Integer> orders = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 7; i++) {
            Date date = calendar.getTime();
            String formattedDate = dateFormat.format(date);
            //最近七日景点预约
            QueryWrapper<SysAttractionOrder> wrapper = new QueryWrapper<>();
            wrapper.lambda().ge(SysAttractionOrder::getCreateTime, formattedDate + " 00:00:00")
                    .le(SysAttractionOrder::getCreateTime, formattedDate + " 23:59:59");
            nums.add(sysAttractionOrderService.count(wrapper));
            //最近七日酒店预约
            QueryWrapper<SysHotelOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().ge(SysHotelOrder::getCreateTime, formattedDate + " 00:00:00")
                    .le(SysHotelOrder::getCreateTime, formattedDate + " 23:59:59");
            orders.add(sysHotelOrderService.count(queryWrapper));
            dates.add(formattedDate);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }

        jsonObject.put("dates", dates);
        jsonObject.put("nums", nums);
        jsonObject.put("orders", orders);
        return Result.success(jsonObject);
    }

    /**
     * 景点预约人数统计
     */
    @GetMapping("attractionOrderChart")
    public Result attractionOrderChart() {
        JSONObject jsonObject = new JSONObject();
        List<String> names = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();

        List<SysAttractionOrder> attractionOrderList = sysAttractionOrderService.list();
        Map<String, List<SysAttractionOrder>> attractionOrderListMap = attractionOrderList.stream()
                .collect(Collectors.groupingBy(
                        SysAttractionOrder::getName
                ));

        attractionOrderListMap.forEach((k,v)->{
            names.add(k);
            nums.add(v.size());
        });

        jsonObject.put("names", names);
        jsonObject.put("nums", nums);
        return Result.success(jsonObject);
    }

    /**
     * 酒店预约人数统计
     */
    @GetMapping("hotelOrderChart")
    public Result hotelOrderChart() {
        JSONObject jsonObject = new JSONObject();
        List<String> names = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();

        List<SysHotelOrder> hotelOrderList = sysHotelOrderService.list();
        Map<String, List<SysHotelOrder>> hotelOrderListMap = hotelOrderList.stream()
                .collect(Collectors.groupingBy(
                        SysHotelOrder::getName
                ));

        hotelOrderListMap.forEach((k,v)->{
            names.add(k);
            nums.add(v.size());
        });

        jsonObject.put("names", names);
        jsonObject.put("nums", nums);
        return Result.success(jsonObject);
    }

    @GetMapping("countChart")
    public Result countChart() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userCount", userService.count());
        jsonObject.put("attractionsCount", sysAttractionsService.count());
        jsonObject.put("lineCount", lineService.count());
        jsonObject.put("hotelCount", sysHotelService.count());

        return Result.success(jsonObject);

    }

}
