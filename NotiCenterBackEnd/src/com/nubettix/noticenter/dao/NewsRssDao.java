package com.nubettix.noticenter.dao;

import java.util.List;

import com.nubettix.noticenter.model.NewsRss;

public class NewsRssDao extends DaoImpl<NewsRss> {
	public NewsRssDao() {
		super(NewsRss.class);
	}

	@Override
	public NewsRss create(NewsRss document) {
		// TODO Auto-generated method stub
		return super.create(document);

	}

	public static void main(String[] args) {
		NewsRssDao dao = new NewsRssDao();
		NewsRss document = new NewsRss();
		// document.setDescription("description");
		// document.setLogo("logo");
		document.setName("RT");
		// document.setRss("rss");
		List<NewsRss> result = dao.read(document);
		System.out.println(result.get(0).getName());
	}

	@Override
	public NewsRss update(NewsRss document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(NewsRss document) {
		// TODO Auto-generated method stub

	}

}
