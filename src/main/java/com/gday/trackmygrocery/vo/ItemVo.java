package com.gday.trackmygrocery.vo;

import com.gday.trackmygrocery.dao.pojo.Item;
import lombok.Data;

@Data
public class ItemVo extends Item {
    private boolean isPotential;
}
