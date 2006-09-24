package de.berlios.housekeeper.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @hibernate.class table="stockItem"
 */
public class StockItem extends BaseObject {

    private Long id;

    private String name;

    private String description;

    private Date expiryDate;

    private Category category;

    /**
     * 
     * @hibernate.many-to-one not-null="true"
     */
    public Category getCategory() {
	return category;
    }

    public void setCategory(Category category) {
	this.category = category;
    }

    /**
     * @hibernate.property length="80"
     */
    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @hibernate.property type="date"
     */
    public Date getExpiryDate() {
	return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
	this.expiryDate = expiryDate;
    }

    /**
         * @return Returns the id.
         * @hibernate.id column="id" generator-class="native"
         */
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    /**
         * @hibernate.property not-null="true" length="50"
         */
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result = PRIME * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	final StockItem other = (StockItem) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    public String toString() {
	ToStringBuilder sb = new ToStringBuilder(this,
		ToStringStyle.DEFAULT_STYLE).append("name", this.name).append(
		"description", this.description).append("expiry",
		this.expiryDate).append("this.category", this.category);
	return sb.toString();
    }

}
