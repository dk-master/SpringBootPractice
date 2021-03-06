package com.board;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class MapperTests {
	@Autowired
	private BoardMapper boardMapper; //node에서 import 해서 모듈화시킨것 가져온 느낌 Autowired 써서
	
	@Test
	public void testOfInsert() {
		for(int i = 2; i<=50; i++) {
		BoardDTO params = new BoardDTO();
		params.setTitle(i + "번 게시글 제목");
		params.setContent(i + "번 게시글 내용");
		params.setWriter(i + "번 게시글 작성자");
		boardMapper.insertBoard(params);
		
	}
	}
	@Test
	public void testOfSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetail((long) 1);
		try {
			System.out.println(board);//객체의 주소가 출력된다.
			String boardJson = new ObjectMapper().writeValueAsString(board); //주소를파
			System.out.println("=========================");
			System.out.println(boardJson);
			System.out.println("=========================");

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testOfUpdate() {
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시물 수정");
		params.setContent("1번 게시물 내용 수정");
		params.setWriter("홍길");
		params.setIdx((long) 1);
		int result = boardMapper.updateBoard(params);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			try {
				String boardJson = new ObjectMapper().writeValueAsString(board); // json으로 변

				System.out.println("=========================");
				System.out.println(boardJson);
				System.out.println("=========================");

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testSelectList() {
		int boardTotalCount = boardMapper.selectBoardTotalCount(); 
		if (boardTotalCount > 0) {
			List<BoardDTO> boardList = boardMapper.selectBoardList();
			System.out.println(boardList);
			if (CollectionUtils.isEmpty(boardList) == false) {
				for (BoardDTO board : boardList) {
//					System.out.println("=========================");
//					System.out.println(board.getTitle());
//					System.out.println(board.getContent());
//					System.out.println(board.getWriter());
//					System.out.println("=========================");
				}
			}
		}
	}
	
	@Test
	public void testOfDelete() {
		int result = boardMapper.deleteBoard((long) 1);
		if(result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1); // boardMapper 클래스의 객체 selectBoardDet
			
			try {
				String boardJson = new ObjectMapper().writeValueAsString(board); // 객체를 json으로 바꿔줌
				System.out.println("=========================");
				System.out.println(boardJson);
				System.out.println("=========================");
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
	}
}
