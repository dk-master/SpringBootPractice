package com.board.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
// UI를 담당하는 controller
@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@GetMapping(value = "/board/write.do") //스프링 4.3버전부터는 요청 메서드의 타입별로 매핑을 처리할 수 있는 에너테이션 추가
	
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
//		String title = "제목";
//		String content = "내용";
//		String writer = "김동관";
//		
//		model.addAttribute("title", title);
//		model.addAttribute("content", content);
//		model.addAttribute("writer", writer);
		if (idx == null) {
			model.addAttribute("board", new BoardDTO());
		}else {
			BoardDTO board = boardService.getBoardDetail(idx);
			if(board == null) {
				return "redirect:/board/list.do";
			}
			 model.addAttribute("board", board);
		}
		return "board/write"; 
	}
	
	@PostMapping(value = "/board/register.do")
	public String registerBoard(final BoardDTO params) {
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				// TODO => 게시글 등록에 실패하였다는 메시지를 전달
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}

		return "redirect:/board/list.do";
	}
	
	@GetMapping(value = "/board/list.do") // url정해둔
	public String openBoardList(Model model) {
		List<BoardDTO> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		for (BoardDTO board : boardList) {
			System.out.println("=========================");
			System.out.println(board.getTitle());
			System.out.println(board.getContent());
			System.out.println(board.getWriter());
			System.out.println("=========================");
		} // foreach 만약 여기서 나온 데이터를 담고싶다면 빈 배열에 객체들을 하나씩 집어넣어야겠
		return "board/list";
	}
	
	@GetMapping(value = "/board/view.do")
	public String openBoardDetail(@RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return "redirect:/board/list.do";
		}

		BoardDTO board = boardService.getBoardDetail(idx);
		if (board == null || "Y".equals(board.getDeleteYn())) {
			// TODO => 없는 게시글이거나, 이미 삭제된 게시글이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return "redirect:/board/list.do";
		}
		model.addAttribute("board", board);

		return "board/view";
	}
	
	@PostMapping(value = "/board/delete.do")
	public String deleteBoard(@RequestParam(value = "idx", required = false) Long idx) {
		if (idx == null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			return "redirect:/board/list.do";
		}

		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if (isDeleted == false) {
				// TODO => 게시글 삭제에 실패하였다는 메시지를 전달
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}

		return "redirect:/board/list.do";
	}
	
}