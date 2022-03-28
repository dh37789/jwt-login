package com.dhaudgkr.jwtSample.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ListResponse<T> extends Response{
    private List<T> list;
}
