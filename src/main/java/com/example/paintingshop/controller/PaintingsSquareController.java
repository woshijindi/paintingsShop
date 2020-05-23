package com.example.paintingshop.controller;


import com.example.paintingshop.dto.LookUpDTO;
import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.mapper.PaintingsMapper;
import com.example.paintingshop.model.Paintings;
import com.example.paintingshop.model.PaintingsExample;
import com.example.paintingshop.service.PaintingsService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PaintingsSquareController {


    @Autowired
    private PaintingsMapper paintingsMapper;

    @Autowired
    private PaintingsService paintingsService;

    @GetMapping("/paintingsSquare")
    public String paintings(Model model,
                            @RequestParam(name = "page", defaultValue = "1") Integer page,
                            @RequestParam(name = "size", defaultValue = "24") Integer size) {

        PaginationDTO paginationPaintings = paintingsService.list(page, size);
        model.addAttribute("pagination", paginationPaintings);
        model.addAttribute("lookUp", 10);

        return "paintingsSquare";
    }

    @GetMapping("/paintingsSquare/type/{id}/{message}")
    public String lookUpByType(Model model,
                               @PathVariable(name = "id") Integer id,
                               @PathVariable(name = "message") String message,
                               @RequestParam(name = "page", defaultValue = "1") Integer page,
                               @RequestParam(name = "size", defaultValue = "24") Integer size) {

        PaginationDTO paginationPaintings = paintingsService.listByMessage(id,message,page, size);
        model.addAttribute("pagination", paginationPaintings);
        model.addAttribute("lookUp", 0);
        model.addAttribute("id",id);
        model.addAttribute("message",message);

        return "paintingsSquare";
    }

    @GetMapping("/paintingsSquare/style/{id}/{message}")
    public String lookUpByStyle(Model model,
                                @PathVariable(name = "id") Integer id,
                                @PathVariable(name = "message") String message,
                                @RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "size", defaultValue = "24") Integer size) {

        PaginationDTO paginationPaintings = paintingsService.listByMessage(id,message,page, size);
        model.addAttribute("pagination", paginationPaintings);
        model.addAttribute("lookUp", 1);
        model.addAttribute("id",id);
        model.addAttribute("message",message);

        return "paintingsSquare";
    }

    @GetMapping("/paintingsSquare/method/{id}/{message}")
    public String lookUpByMethod(Model model,
                                 @PathVariable(name = "id") Integer id,
                                 @PathVariable(name = "message") String message,
                                 @RequestParam(name = "page", defaultValue = "1") Integer page,
                                 @RequestParam(name = "size", defaultValue = "24") Integer size) {

        PaginationDTO paginationPaintings = paintingsService.listByMessage(id,message,page, size);
        model.addAttribute("pagination", paginationPaintings);
        model.addAttribute("lookUp", 2);
        model.addAttribute("id",id);
        model.addAttribute("message",message);

        return "paintingsSquare";
    }


}
