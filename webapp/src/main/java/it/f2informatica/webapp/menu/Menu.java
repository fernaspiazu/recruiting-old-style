package it.f2informatica.webapp.menu;

import it.f2informatica.mongodb.domain.constants.Authority;
import org.apache.commons.lang.NotImplementedException;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class Menu {

	private static final String METHOD_NOT_IMPLEMENTED = "Method not yet implemented. Called method from: "
			+ Menu.class.getName();

	protected String label;
	protected String url;

	public Menu() {
	}

	public Menu(String label, String url) {
		this.label = checkNotNull(label);
		this.url = checkNotNull(url);
	}

	public void add(Menu menu) {
		checkIfOperationIsSupported();
	}

	public void add(Menu item, Authority role) {
		checkIfOperationIsSupported();
	}

	public void addWithAuthorities(Menu menu, Set<Authority> authorities) {
		checkIfOperationIsSupported();
	}

	public Map<Menu, Set<Authority>> getMenuItems() {
		checkIfOperationIsSupported();
		throw new NotImplementedException(METHOD_NOT_IMPLEMENTED);
	}

	public Menu getItemByLabel(String label) {
		throw new NotImplementedException(METHOD_NOT_IMPLEMENTED);
	}

	public Menu getItemByUrl(String url) {
		throw new NotImplementedException(METHOD_NOT_IMPLEMENTED);
	}

	public Set<Menu> getItemsByAuthority(Authority role) {
		throw new NotImplementedException(METHOD_NOT_IMPLEMENTED);
	}

	public Set<Menu> getItemsByAuthorities(Set<Authority> authorities) {
		throw new NotImplementedException(METHOD_NOT_IMPLEMENTED);
	}

	public Set<Menu> getExactlyItemsByAuthorities(Set<Authority> authorities) {
		throw new NotImplementedException(METHOD_NOT_IMPLEMENTED);
	}

	public String getLabel() {
		return label;
	}

	public String getUrl() {
		return url;
	}

	private void checkIfOperationIsSupported() {
		if (this instanceof MenuItem || this instanceof EmptyMenuItem) {
			throw new MenuItemException("Unsupported Operation");
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Menu");
		sb.append("{label='").append(label).append('\'');
		sb.append(", url='").append(url).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
