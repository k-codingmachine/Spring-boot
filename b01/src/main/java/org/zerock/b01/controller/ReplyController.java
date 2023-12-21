package org.zerock.b01.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.service.ReplyServcie;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor // final
public class ReplyController {

    private final ReplyServcie replyServcie;

//    @Operation(summary = "Relies POST", description = "POST방식으로 댓글 등록")
//    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, Long>> register(@Valid @RequestBody ReplyDTO replyDTO
//                                                        ,BindingResult bindingResult) throws BindException{
//
//        log.info(replyDTO);
//
//        if(bindingResult.hasErrors()){
//            throw new BindException(bindingResult);
//        }
//
//        Map<String, Long> resultMap = Map.of("rno", 108L);
//
//        return ResponseEntity.ok(resultMap);
//
//    }

    @Operation(summary = "Replies POST", description = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                      BindingResult bindingResult) throws  BindException{

        log.info(replyDTO);

        if (bindingResult.hasErrors()){
            throw  new BindException(bindingResult);
        }
        Map<String, Long>resultMap = new HashMap<>();
        Long rno = replyServcie.register(replyDTO);

        resultMap.put("rno", rno);

        return resultMap;
    }
    @Operation(summary = "Replies POST", description = "POST 방식으로 댓글 등록")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO){
        return replyServcie.getListOfBoard(bno, pageRequestDTO);
    }

    @Operation(summary = "Read Reply", description = "GET 방식으로 댓글 조회")
    @GetMapping(value = "/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno){
        return replyServcie.read(rno);
    }


}
