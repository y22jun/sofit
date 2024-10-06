package sofit.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sofit.demo.dto.board.BoardInfoDto;
import sofit.demo.dto.board.BoardSaveDto;
import sofit.demo.dto.board.BoardUpdateDto;
import sofit.demo.global.template.ResponseTemplate;
import sofit.demo.service.board.BoardService;

@RestController
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;

    @PostMapping("/board/save")
    public void save(@RequestPart BoardSaveDto boardSaveDto, @RequestPart(required = false) MultipartFile multipartFile) {
        boardService.save(boardSaveDto, multipartFile);
    }

    @PostMapping("/board/{boardId}")
    public void update(@PathVariable("boardId") Long boardId, @RequestBody BoardUpdateDto boardUpdateDto, @RequestPart(required = false) MultipartFile multipartFile) {
        boardService.update(boardId, boardUpdateDto, multipartFile);
    }

    @DeleteMapping("/board/{boardId}")
    public void delete(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
    }

    @GetMapping("board/{boardId}")
    public ResponseEntity<BoardInfoDto> getInfo(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardService.getBoardInfo(boardId));
    }

    @GetMapping("/board/all")
    public ResponseTemplate<List<BoardInfoDto>> getAllBoards() {
        return new ResponseTemplate<>(HttpStatus.OK, "보드 전체 정보 조회 성공", (boardService.getAllBoards()));
    }

    @GetMapping("/board/search")
    public Page<BoardInfoDto> searchBoardsByTitle(@RequestParam String keyword, Pageable pageable) {
        return boardService.searchingBoardList(keyword, pageable);
    }
}
