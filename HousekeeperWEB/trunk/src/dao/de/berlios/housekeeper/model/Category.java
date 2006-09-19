package de.berlios.housekeeper.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @hibernate.class table="category"
 */
public class Category extends BaseObject {

    private Long id;

    private String name;

    private String description;

    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	final Category other = (Category) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    /**
     * @hibernate.property column="description" length="70"
     */
    public String getDescription() {
	return description;
    }

    /**
     * @return Returns the id.
     * @hibernate.id column="id" generator-class="native" unsaved-value="null"
     */
    public Long getId() {
	return id;
    }
    
    /**
     * @hibernate.property column="name" length="15"
     */
    public String getName() {
	return name;
    }

    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result = PRIME * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String toString() {
	ToStringBuilder sb = new ToStringBuilder(this,
		ToStringStyle.DEFAULT_STYLE).append("name", this.name).append(
		"description", this.description);
	return sb.toString();
    }

}
