package com.example.springpostgres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springpostgres.model.CheckListItemResponse;
import com.example.springpostgres.model.CreateCheckListItemRequest;
import com.example.springpostgres.model.WebResponse;
import com.example.springpostgres.security.CustomWebAuthenticationDetails;
import com.example.springpostgres.service.CheckListItemService;

@RestController
public class CheckListItemController {

    @Autowired
    private CheckListItemService checkListItemService;

    @PostMapping(
        path = "/checklist/{checkListId}/item",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> create(@RequestBody CreateCheckListItemRequest request, @PathVariable("checkListId") Integer checkListId){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        
        Integer penggunaId = details.getPenggunaId();   
        request.setCheckListId(checkListId);
        checkListItemService.create(request, penggunaId);

        return WebResponse.<String>builder().code(HttpStatus.OK.value()).message("OK").build();
    }

    @GetMapping(
        path = "/checklist/{checkListId}/item",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CheckListItemResponse>> getAll(@PathVariable("checkListId") Integer checkListId){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Integer penggunaId = details.getPenggunaId();
        List<CheckListItemResponse> checkListItemResponses = checkListItemService.getAll(checkListId, penggunaId);

        return WebResponse.<List<CheckListItemResponse>>builder().code(HttpStatus.OK.value()).message("OK").data(checkListItemResponses).build();

    }

    @GetMapping(
        path = "/checklist/{checkListId}/item/{checkListItemId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CheckListItemResponse> getById(@PathVariable("checkListId") Integer checkListId, @PathVariable("checkListItemId") Integer checkListItemId){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Integer penggunaId = details.getPenggunaId();
        CheckListItemResponse checkListItemResponse = checkListItemService.getById(checkListId, checkListItemId, penggunaId);

        return WebResponse.<CheckListItemResponse>builder().code(HttpStatus.OK.value()).message("OK").data(checkListItemResponse).build();

    }

    @DeleteMapping(
        path = "/checklist/{checkListId}/item/{checkListItemId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("checkListId") Integer checkListId, @PathVariable("checkListItemId") Integer checkListItemId){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Integer penggunaId = details.getPenggunaId();
        checkListItemService.delete(checkListId, checkListItemId, penggunaId);

        return WebResponse.<String>builder().code(HttpStatus.OK.value()).message("OK").build();

    }

}
