package org.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {

    private final TvGuideService guide;

    @Autowired
    public JsonController(TvGuideService guide) {
        this.guide = guide;
    }

    @ResponseBody
    @RequestMapping(value = "/api/search")
    public JsonResponse programme(@RequestBody String channel) {
        return new JsonResponse();
    }

}