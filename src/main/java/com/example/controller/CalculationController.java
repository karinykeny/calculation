package com.example.controller;

import com.example.domain.DataCalculation;
import com.example.domain.Result;
import com.example.services.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/simulacao")
public class CalculationController {

    @Autowired
    CalculationService service;

    @GetMapping
    public String pegeIndex() {
        return "index";
    }

    @RequestMapping(value = "/resultado", method = RequestMethod.POST)
    public ModelAndView calculate(DataCalculation data, BindingResult result) {
        ModelAndView view;
        if(result.hasErrors()) {
            view = new ModelAndView("index");
            return view;
        }
        view = new ModelAndView("result");
        Result formResult = service.calculateMonthlyInstallment(data);
        view.addObject("formResult",formResult);
        return view;
    }

    @PostMapping()
    public ModelAndView saveResult(@ModelAttribute("formResult") Result formResult,
                                   BindingResult bindingResult) throws IOException {
        ModelAndView view;
        if(bindingResult.hasErrors()) {
            view = new ModelAndView("result");
            return view;
        }
        view = new ModelAndView("index");
        String data = service.saveResult(formResult);
        view.addObject("data",data);
        return view;
    }

}
