package com.example.springpostgres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springpostgres.model.CheckListResponse;
import com.example.springpostgres.model.CreateCheckListRequest;
import com.example.springpostgres.model.CreateCheckListWithItemsRequst;
import com.example.springpostgres.model.UpdateCheckListRequst;
import com.example.springpostgres.model.WebResponse;
import com.example.springpostgres.security.CustomWebAuthenticationDetails;
import com.example.springpostgres.service.CheckListService;

@RestController
public class CheckListController {

    @Autowired
    private CheckListService checkListService;

    @PostMapping(
        path = "/checklist",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> create(@RequestBody CreateCheckListRequest request){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        request.setPenggunaId(details.getPenggunaId());
        checkListService.create(request);

        return WebResponse.<String>builder().code(HttpStatus.OK.value()).message("OK").build();

    }

    @GetMapping(
        path = "/checklist",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CheckListResponse>> getAll(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit, @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Integer penggunaId = details.getPenggunaId();

        Page<CheckListResponse> checkListResponse = checkListService.getAll(penggunaId, limit, offset);

        return WebResponse.<List<CheckListResponse>>builder().code(HttpStatus.OK.value()).message("OK").data(checkListResponse.getContent()).limit(limit).offset(offset).total(checkListResponse.getTotalElements()).build();

    }

    @PutMapping(
        path = "/checklist/{checklistId}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CheckListResponse> update(@PathVariable("checklistId") Integer checklistId, @RequestBody UpdateCheckListRequst request){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Integer penggunaId = details.getPenggunaId();

        request.setChecklistId(checklistId);
        request.setPenggunaId(penggunaId);

        CheckListResponse update = checkListService.update(request);

        return WebResponse.<CheckListResponse>builder().code(HttpStatus.OK.value()).message("OK").data(update).build();
    }
    
    @DeleteMapping(
        path = "checklist/{checkListId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteById(@PathVariable("checkListId") Integer checkListId){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Integer penggunaId = details.getPenggunaId();

        checkListService.deleteCheckListByID(checkListId, penggunaId);

        return WebResponse.<String>builder().code(HttpStatus.OK.value()).message("OK").build();

    }

    @PostMapping(
        path = "/checklistwithitems",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> createWithItems(@RequestBody CreateCheckListWithItemsRequst request){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Integer penggunaId = details.getPenggunaId();
        request.setPenggunaId(penggunaId);
        
        checkListService.createWithItems(request);

        return WebResponse.<String>builder().build();

    }

}
