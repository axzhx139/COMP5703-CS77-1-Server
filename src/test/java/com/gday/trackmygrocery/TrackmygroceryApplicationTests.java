package com.gday.trackmygrocery;

import com.gday.trackmygrocery.service.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrackmygroceryApplicationTests {

    @Autowired
    private RankingService rankingService;

    @Test
    void contextLoads() {
        rankingService.getPreviousOne(1,null);
    }

}
