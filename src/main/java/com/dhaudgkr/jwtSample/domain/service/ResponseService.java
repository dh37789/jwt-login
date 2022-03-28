package com.dhaudgkr.jwtSample.domain.service;

import com.dhaudgkr.jwtSample.advice.ErrorCode;
import com.dhaudgkr.jwtSample.domain.response.Response;
import com.dhaudgkr.jwtSample.domain.response.SingleResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ResponseService {

    public <T> SingleResponse<T> getSingleResponse(T data){
        SingleResponse<T> response = new SingleResponse<>();
        setSuccessResponse(response);
        response.setData(data);

        return response;
    }

    private Response getSuccessResponse() {
        Response result = new Response();
        setSuccessResponse(result);

        return result;
    }

    private Response setSuccessResponse(Response response) {
        response.setSuccess(true);
        response.setCode("00");
        response.setMsg("성공");

        return response;
    }

    public Response getFailureResult(ErrorCode errorCode) {
        Response result = new Response();
        result.setSuccess(false);
        result.setCode(errorCode.getCode());
        result.setMsg(errorCode.getMessage());
        result.setStatus(errorCode.getStatus());

        return result;
    }

}
