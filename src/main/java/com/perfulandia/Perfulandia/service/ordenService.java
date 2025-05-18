package com.perfulandia.Perfulandia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ordenService {
    @Autowired
    private itemCarritoService itemCarritoService;
}
