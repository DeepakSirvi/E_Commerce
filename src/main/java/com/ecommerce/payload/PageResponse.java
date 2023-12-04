package com.ecommerce.payload;

import java.util.Set;

import lombok.Data;

@Data
public class PageResponse<T> {
	private Set<T> content;
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private boolean last;
	private boolean first;
}
