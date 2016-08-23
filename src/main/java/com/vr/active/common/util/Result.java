package com.vr.active.common.util;

public class Result {

	private String code;
	private String message;
	private Object content;

	public enum Code {
		SUCCESS("success"), FAILED("failed");

		private final String value;

		Code(String value)
		{
			this.value = value;
		}

		public String getValue()
		{
			return value;
		}
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Result(String code, String message)
	{
		super();
		this.code = code;
		this.message = message;
	}

	public Result()
	{
		super();
	}

	public static Result success()
	{
		return new Result(Result.Code.SUCCESS.getValue(), Result.Code.SUCCESS.getValue());
	}

	public static Result failed(String reason)
	{
		return new Result(Result.Code.FAILED.getValue(), reason);
	}



	
	
	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + ", content="
				+ content + "]";
	}

	public Object getContent()
	{
		return content;
	}

	public void setContent(Object content)
	{
		this.content = content;
	}

	public Result addObject(Object o){
		setContent(o);
		return this;
	}
}
