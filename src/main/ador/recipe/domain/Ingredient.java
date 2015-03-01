package ador.recipe.domain;

import java.util.Date;

public class Ingredient {
	String item;
	Double amount;
	Unit unit;
	Date useBy;
	

    
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public void setUnit(String unit) {
		this.unit = Enum.valueOf(Unit.class,unit);
	}
	public Date getUseBy() {
		return useBy;
	}
	public void setUseBy(Date usedBy) {
		this.useBy = usedBy;
	}
	public Ingredient(String item, Double amount, Unit unit, Date usedBy) {
		super();
		this.item = item;
		this.amount = amount;
		this.unit = unit;
		this.useBy = usedBy;
	}
	
	public Ingredient() {
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((useBy == null) ? 0 : useBy.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (unit != other.unit)
			return false;
		if (useBy == null) {
			if (other.useBy != null)
				return false;
		} else if (!useBy.equals(other.useBy))
			return false;
		return true;
	}

	
}
