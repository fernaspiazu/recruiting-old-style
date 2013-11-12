package it.f2informatica.webapp.test.menu;

import com.google.common.collect.Sets;
import it.f2informatica.webapp.menu.*;
import it.f2informatica.mongodb.domain.constants.Authority;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class MenuCompositeTest {

	private MenuComposite menuComposite;
	private MenuComposite menuCompositeWithAuthorities;

	@Before
	public void setUp() {
		menuComposite = new MenuComposite("parent menu", "#");
		menuComposite.add(new MenuItem("label 1", "/label1"));
		menuComposite.add(new MenuItem("label 2", "/label2"));
		menuComposite.add(new MenuItem("label 3", "/label3"));

		menuCompositeWithAuthorities = new MenuComposite("parent authority menu", "#");
		menuCompositeWithAuthorities.add(new MenuItem("label 4", "/label4"), Authority.ROLE_ADMIN);
		menuCompositeWithAuthorities.add(new MenuItem("label 5", "/label5"), Authority.ROLE_USER);
		menuCompositeWithAuthorities.add(new MenuItem("label 6", "/label6"), Authority.ROLE_USER);
	}

	@Test
	public void addMenuItem() {
		menuComposite.add(new MenuItem("label 4", "/label4"));
		assertThat(menuComposite.getMenuItems().keySet()).contains(new MenuItem("label 4", "/label4"));
	}

	@Test(expected = MenuItemException.class)
	public void verifyIfItsPosibleToAddSameMenuItems() {
		MenuComposite wrongMenuCompositeToAdd = new MenuComposite("parent menu", "#");
		wrongMenuCompositeToAdd.add(new MenuItem("label 1", "/label1"));
		wrongMenuCompositeToAdd.add(new MenuItem("label 2", "/label2"));
		wrongMenuCompositeToAdd.add(new MenuItem("label 3", "/label3"));
		menuComposite.add(wrongMenuCompositeToAdd);
	}

	@Test(expected = MenuItemException.class)
	public void verifyIfItsPosibleToAddWithAtLeastOneExistentItem() {
		MenuComposite wrongMenuCompositeToAdd = new MenuComposite("parent menu", "#");
		wrongMenuCompositeToAdd.add(new MenuItem("Login", "/login"));
		wrongMenuCompositeToAdd.add(new MenuItem("label 2", "/label2"));
		wrongMenuCompositeToAdd.add(new MenuItem("Logout", "/logout"));
		menuComposite.add(wrongMenuCompositeToAdd);
	}

	@Test
	public void getMenuItems() {
		menuCompositeWithAuthorities.add(menuComposite, Authority.ROLE_ADMIN);
		assertThat(menuCompositeWithAuthorities.getMenuItems().keySet())
				.contains(menuComposite).hasSize(4);
	}

	@Test
	public void getItemByLabel() {
		assertThat(menuComposite.getItemByLabel("label 1"))
				.isEqualTo(new MenuItem("label 1", "/label1"));
	}

	@Test
	public void getInternalItemByLabel() {
		menuCompositeWithAuthorities.add(menuComposite);
		assertThat(menuCompositeWithAuthorities.getItemByLabel("label 1"))
				.isEqualTo(new MenuItem("label 1", "/label1"));
	}

	@Test
	public void getItemByLabelWhenItsNotFound() {
		menuCompositeWithAuthorities.add(menuComposite);
		assertThat(menuCompositeWithAuthorities.getItemByLabel("label 10"))
				.isInstanceOf(EmptyMenuItem.class);
	}

	@Test
	public void getItemByUrl() {
		assertThat(menuComposite.getItemByUrl("/label3"))
				.isEqualTo(new MenuItem("label 3", "/label3"));
	}

	@Test
	public void getInternalItemByUrl() {
		menuComposite.add(menuCompositeWithAuthorities);
		assertThat(menuComposite.getItemByUrl("/label5"))
				.isEqualTo(new MenuItem("label 5", "/label5"));
	}

	@Test
	public void getItemByUrlWhenItsNotFound() {
		menuCompositeWithAuthorities.add(menuComposite);
		assertThat(menuCompositeWithAuthorities.getItemByUrl("/label10"))
				.isInstanceOf(EmptyMenuItem.class);
	}

	@Test
	public void getItemsByAuthority() {
		menuCompositeWithAuthorities.add(new MenuItem("label 7", "/label7"), Authority.ROLE_USER);
		assertThat(menuCompositeWithAuthorities.getItemsByAuthority(Authority.ROLE_USER))
				.contains(new MenuItem("label 6", "/label6")).hasSize(3);
	}

	@Test
	public void getItemsByAuthorityNotFound() {
		assertThat(menuComposite.getItemsByAuthority(Authority.ROLE_ADMIN)).isEmpty();
	}

	@Test
	public void getItemsByAuthorityGivingEmptyList() {
		assertThat(menuComposite.getItemsByAuthorities(Sets.<Authority>newHashSet())).isEmpty();
	}

	@Test
	public void getItemsByAuthorityRecursively() {
		menuCompositeWithAuthorities.add(menuComposite);
		assertThat(menuCompositeWithAuthorities.getItemsByAuthority(Authority.ROLE_USER))
				.contains(new MenuItem("label 1", "/label1"), new MenuItem("label 5", "/label5")).hasSize(5);
	}

	@Test
	public void getItemsBySomeAuthorities() {
		Set<Authority> authorities = Sets.newHashSet(Authority.ROLE_ADMIN, Authority.ROLE_USER);
		menuCompositeWithAuthorities.addWithAuthorities(new MenuItem("multiple auth", "/multAuth"), authorities);
		Set<Menu> itemsByAuthorities = menuCompositeWithAuthorities.getItemsByAuthorities(authorities);
		assertThat(itemsByAuthorities).hasSize(4).contains(new MenuItem("multiple auth", "/multAuth"));
	}

	@Test
	public void getItemsByAuthoritiesGivingOneAuthority() {
		Set<Authority> authorities = Sets.newHashSet(Authority.ROLE_ADMIN, Authority.ROLE_USER);
		menuCompositeWithAuthorities.addWithAuthorities(new MenuItem("multiple auth", "/multAuth"), authorities);
		Set<Menu> itemsByAuthorities = menuCompositeWithAuthorities.getItemsByAuthority(Authority.ROLE_ADMIN);
		assertThat(itemsByAuthorities).hasSize(2).contains(new MenuItem("multiple auth", "/multAuth"));
	}

	@Test
	public void getItemsByExactlySetOfAuthorities() {
		Set<Authority> authorities = Sets.newHashSet(Authority.ROLE_ADMIN, Authority.ROLE_USER);
		menuCompositeWithAuthorities.addWithAuthorities(new MenuItem("multiple auth", "/multAuth"), authorities);
		Set<Menu> itemsByAuthorities = menuCompositeWithAuthorities.getExactlyItemsByAuthorities(authorities);
		assertThat(itemsByAuthorities).hasSize(1).contains(new MenuItem("multiple auth", "/multAuth"));
	}

	@Test
	public void getItemsByExactlySetOfAuthoritiesWithoutFindResults() {
		Set<Authority> authorities = Sets.newHashSet(Authority.ROLE_ADMIN, Authority.ROLE_USER);
		Menu menu = new MenuComposite("parent", "/parent");
		menu.addWithAuthorities(new MenuItem("multiple auth", "/multAuth"), authorities);
		Set<Menu> itemsByAuthorities = menu.getExactlyItemsByAuthorities(Sets.newHashSet(Authority.ROLE_ADMIN));
		assertThat(itemsByAuthorities).isEmpty();
	}

	@Test(expected = NotImplementedException.class)
	public void getMenuItemsThrowingException() {
		Menu fooMenu = new FooMenu();
		fooMenu.getMenuItems();
	}

	@Test(expected = NotImplementedException.class)
	public void addingWithoutAnyAction() {
		Menu fooMenu = new FooMenu();
		fooMenu.add(new FooMenu());
		fooMenu.getMenuItems();
	}

	@Test(expected = NotImplementedException.class)
	public void addingWithoutAnyActionWithAutority() {
		Menu fooMenu = new FooMenu();
		fooMenu.add(new FooMenu(), Authority.ROLE_USER);
		fooMenu.getMenuItems();
	}

	@Test(expected = NotImplementedException.class)
	public void addingWithoutAnyActionWithAutorities() {
		Menu fooMenu = new FooMenu();
		fooMenu.addWithAuthorities(new FooMenu(), Sets.<Authority>newHashSet());
		fooMenu.getMenuItems();
	}

	private class FooMenu extends Menu {

	}

}
