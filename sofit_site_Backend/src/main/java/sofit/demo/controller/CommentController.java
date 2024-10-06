package sofit.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sofit.demo.dto.board.BoardInfoDto;
import sofit.demo.dto.comment.CommentInfoDto;
import sofit.demo.dto.comment.CommentSaveDto;
import sofit.demo.dto.comment.CommentUpdateDto;
import sofit.demo.global.template.ResponseTemplate;
import sofit.demo.service.comment.CommentService;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;

    @PostMapping("/comment/{boardId}") // 댓글 저장
    public ResponseEntity<ResponseTemplate<CommentSaveDto>> commentSave(@PathVariable("boardId") Long boardId, @RequestBody CommentSaveDto commentSaveDto) {
        commentService.save(boardId, commentSaveDto);
        ResponseTemplate<CommentSaveDto> response = new ResponseTemplate<>(HttpStatus.OK, "댓글 성공", commentSaveDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/comment/{boardId}/{commentId}") //대댓글 저장
    public void reCommentSave(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, @RequestBody CommentSaveDto commentSaveDto) {
        commentService.saveReComment(boardId, commentId, commentSaveDto);
    }

    @PutMapping("/comment/{commentId}") //댓글 수정
    public void update(@PathVariable("commentId") Long commentId,@RequestBody CommentUpdateDto commentUpdateDto) {
        commentService.update(commentId, commentUpdateDto);
    }

    @DeleteMapping("/comment/{commentId}") //댓글 삭제
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }

    // @GetMapping("comment/{commentId}") //댓글 조회
    // public ResponseEntity<CommentInfoDto> getInfo(@PathVariable("commentId") Long commentId) {
    //     return ResponseEntity.ok(commentService.getCommentInfoDto(commentId));
    // }

    @GetMapping("comment/{boardId}") // boardId로 댓글 조회
    public ResponseEntity<List<CommentInfoDto>> getCommentsByBoardId(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(commentService.getCommentsByBoardId(boardId));
    }
}
