package com.example.springpostgres.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.springpostgres.entity.CheckList;
import com.example.springpostgres.entity.CheckListItem;
import com.example.springpostgres.entity.Pengguna;
import com.example.springpostgres.model.CheckListResponse;
import com.example.springpostgres.model.CreateCheckListRequest;
import com.example.springpostgres.model.CreateCheckListWithItemsRequst;
import com.example.springpostgres.model.UpdateCheckListRequst;
import com.example.springpostgres.repository.CheckListItemRepository;
import com.example.springpostgres.repository.CheckListRepository;
import com.example.springpostgres.repository.PenggunaRepository;

@Service
public class CheckListService {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private CheckListItemRepository checkListItemRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void create(CreateCheckListRequest request){

        validationService.validate(request);

        Pengguna pengguna = penggunaRepository.findById(request.getPenggunaId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengguna tidak ditemukan"));
        
        CheckList checkList = new CheckList();
        checkList.setCheckListNama(request.getName());
        checkList.setPengguna(pengguna);
        checkListRepository.save(checkList);

    }

    @Transactional(readOnly = true)
    public Page<CheckListResponse> getAll(Integer penggunaId, Integer limit, Integer offset){

        Pageable pageable = PageRequest.of(offset, limit);
        Page<CheckList> checkLists = checkListRepository.findAllByPengguna_PenggunaId(penggunaId, pageable);
        List<CheckListResponse> checkListResponses = checkLists.getContent().stream()
            .map(checkList -> toCheckListResponse(checkList)).collect(Collectors.toList());

        return new PageImpl<>(checkListResponses, pageable, checkLists.getTotalElements());
    }

    @Transactional
    public CheckListResponse update(UpdateCheckListRequst requst){

        CheckList checkList = checkListRepository.findFirstByChecklistIdAndPengguna_PenggunaId(requst.getChecklistId(), requst.getPenggunaId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo tidak ditemukan"));

        checkList.setCheckListNama(requst.getName());
        checkListRepository.save(checkList);

        return toCheckListResponse(checkList);
        
    }

    @Transactional
    public void deleteCheckListByID(Integer checkListId, Integer penggunaId){

        CheckList checkList = checkListRepository.findFirstByChecklistIdAndPengguna_PenggunaId(checkListId, penggunaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo tidak ditemukan"));

        checkListRepository.delete(checkList);
    }

    @Transactional
    public void createWithItems(CreateCheckListWithItemsRequst request){

        validationService.validate(request);

        Pengguna pengguna = penggunaRepository.findById(request.getPenggunaId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengguna tidak ditemukan"));

        CheckList checkList = new CheckList();
        checkList.setCheckListNama(request.getName());
        checkList.setPengguna(pengguna);
        checkListRepository.save(checkList);

        // Integer checkListId = checkList.getChecklistId();
        List<CheckListItem> items = new ArrayList<>();

        for (int index = 0; index < request.getItems().size(); index++) {
            
            CheckListItem item = new CheckListItem();
            item.setCheckList(checkList);
            item.setCheckListItemNama(request.getItems().get(index).getItemName());
            item.setStatusAktif(true);
            items.add(item);
        }

        checkListItemRepository.saveAll(items);
        
    }


    public CheckListResponse toCheckListResponse(CheckList checkList){
        return CheckListResponse.builder()
                .checkListId(checkList.getChecklistId())
                .name(checkList.getCheckListNama())
                .build();
    }



}
