package it.f2informatica.webapp.menu;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class MenuItem extends Menu {

	public MenuItem(String label, String url) {
		super(label, url);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(label)
				.append(url)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;

		MenuItem menuItem = (MenuItem) obj;
		return new EqualsBuilder()
				.append(label, menuItem.getLabel())
				.append(url, menuItem.getUrl())
				.isEquals();
	}

}
