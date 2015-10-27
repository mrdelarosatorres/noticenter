package com.nubettix.noticenter.model;

import com.nubettix.noticenter.annotations.Collection;
import com.nubettix.noticenter.annotations.Column;

@Collection(name = "newsRss",dataBase="noticenter")
public class NewsRss {
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "rss")
	private String rss;
	@Column(name = "logo")
	private String logo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRss() {
		return rss;
	}

	public void setRss(String rss) {
		this.rss = rss;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}
