package com.thinkgem.jeesite.common.persistence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

@JsonInclude(Include.ALWAYS)
public class ReturnEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private T data;
	private String message = "";
	private String token;
	private int status = 1; // 0-没有登录，1-操作成功，2-操作失败，3-没有权限，4-系统异常，5-加密狗异常

	public ReturnEntity() {
		super();
	}

	/**
	 * 返回正常数据 code为1
	 * 
	 * @param message
	 *            返回的信息
	 * @return
	 */
	public static ReturnEntity success(String message) {
		return new ReturnEntity<Object>(ReturnStatus.OPTSUCCESS, message);
	}

	/**
	 * 返回正常数据 code为1
	 * 
	 * @param data
	 *            返回的数据
	 * @return
	 */
	public static ReturnEntity success(Object data) {
		return new ReturnEntity<Object>(ReturnStatus.OPTSUCCESS, data);
	}

	/**
	 * 返回正常数据 code为1
	 * 
	 * @param data
	 *            返回的数据
	 * @param message
	 *            返回提示信息
	 * @return
	 */
	public static ReturnEntity success(Object data, String message) {
		return new ReturnEntity<Object>(ReturnStatus.OPTSUCCESS, message, data);
	}

	/**
	 * 返回错误数据
	 * 
	 * @param message
	 *            错误信息
	 * @return
	 */
	public static ReturnEntity fail(String message) {
		return new ReturnEntity<Object>(ReturnStatus.OPTFAIL, message);
	}

	/**
	 * 返回操作状态码，及操作信息 使用场景： 错误返回提示 例：statuts = -2 message = "数据格式有误，请校验后重新提交"
	 * 
	 * @param status
	 * @param message
	 */
	public ReturnEntity(int status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * 其他全返回格式场景
	 * 
	 * @param status
	 * @param message
	 * @param data
	 */
	public ReturnEntity(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	/**
	 * 返回操作状态码及数据
	 * 
	 * @param status
	 * @param data
	 */
	public ReturnEntity(int status, T data) {
		this.status = status;
		this.data = data;
	}

	/**
	 * 操作成功，直接返回返回成功信息
	 * 
	 * @param message
	 */
	public ReturnEntity(String message) {
		this.status = 1;
		this.message = message;
	}

	/**
	 * 操作成功，直接返回返回数据
	 * 
	 * @param data
	 */
	public ReturnEntity(T data) {
		this.status = 1;
		this.data = data;
	}

	/**
	 * 操作成功，返回成功信息及成功数据
	 * 
	 * @param data
	 * @param message
	 */
	public ReturnEntity(T data, String message) {
		this.status = 1;
		this.data = data;
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
