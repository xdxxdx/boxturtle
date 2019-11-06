package com.xdx.common.dto;

import java.io.Serializable;

/**
 * @author gaolinlou
 * @description 对页面统一返回对象
 * @date 2018/8/8
 */
public class BaseResponse<T> implements Serializable{

	private static final long serialVersionUID = -7672234412786558026L;
	
	/**
     * 服务返回消息
     */
    private String msg;
    
    /**
     * 是否执行成功 0:成功 ,非0:失败
     */
    private Integer code = new Integer(0);
    
    /**
     * 业务数据 
     */
    private T data = null;

    public BaseResponse(){

    }
    public BaseResponse(Integer code, String msg,T data ){
        this.setCode(code,msg);
        this.data = data ;
    }

    public void setCode(Integer code, String msg) {
        this.setCode(code);
        this.msg = msg;
    }
    
    /**
     * 设置状态码。0-成功。非0-不成功.<br>
     * 当设置为0是，success会被设置为true。 当设置为false是，success会被设置为false。
     * @param code
     */
    public void setCode(Integer code) {
        if (code == null) {
            code = -1;
        }
        this.code = code;
    }

    public void setCode(int code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public Integer getCode() {
        return code;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

    public static BaseResponse<?> success() {
        return new BaseResponse<Object>(0,"success",null);
    }

    /***
     * success:操作成功返回 <br/>
     * @author gaolinlou
     * @param data 返回到前端的数据对象
     * @return BaseResponse
     * @since JDK 1.7
     */
    public static <E> BaseResponse<E> success(E data) {
        BaseResponse<E> result = new BaseResponse<E>(0,"success",data);
        return result;
    }


    /***
     * fail:操作失败时返回 <br/>
     * @author gaolinlou
     * @param errCode 错误代码
     * @return BaseResponse
     * @since JDK 1.7
     */
    public static BaseResponse<?> fail(int errCode) {
        BaseResponse<Object> result = new BaseResponse<Object>(errCode,"faliure",null);
        return result;
    }

    /**
     * 操作失败抛出错误代码和错误明细 fail:<br/>
     * @author gaolinlou
     * @param errCode
     * @param data
     * @return BaseResponse
     * @since JDK 1.7
     */
    public static <E> BaseResponse<E> fail(int errCode, E data){
        BaseResponse<E> result = new BaseResponse<E>(errCode,"failure",null);
        return result;
    }

    /**
     * 操作失败抛出错误代码和错误信息 fail:<br/>
     * @author gaolinlou
     * @param errCode
     * @param errMessage
     * @return BaseResponse
     * @since JDK 1.7
     */
    public static BaseResponse<?> fail(int errCode, String errMessage) {
        if (errMessage == null || errMessage == "") {
            return fail(errCode);
        } else {
            BaseResponse<Object> result = new BaseResponse<Object>(errCode, errMessage,null);
            return result;
        }
    }

}
