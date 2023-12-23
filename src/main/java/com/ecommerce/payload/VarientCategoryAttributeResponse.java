package com.ecommerce.payload;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.ecommerce.model.VarientCategory;
import com.ecommerce.model.VarientCategoryAttribute;
import com.ecommerce.model.VarientCategoryJoin;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class VarientCategoryAttributeResponse {

	private Long id;
	private String attributeName;
	private VarientCategoryReponse varientCategory;
	private Set<VarientCategoryJoinResonse> categoryJoins = new HashSet<>();
	

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VarientCategoryAttributeResponse that = (VarientCategoryAttributeResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(attributeName, that.attributeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attributeName);
    }
	
	public VarientCategoryAttributeResponse vatAttributeToResponse(VarientCategoryAttribute attribute) {
		this.setAttributeName(attribute.getAttributeName());
		this.setId(attribute.getId());
		VarientCategoryReponse categoryReponse=new VarientCategoryReponse();
		this.setVarientCategory(categoryReponse.vatCatToResponse(attribute.getVarientCategory()));
		return this;
	}
}
