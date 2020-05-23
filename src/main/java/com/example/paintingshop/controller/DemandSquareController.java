package com.example.paintingshop.controller;


import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.enums.DemandPriceEnum;
import com.example.paintingshop.enums.DemandPurposeEnum;
import com.example.paintingshop.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemandSquareController {

    @Autowired
    private DemandService demandService;

    @GetMapping("/demandSquare")
    public String demand(Model model,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "10") Integer size) {

        PaginationDTO paginationDemand = demandService.list(page, size);
        model.addAttribute("pagination", paginationDemand);
        model.addAttribute("lookUp", 10);

        return "demandSquare";
    }

    @GetMapping("/demandSquare/purpose/{id}/{message}")
    public String demandByPurpose(Model model,
                                  @PathVariable(name = "id") Integer id,
                                  @PathVariable(name = "message") Integer messageId,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {

        String message = DemandPurposeEnum.getValueByCode(messageId);

        PaginationDTO paginationDemand = demandService.listByMessage(id, message, page, size);
        model.addAttribute("pagination", paginationDemand);
        model.addAttribute("lookUp", 0);
        model.addAttribute("id", id);
        model.addAttribute("messageId", messageId);

        return "demandSquare";
    }

    @GetMapping("/demandSquare/price/{id}/{message}")
    public String demandByPrice(Model model,
                                @PathVariable(name = "id") Integer id,
                                @PathVariable(name = "message") Integer messageId,
                                @RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "size", defaultValue = "10") Integer size) {

        String message = DemandPriceEnum.getValueByCode(messageId);

        PaginationDTO paginationDemand = demandService.listByMessage(id, message, page, size);
        model.addAttribute("pagination", paginationDemand);
        model.addAttribute("lookUp", 1);
        model.addAttribute("id", id);
        model.addAttribute("messageId", messageId);
        return "demandSquare";
    }


}
