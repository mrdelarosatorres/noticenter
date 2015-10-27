package com.nubettix.noticenter.dao;

import java.util.List;

public interface Dao<T> {
	T create(T document);
	List<T> read(T document);
	T update(T document);
	void delete(T document);
}
