package com.boot.board_240718.controller;

import com.boot.board_240718.model.Board;
import com.boot.board_240718.repository.BoardRepository;
import com.boot.board_240718.service.BoardService;
import com.boot.board_240718.validator.BoardValidator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,@RequestParam(required = false, defaultValue = "") String searchText){
        log.info("@# list==>");
//        List<Board> boards = boardRepository.findAll();
//        Page<Board> boards = boardRepository.findAll(PageRequest.of(0, 20));
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContent(searchText, searchText, pageable);
        // 최종 : 5개 이전페이지 표시 + 5개 이후페이지 표시
        // 음수가오면안되기떄문에 시작을 0 으로 줌
//        int startPage = Math.max(0,boards.getPageable().getPageNumber() -4);
        int startPage = Math.max(1,boards.getPageable().getPageNumber() -4);
        // 마지막페이지부터 +4를 시켜야 뒤로 5가나오기때문
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() +4);
        log.info("@# boards pageable==>"+pageable);
        log.info("@# boards==>"+boards);
//        Page<Board> boards = boardRepository.findAll(PageRequest.of(1, 20));
//        boards.getTotalElements() 총 건수
        
        model.addAttribute("boards",boards);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "board/list";
    }

    @GetMapping("/form")
//    public String form(Model model){
//    public String form(Model model, @RequestParam Long id){
    public String form(Model model, @RequestParam(required = false) Long id){
        log.info("@@ GetMapping form()");
        log.info("@@ id >> "+id);

        if (id != null){
//            Optional<Board> board = boardRepository.findById(id);  // 널포인트익셉션 오류를 막기위해 optional사용
            Board board = boardRepository.findById(id).orElse(null); // 조회시점에 데이터삭제 등 없는 경우 null처리
            model.addAttribute("board",board);
            log.info("@@ board >> "+board);
        }else {
            // id값이 없으니 보드 객체만 들고감
            model.addAttribute("board", new Board());
        }
        return "board/form";
    }

    @PostMapping("/form")
//    public String form(@ModelAttribute Board board, Model model) {
        public String form(@Valid Board board, BindingResult bindingResult) {
        // 서버단에서 validation 체크
        boardValidator.validate(board, bindingResult);

        // 오류가 났을때 return을 form으로 한다
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        // 시큐리티에서 사용자정보 가져오는 방법
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
//        boardRepository.save(board);
        boardService.save(username, board);
        
        return "redirect:/board/list";
    }
}
