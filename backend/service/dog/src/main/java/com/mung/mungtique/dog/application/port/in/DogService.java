package com.mung.mungtique.dog.application.port.in;

import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogJoinReq;
import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogRes;
import com.mung.mungtique.dog.domain.Dog;

import java.util.List;

public interface DogService {
    Dog join(DogJoinReq mungJoinReq);
    List<DogRes> getDogs(Long userId);
}
