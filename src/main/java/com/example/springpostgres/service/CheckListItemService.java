package com.example.springpostgres.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.springpostgres.entity.CheckList;
import com.example.springpostgres.entity.CheckListItem;
import com.example.springpostgres.model.CheckListItemResponse;
import com.example.springpostgres.model.CreateCheckListItemRequest;
import com.example.springpostgres.repository.CheckListItemRepository;
import com.example.springpostgres.repository.CheckListRepository;

@Service
public class CheckListItemService {

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private CheckListItemRepository checkListItemRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void create(CreateCheckListItemRequest request, Integer penggunaId){

        validationService.validate(request);

        CheckList checkList = checkListRepository.findFirstByChecklistIdAndPengguna_PenggunaId(request.getCheckListId(), penggunaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Tidak Ditemukan"));

        CheckListItem checkListItem = new CheckListItem();
        checkListItem.setCheckListItemNama(request.getItemName());
        checkListItem.setStatusAktif(true);
        checkListItem.setCheckList(checkList);
        checkListItemRepository.save(checkListItem);

    }

    @Transactional(readOnly = true)
    public List<CheckListItemResponse> getAll(Integer checkListId, Integer penggunaId){

        CheckList checkList = checkListRepository.findFirstByChecklistIdAndPengguna_PenggunaId(checkListId, penggunaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Tidak Ditemukan"));

        
        List<CheckListItem> checkListItems = checkListItemRepository.findAllByCheckList(checkList);

        return checkListItems.stream().map(checkListItem -> toCheckListItemResponse(checkListItem)).toList();
    }

    @Transactional(readOnly = true)
    public CheckListItemResponse getById(Integer checkListId, Integer checkListItemId, Integer penggunaId){

        CheckList checkList = checkListRepository.findFirstByChecklistIdAndPengguna_PenggunaId(checkListId, penggunaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Tidak Ditemukan"));

        CheckListItem checkListItem = checkListItemRepository.findFirstByCheckListAndCheckListItemId(checkList, checkListItemId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Todo Tidak Ditemukan"));

        return toCheckListItemResponse(checkListItem);
    }

    @Transactional
    public void delete(Integer checkListId, Integer checkListItemId, Integer penggunaId){

        CheckList checkList = checkListRepository.findFirstByChecklistIdAndPengguna_PenggunaId(checkListId, penggunaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Tidak Ditemukan"));

        CheckListItem checkListItem = checkListItemRepository.findFirstByCheckListAndCheckListItemId(checkList, checkListItemId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Todo Tidak Ditemukan"));

        checkListItemRepository.delete(checkListItem);
    }

    private CheckListItemResponse toCheckListItemResponse(CheckListItem checkListItem){
        return CheckListItemResponse.builder()
            .checkListItemId(checkListItem.getCheckListItemId())
            .itemName(checkListItem.getCheckListItemNama())
            .statusActive(checkListItem.getStatusAktif())
            .build();
    }

}
