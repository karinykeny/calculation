package com.example.controller;

import com.example.domain.DataCalculation;
import com.example.domain.Result;
import com.example.services.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public ModelAndView calculate(@Valid DataCalculation data, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return this.hasErrors(bindingResult,"index");
        }
        try {
            ModelAndView view = new ModelAndView("result");
            Result formResult = service.calculateMonthlyInstallment(data);
            view.addObject("formResult",formResult);
            return view;
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.hasError("index", ex.getMessage());
        }
    }

    @PostMapping()
    public ModelAndView saveResult(@ModelAttribute("formResult") @Valid Result formResult,
                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return this.hasErrors(bindingResult,"result");
        }

        try {
            String data = service.saveResult(formResult);
            ModelAndView view = new ModelAndView("index");
            view.addObject("data",data);
            return view;
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.hasError("result", ex.getMessage());
        }
    }

    private ModelAndView hasErrors(BindingResult bindingResult, String viewName) {
        List<String> msg = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return this.hasError("result", msg);
    }

    private ModelAndView hasError(String viewName, Object msg) {
        ModelAndView view = new ModelAndView(viewName);
        view.addObject("msg",msg);
        return view;
    }

}
