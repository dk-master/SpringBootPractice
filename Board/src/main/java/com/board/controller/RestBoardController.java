package com.board.controller;
import com.board.domain.CommentDTO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
@RestController 
public class RestBoardController {
	
	@Autowired
	private BoardService boardService;
	
	//게시물 보기
	
	@GetMapping(value = "/board")
	public JsonObject getBoardList() {

		JsonObject jsonObj = new JsonObject();

		List<BoardDTO> boardList = boardService.getBoardList();
		if (CollectionUtils.isEmpty(boardList) == false) {
			JsonArray jsonArr = new Gson().toJsonTree(boardList).getAsJsonArray();
			jsonObj.add("boardList", jsonArr);
		}

		return jsonObj;
	}
	
	// 게시물 등록
	
	@PostMapping(value = "/board")
	public JsonObject addBoard(@RequestBody final BoardDTO params) {
		JsonObject jsonObj = new JsonObject();
		try {
			boolean isRegistered = boardService.registerBoard(params);
			jsonObj.addProperty("result","게시물 등록에 성공했습니다.");
		}catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였씁니다.");
		}catch(Exception e) {
			jsonObj.addProperty("message", "서버에라");
		}
		return jsonObj;
	}
	// 게시물 수정
	@PostMapping(value= {"/board/{idx}"})
	public JsonObject updateBoard(@PathVariable(value = "idx")Long idx, 
			@RequestBody final BoardDTO params) {
		JsonObject jsonObj = new JsonObject();
		try {
			params.setIdx(idx);
			boolean isUpdate = boardService.registerBoard(params);
			jsonObj.addProperty("result", "게시물을 수정하는데 성공했습니.");
		}catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였씁니다.");
		}catch(Exception e) {
			jsonObj.addProperty("message", "서버에라");
		}
		return jsonObj;
	}
	
	// 게시물 삭제
	@PostMapping(value="/board/del/{idx}")
	public JsonObject deleteBoard(@PathVariable("idx") final Long idx){
		JsonObject jsonObj = new JsonObject();
		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if(isDeleted) {
				jsonObj.addProperty("result", "게시물 삭제에 성공했습니다.");
			}
		}catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발");
		}
		catch(Exception e) {
			jsonObj.addProperty("message", "서버에");
		}
		return jsonObj;
	}
}