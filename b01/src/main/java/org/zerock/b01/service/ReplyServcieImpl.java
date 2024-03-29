package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Reply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServcieImpl implements ReplyServcie{

    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;


    @Override
    public Long register(ReplyDTO replyDTO) {

        Reply reply = modelMapper.map(replyDTO, Reply.class);


        return replyRepository.save(reply).getRno();
    }

    @Override
    public ReplyDTO read(Long rno) {

       Reply reply = replyRepository.findById(rno).orElseThrow();

        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {

       // 댓글이 존재하는지?
       Reply reply = replyRepository.findById(replyDTO.getBno()).orElseThrow();

        // 댓글 존재 -> 댓글 내용만 변경
        reply.changeText(replyDTO.getReplyText());

       // 변경 내용 저장
       replyRepository.save(reply);

    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {

       Pageable pageable = PageRequest.of(  pageRequestDTO.getPage()<0 ? 0:pageRequestDTO.getPage()-1,
                                pageRequestDTO.getSize()
                                , Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        List<ReplyDTO> dtoList = 
            result.getContent().stream().map(reply -> modelMapper.map(reply,ReplyDTO.class))
                    .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }


}
