package com.boot.board_240718.validator;

import com.boot.board_240718.model.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Slf4j
@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
//        return Board.class.isAssignableFrom(clazz);
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

        Board b = (Board) obj;
        log.info("@3 b==>"+b);

        if (b.getContent().equals("")){
            e.rejectValue("content","key","내용을 입력하세요~");
        }
    }
}
