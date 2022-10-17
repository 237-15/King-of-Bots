package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RestController
@RequestMapping("/pk/")

public class BotInfoController {

    @RequestMapping("getBotInfo/")
    public Map getBotInfo() {
//        LinkedList<String> Link;
//        Link = new LinkedList<>();
//        Link.add("lhy");
//        Link.add("lqh");
//        Link.add("永远在一起啦");
        Map<String, String> map;
        map = new HashMap<>();
        map.put("name", "lhy");
        map.put("rating", "250");
        return map;
    }
}
