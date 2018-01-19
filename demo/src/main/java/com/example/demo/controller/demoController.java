package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class demoController {
	
	private static final Logger logger = LoggerFactory.getLogger(demoController.class);
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public @ResponseBody String test()	{
		System.out.println("debug test 1");
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
		System.out.println("debug test 2");
		return "test!";
	}
}
