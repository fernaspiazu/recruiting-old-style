package it.f2informatica.webapp.menu;

public class EmptyMenuItem extends Menu {
	public static final String THIS_IS_AN_EMPTY_MENU_ITEM = "This is an Empty menu item";

	public String getLabel() {
		throw new MenuItemException(THIS_IS_AN_EMPTY_MENU_ITEM);
	}

	public String getUrl() {
		throw new MenuItemException(THIS_IS_AN_EMPTY_MENU_ITEM);
	}

}
