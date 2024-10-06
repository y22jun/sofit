package sofit.demo.service.board;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sofit.demo.domain.Board;
import sofit.demo.dto.board.BoardInfoDto;
import sofit.demo.dto.board.BoardSaveDto;
import sofit.demo.dto.board.BoardUpdateDto;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.repository.BoardRepository;
import sofit.demo.repository.UserRepository;
import sofit.demo.service.file.FileService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final FileService fileService;

    public void save(BoardSaveDto boardSaveDto, MultipartFile multipartFile) {
        Board board = boardSaveDto.toEntity();

        board.confirmWriter(userRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다.")));
        if(multipartFile != null && !multipartFile.isEmpty()) {
            String filePath = fileService.save(multipartFile);
            board.updateFilePath(filePath);
        }
        boardRepository.save(board);
    }

    public void update(Long id, BoardUpdateDto boardUpdateDto, MultipartFile multipartFile) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        checkAuthority(board);

        if (boardUpdateDto != null) {
            boardUpdateDto.title().ifPresent(board::updateTitle);
            boardUpdateDto.content().ifPresent(board::updateContent);
            boardUpdateDto.hashtag().ifPresent(board::updateHashtag);
        }

        if(board.getFilePath() != null) {
            fileService.delete(board.getFilePath());
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String filePath = fileService.save(multipartFile);
            board.updateFilePath(filePath);
        }
    }

    public void delete(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        checkAuthority(board);

        if(board.getFilePath() != null) {
            fileService.delete(board.getFilePath());
        }

        boardRepository.delete(board);
    }

    public BoardInfoDto getBoardInfo(Long id) {
        return new BoardInfoDto(boardRepository.findWithUserById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다.")));
    }

    public List<BoardInfoDto> getAllBoards() {
        
        return boardRepository.findAll().stream()
                              .map(board -> new BoardInfoDto(board))
                              .collect(Collectors.toList());
    }

    public void checkAuthority(Board board) {
        if(!board.getUser().getEmail().equals(SecurityUtil.getLoginUsername())) 
            throw new IllegalArgumentException("x");
    }

    public Page<BoardInfoDto> searchingBoardList(String keyword1, Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitleContaining(keyword1, pageable);
        return getBoardResponseDTOS(pageable, boards);
    }

    private Page<BoardInfoDto> getBoardResponseDTOS(Pageable pageable, Page<Board> boards) {
        List<BoardInfoDto> boardDTOs = new ArrayList<>();

        for (Board board : boards) {
            BoardInfoDto result = BoardInfoDto.builder()
                    .board(board)
                    .build();
            boardDTOs.add(result);
        }

        return new PageImpl<>(boardDTOs, pageable, boards.getTotalElements());
    }

}
