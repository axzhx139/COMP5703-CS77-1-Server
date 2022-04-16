package com.gday.trackmygrocery.controller;

import com.gday.trackmygrocery.dao.pojo.RankingObject;
import com.gday.trackmygrocery.dao.pojo.User;
import com.gday.trackmygrocery.service.RankingService;
import com.gday.trackmygrocery.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ranking")
public class RankingController {
    final Logger logger = LoggerFactory.getLogger(getClass());
    final LogUtils logUtils = LogUtils.getInstance();

    @Autowired
    private RankingService rankingService;

    @GetMapping("/topTen/{address}")
    public List<User> getRankingTopTen(@PathVariable("address") String address) {
        logger.info("getRankingTopTen<<<(address: String): " + address);
        List<User> res = rankingService.getTopTenUsers(address);
        logger.info("getRankingTopTen>>>" + res);
        return res;
    }

    @GetMapping("/previous/{id}/{address}")
    public RankingObject getRankingPreviousOne(@PathVariable("id") int id, @PathVariable("address") String address) {
        logger.info("getRankingPreviousOne<<<(id: int): " + id + "(address: String): " + address);
        RankingObject res = rankingService.getPreviousOne(id, address);
        logger.info("getRankingPreviousOne>>>" + res);
        return res;
    }

}
