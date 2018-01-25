package org.matozzo.training.messenger.resources.bean;

import javax.ws.rs.QueryParam;

public class MessageFilterBean {
	
	// ======================================================================
	// Esse attributos recebem a anotation pq seram inicializado com o valor
	// qd o @BeanParam do Service chamar eles
	// ======================================================================
	private @QueryParam("year") int year;
	private @QueryParam("start") int paginationStart;
	private @QueryParam("size") int paginationSize;

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getPaginationStart() {
		return paginationStart;
	}
	public void setPaginationStart(int paginationStart) {
		this.paginationStart = paginationStart;
	}
	public int getPaginationSize() {
		return paginationSize;
	}
	public void setPaginationSize(int paginationSize) {
		this.paginationSize = paginationSize;
	}
}


