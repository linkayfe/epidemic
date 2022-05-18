package com.kayfe.handler;


import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class EpidemicGenerator implements IdentifierGenerator {

    private final AtomicLong al = new AtomicLong(1);
    @Override
    public Number nextId(Object entity) {
        final long id = al.getAndAdd(1);
        return id;
    }
}
