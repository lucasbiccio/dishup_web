package br.com.dishup.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DishUpException extends Throwable{

	private static final long serialVersionUID = 1L;

	private String className;
	private String methodName;
	private String methodParameterPassed;
	private String dateException;
	private Throwable exceptionThrown;
	
	public DishUpException(String className, String methodName, String methodParameterPassed, String message, Throwable exceptionThrown) {
		super(message);
		this.className = className;
		this.methodName = methodName;
		this.methodParameterPassed = methodParameterPassed;
		this.dateException = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		this.exceptionThrown = exceptionThrown;
	}

	public String getDateException() {
		return dateException;
	}
	
	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}
	
	public String getMethodParameterPassed(){
		return methodParameterPassed;
	}
	
	public Throwable getExceptionThrown(){
		return exceptionThrown;
	}
	
	@Override
	public String toString() {
		return "**********************************************************\n"+
			   "DATA("+this.dateException+")\n" +
			   "CLASSE("+this.className+")\n" +
			   "METODO("+this.methodName+")\n" +
			   "PARAMETRO METODO("+this.methodParameterPassed+")\n" +
			   "MESSAGEM: "+this.getMessage()+"\n" +
			   "EXCECAO LANCADA: "+exceptionThrown.toString()+"\n" +
			   "**********************************************************";
	}
}
