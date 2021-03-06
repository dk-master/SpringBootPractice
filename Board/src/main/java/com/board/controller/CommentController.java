package com.board.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.board.domain.CommentDTO;
import com.board.service.CommentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController  // 모든 메서드는 화면이 아닌 리턴타입에 해당하는 데이터 자체를 리턴한다.
public class CommentController {
	
		@Autowired 
		private CommentService commentService;
		
		
		@RequestMapping(value = { "/comments","comments/{idx}"}, method = {RequestMethod.POST,RequestMethod.PATCH})
		
		public JsonObject registerComment(@PathVariable(value ="idx", required = false) Long idx,
				@RequestBody final CommentDTO params) {
			
			JsonObject jsonObj = new JsonObject();
			try {
				if(idx != null) {
					params.setIdx(idx);
				}
				boolean isRegistered = commentService.registerComment(params);
				jsonObj.addProperty("result", isRegistered);
			}catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였씁니다.");
			}catch(Exception e) {
				jsonObj.addProperty("message", "서버에라");
			}
			return jsonObj;
		}
		
		
		
		@GetMapping(value = "/comments/{boardIdx}")
		//PathVariable 은 requestParam과 비슷한 역할을 하고 url {boardIdx}와 매핑된다.
		public JsonObject getCommentList(@PathVariable("boardIdx") Long boardIdx, @ModelAttribute("params") CommentDTO params) {
				JsonObject jsonObj = new JsonObject();
				System.out.println(jsonObj);
				System.out.println("-----------------------------");
				List<CommentDTO> commentList = commentService.getCommentList(params);
				System.out.println(commentList);
				System.out.println("-----------------------------");
				if (CollectionUtils.isEmpty(commentList) == false) {
					JsonArray jsonArr = new Gson().toJsonTree(commentList).getAsJsonArray();
					System.out.println(jsonArr);
					System.out.println("-----------------------------");
					jsonObj.add("commentList", jsonArr);
					System.out.println(jsonObj);
				}

				return jsonObj;
			}
		
		@DeleteMapping(value="/comments/{idx}")
		public JsonObject deleteComment(@PathVariable("idx") final Long idx) {
			
			JsonObject jsonObj = new JsonObject();
			
			try {
				boolean isDeleted = commentService.deleteComment(idx);
				if(isDeleted) {jsonObj.addProperty("result", "댓글 삭제 성공");}
			} catch(DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발");
			}
			catch(Exception e) {
				jsonObj.addProperty("message", "서버에");
			}
			return jsonObj;
		}
		
		}