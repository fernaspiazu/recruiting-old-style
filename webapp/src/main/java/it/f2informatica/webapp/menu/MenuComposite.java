package it.f2informatica.webapp.menu;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.f2informatica.webapp.security.Authority;

import java.util.Map;
import java.util.Set;

import static it.f2informatica.webapp.menu.MenuPredicates.*;

public class MenuComposite extends Menu {

	private Map<Menu, Set<Authority>> items;

	public MenuComposite(String label, String url) {
		super(label, url);
		items = Maps.newHashMap();
	}

	public void add(Menu menu) {
		add(menu, Authority.ROLE_USER);
	}

	public void add(Menu menu, Authority authority) {
		addWithAuthorities(menu, Sets.newHashSet(authority));
	}

	public void addWithAuthorities(Menu menu, Set<Authority> authorities) {
		checkIfMenuContainsElementsFromThisMenu(menu);
		items.put(menu, authorities);
	}

	private void checkIfMenuContainsElementsFromThisMenu(Menu menuToScan) {
		if (menuToScan instanceof MenuComposite)
			for (Menu menuItem : items.keySet())
				if (Iterables.contains(menuToScan.getMenuItems().keySet(), menuItem))
					throw new MenuItemException("One item into menu's parameter is already inside this menu");
	}

	public Map<Menu, Set<Authority>> getMenuItems() {
		return items;
	}

	public Menu getItemByLabel(String label) {
		return internalMenuItemSearcher(items.keySet(), new PredicateSearchByLabel(label));
	}

	public Menu getItemByUrl(String url) {
		return internalMenuItemSearcher(items.keySet(), new PredicateSearchByUrl(url));
	}

	private Menu internalMenuItemSearcher(Set<Menu> keys, Predicate<Menu> predicate) {
		Menu result = Iterables.find(keys, predicate, new EmptyMenuItem());
		if (result instanceof EmptyMenuItem) {
			for (Menu menu : keys)
				if (menu instanceof MenuComposite)
					return internalMenuItemSearcher(menu.getMenuItems().keySet(), predicate);
		}
		return result;
	}

	public Set<Menu> getItemsByAuthority(Authority authority) {
		return getItemsByAuthorities(Sets.newHashSet(authority));
	}

	public Set<Menu> getItemsByAuthorities(Set<Authority> authorities) {
		return internalItemsByAuthoritySearcher(items, new PredicateSearchByAuthority(authorities));
	}

	public Set<Menu> getExactlyItemsByAuthorities(Set<Authority> authorities) {
		return internalItemsByAuthoritySearcher(items, new PredicateSearchExactlyByAuthority(authorities));
	}

	private Set<Menu> internalItemsByAuthoritySearcher(
				Map<Menu, Set<Authority>> _items, Predicate<Set<Authority>> predicate) {
		Set<Menu> finalSet = Sets.newHashSet();
		Map<Menu, Set<Authority>> resultSearch = Maps.filterValues(_items, predicate);
		for (Menu menu : resultSearch.keySet()) {
			try {
				finalSet.addAll(internalItemsByAuthoritySearcher(menu.getMenuItems(), predicate));
			} catch (MenuItemException e) {
				finalSet.add(menu);
			}
		}
		return finalSet;
	}

}
