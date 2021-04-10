package com.board.controller;
import com.board.domain.CommentDTO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping(value = "/board")
	public JsonObject getBoardList() {

		JsonObject jsonObj = new JsonObject();

		List<BoardDTO> boardList = boardService.getBoardList();
		if (CollectionUtils.isEmpty(boardList) == false) {
			JsonArray jsonArr = new Gson().toJsonTree(boardList).getAsJsonArray();
			jsonObj.add("commentList", jsonArr);
		}

		return jsonObj;
	}

}