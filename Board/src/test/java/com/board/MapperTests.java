package com.board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 게시글 제목"); // 클라에서 들어오는 데이터들 여기다 집어넣겟네 
		params.setContent("1번 게시물 내용");
		params.setWriter("김동관");
		
		int result = boardMapper.insertBoard(params);
		System.out.println("결과는 "	+ result + "입니다.");
		
	}
	
	@Test
	public void testOfSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetail((long) 1);
		try {
			String boardJson = new ObjectMapper().writeValueAsString(board);
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
}
